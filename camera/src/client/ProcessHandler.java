package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import GUIView.ViewHandler;
import GUIView.ViewWindow;

/**
 * @author Shan
 *
 */
public class ProcessHandler extends Thread {

	private ClientMonitor MainMonitor;
	private PictureMonitor picMonitor;
	private HashMap<Integer, Socket> clientSockets;
	private HashMap<Integer, ViewHandler> views;
	private ConnectionData cdata;

	/**
	 * Creates a ProcessHandler thread which handles connections processes
	 * 
	 * @param MainMonitor
	 *            : The main monitor which gives out connection data to the
	 *            processhandler
	 * @param picMonitor
	 *            : Monitor used to create ViewHandler threads.
	 */
	public ProcessHandler(ClientMonitor MainMonitor, PictureMonitor picMonitor) {
		this.MainMonitor = MainMonitor;
		this.picMonitor = picMonitor;
		clientSockets = new HashMap<Integer, Socket>();
		views = new HashMap<Integer, ViewHandler>();
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
				cdata = MainMonitor.getConnectionData();
				Thread.currentThread().interrupt();
				int id = cdata.getID();
				switch (cdata.getAction()) {
				case (ConnectionData.OPEN_CONNECTION):
					views.put(id, new ViewHandler(picMonitor, new ViewWindow(
							"Camera: " + id), id));
					clientSockets.put(id,
							new Socket(cdata.getIP(), cdata.getPort()));
					new ClientReceiver(MainMonitor, clientSockets.get(id)
							.getInputStream(), id);
					new ClientSender(MainMonitor, clientSockets.get(id)
							.getOutputStream(), id);
					break;
				case (ConnectionData.CLOSE_CONNECTION):
					views.get(id).interrupt();
					clientSockets.get(id).close();
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
