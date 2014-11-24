package client;

import java.io.OutputStream;

public class ClientSender extends Thread{
private ClientMonitor monitor;
private OutputStream output;
private int id;

	/*
	 * Creates a ClientSender thread, which sends activity over the network
	 * @monitor 	Monitor which dictates what to send and when
	 * @outputStream	The outputstream to send on
	 * @id	Which network this thread belongs to (ergo camera)
	 */
	public ClientSender(ClientMonitor monitor, OutputStream outputStream, int id) {
		this.monitor = monitor;
		this.output = outputStream;
		this.id = id;
	}

	/*
	 * Blocked by monitor until getting package to send to server
	 * Dies by socket.close
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		
	}
}
