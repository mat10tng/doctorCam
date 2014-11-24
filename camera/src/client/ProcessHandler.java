package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProcessHandler extends Thread {
	private ClientMonitor monitor;
	private ConnectionData cdata;
	private Socket clientSockets[];

	public ProcessHandler(ClientMonitor monitor) {
		this.monitor = monitor;
		clientSockets = new Socket[2];
	}

	/*
	 * Sets up and destroys connection, creates new connections threads and destroys them when terminated
	 * Blocked by monitor operation to know when to set up och kill connections.
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (!isInterrupted()) {
			try {
				cdata = monitor.getConnectionData();
			} catch (InterruptedException e1) {
				Thread.currentThread().interrupt();
			}
			int id = cdata.getID();
			switch (cdata.getAction()) {
			case (ConnectionData.OPEN_CONNECTION):
				try {
					clientSockets[id] = new Socket(cdata.getIP(),
							cdata.getPort());
					new ClientReceiver(monitor,
							clientSockets[id].getInputStream(),id);
					new ClientSender(monitor,
							clientSockets[id].getOutputStream(),id);
				} catch (UnknownHostException e) {
					System.out.println("Failed to connect to Host");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Something went wrong");
					e.printStackTrace();
				}

				break;
			case (ConnectionData.CLOSE_CONNECTION):
				try {
					clientSockets[id].close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("You should not be here!");
				System.exit(1);
				break;
			}

		}
	}

}
