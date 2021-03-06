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

	private ClientMonitor clientMonitor;
	private PictureMonitor picMonitor;
	private HashMap<Integer, Socket> clientSockets;
	private HashMap<Integer, ViewHandler> views;
	private HashMap<Integer, ClientSender> senders;
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
		this.clientMonitor = MainMonitor;
		this.picMonitor = picMonitor;
		clientSockets = new HashMap<Integer, Socket>();
		views = new HashMap<Integer, ViewHandler>();
		senders = new HashMap<Integer, ClientSender>();
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
					cdata = clientMonitor.getConnectionData();
					if (cdata == null) {
						closeAllConnections();
					}
					int id = cdata.getID();
					try {
					switch (cdata.getAction()) {
					case (Constants.ConnectionActions.OPEN_CONNECTION):
						openConnection(id);
						break;
					case (Constants.ConnectionActions.CLOSE_CONNECTION):
						closeConnection(id);
						break;
					default:
						System.out.println("You should not be here!");
						System.exit(1);
						break;
					}
				} catch (UnknownHostException e) {
					System.out.println("Failed to connect to Host");
					views.get(id).interrupt();
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("something went wrong");
					views.get(id).interrupt();
					e.printStackTrace();
				}

			}
		} catch (InterruptedException e1) {
			System.out.println("interrupted thread, errorMessege"
					+ e1.getMessage());
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			System.out.println("something went wrong 2");
			e.printStackTrace();
		}

	}

	/**
	 * Closes all connections
	 * 
	 * @throws IOException
	 */
	private void closeAllConnections() throws IOException {
		for (int id : clientSockets.keySet()) {
			clientSockets.get(id).close();
		}
		clientMonitor.destroyed();

	}
	
	private  void openConnection(int id) throws UnknownHostException, IOException{
		views.put(id, new ViewHandler(picMonitor,
				new ViewWindow("Camera: " + id), id));
		views.get(id).start();
		clientSockets.put(id,
				new Socket(cdata.getIP(), cdata.getPort()));
		ClientReceiver receiver = new ClientReceiver(
				clientMonitor, clientSockets.get(id)
						.getInputStream(), id);
		senders.put(id, new ClientSender(clientMonitor,
				clientSockets.get(id).getOutputStream(), id));
		picMonitor.registerPictureSource(id);
		senders.get(id).start();
		receiver.start();
	}
	
	private void closeConnection(int id) throws IOException{
		clientSockets.get(id).close();
		senders.get(id).interrupt();
		views.get(id).interrupt();
		clientMonitor.removeConnection(id);
		picMonitor.removePictureSource(id);
		senders.remove(id);
		views.remove(id);
	}
}
