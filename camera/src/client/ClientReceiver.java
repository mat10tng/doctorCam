package client;

import java.io.InputStream;

public class ClientReceiver extends Thread {
	private ClientMonitor monitor;
	private InputStream input;
	private int id;

	/*
	 * Creates a ClientReceiver thread which receives data from a specific source.
	 * @monitor 	Monitor which dictates what to send and when
	 * @inputStream		the stream which information comes from
	 * @id	Which network this thread belongs to (ergo camera)
	 */
	public ClientReceiver(ClientMonitor monitor, InputStream inputStream, int id) {
		this.monitor = monitor;
		this.input = inputStream;
		this.id  = id;
	}

}
