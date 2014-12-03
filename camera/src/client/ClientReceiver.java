package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * @author Shan
 * 
 */
public class ClientReceiver extends Thread {
	private ClientMonitor monitor;
	private InputStream input;
	private int id;

	/**
	 * Creates a ClientReceiver thread which receives data from a specific
	 * source.
	 * 
	 * @param monitor
	 *            : Monitor which dictates what to send and when
	 * @param inputStream
	 *            : the stream which information comes from
	 * @param id
	 *            : Which network this thread belongs to (ergo camera)
	 */
	public ClientReceiver(ClientMonitor monitor, InputStream inputStream, int id) {
		this.monitor = monitor;
		this.input = inputStream;
		this.id = id;
	}

	/*
	 * Blocked by inputstream until package is getting sent. Takes the package,
	 * identifies it and does the appropriate monitor operation. Dies by
	 * SocketException (when socket closes)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		ArrayList<Byte> dataPackage = new ArrayList<Byte>();
		try {
			while (!isInterrupted()) {
				// wait for package to be read
				int tempData = input.read();
				while (tempData > -1) {
					//cast from int to byte
					dataPackage.add((byte) (tempData - 128));
					tempData = input.read();
				}
				// proccess package to information.
				// TODO CONSTANTS ADDED FROM SERVERSIDE
				switch (dataPackage.get(0)) {
				case (0 /* Picture Package */):
					dataPackage.remove(0);
					monitor.addPicture(new Picture((Byte[]) dataPackage
							.toArray(), id));
					break;
				case (1 /* Motion package */):
					monitor.motionDetected();
					break;
				}
			}
		} catch (SocketException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
