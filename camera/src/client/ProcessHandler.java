package client;

import java.io.IOException;
import java.io.InterruptedIOException;
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

	public void run(){
		while (!Thread.interrupted()) {
			//cdata = monitor.getConnectionData();
			int id = cdata.getID();
			switch (cdata.getAction()) {
			case (ConnectionData.OPEN_CONNECTION):
				try {
					clientSockets[id] = new Socket(cdata.getIP(),
							cdata.getPort());
					new ClientReceiver(monitor,
							clientSockets[id].getInputStream());
					new ClientSender(monitor,
							clientSockets[id].getOutputStream());
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
