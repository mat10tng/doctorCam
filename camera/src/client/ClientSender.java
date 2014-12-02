package client;

import java.io.IOException;
import java.io.OutputStream;

public class ClientSender extends Thread {
	private ClientMonitor monitor;
	private OutputStream output;
	private int id;

	/*
	 * Creates a ClientSender thread, which sends activity over the network
	 * 
	 * @monitor Monitor which dictates what to send and when
	 * 
	 * @outputStream The outputstream to send information on
	 * 
	 * @id Which network this thread belongs to (ergo camera)
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
				byte[] httpPackage = new byte[10];
				httpPackage = monitor.writeToOutput(id);
				Thread.currentThread().interrupt();
				output.write(httpPackage);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
