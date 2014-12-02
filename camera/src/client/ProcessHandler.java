package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProcessHandler extends Thread {
	private ClientMonitor monitor;
	private ConnectionData cdata;
	private Socket clientSockets[];

	/*
	 * Creates a ProcessHandler thread which
	 */
	public ProcessHandler(ClientMonitor monitor) {
		this.monitor = monitor;
		clientSockets = new Socket[2];
	}

	/*
	 * Sets up and destroys connection, creates new connections threads and
	 * destroys them when terminated Blocked by monitor operation to know when
	 * to set up och kill connections.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			while (!isInterrupted()) {
				cdata = monitor.getConnectionData();
				Thread.currentThread().interrupt();
				int id = cdata.getID();
				switch (cdata.getAction()) {
				case (ConnectionData.OPEN_CONNECTION):
					clientSockets[id] = new Socket(cdata.getIP(),
							cdata.getPort());
					new ClientReceiver(monitor,
							clientSockets[id].getInputStream(), id);
					new ClientSender(monitor,
							clientSockets[id].getOutputStream(), id);
					break;
				case (ConnectionData.CLOSE_CONNECTION):
					clientSockets[id].close();
					break;
				default:
					System.out.println("You should not be here!");
					System.exit(1);
					break;
				}

			}
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
		} catch (UnknownHostException e) {
			System.out.println("Failed to connect to Host");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
