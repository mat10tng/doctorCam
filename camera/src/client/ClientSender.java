package client;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Shan
 * 
 */
public class ClientSender extends Thread {
	private ClientMonitor monitor;
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
	public ClientSender(ClientMonitor monitor, OutputStream outputStream, int id) {
		this.monitor = monitor;
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
				ClientSendData packagedData;
				packagedData = monitor.writeToOutput(id);
				if (packagedData.isCloseConnection()) {
					throw new InterruptedException();
				} else {
					output.write(packagedData.getHttpData());
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
