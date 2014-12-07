package client;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Shan
 * 
 */
public class ClientSender extends Thread {
	private ClientMonitor clientMonitor;
	private OutputStream output;
	private int id;

	/**
	 * Creates a ClientSender thread, which sends activity over the network
	 * 
	 * @param monitor
	 *            : Monitor which dictates what to send and when
	 * @param outputStream
	 *            : The outputstream to send information on
	 * @param id
	 *            : Which network this thread belongs to (ergo camera)
	 */
	public ClientSender(ClientMonitor clientMonitor, OutputStream outputStream,
			int id) {
		this.clientMonitor = clientMonitor;
		this.output = outputStream;
		this.id = id;
	}

	/*
	 * Blocked by monitor until getting package to send to server, Dies by
	 * calling interrupt on thread.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			while (!isInterrupted()) {
				Byte[] packagedData = clientMonitor.getOutgoingData(id);
				// in order to write the data it needs to be in prmitive byte
				// form, not Byte objects
				byte[] primitiveBytePackage = toPrimativeByteArray(packagedData);
				output.write(primitiveBytePackage);
				output.flush();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {

		}
	}

	/**
	 * Turns a Byte[] to the primitive týpe.
	 * 
	 * @param bytePackage
	 *            : the Byte array to be changed.
	 * @return the primitive byte array
	 */
	private byte[] toPrimativeByteArray(Byte[] bytePackage) {
		byte[] primitiveBytePackage = new byte[bytePackage.length];
		for (int i = 0; i < bytePackage.length; i++) {
			primitiveBytePackage[i] = bytePackage[i].byteValue();
		}
		return primitiveBytePackage;
	}
}
