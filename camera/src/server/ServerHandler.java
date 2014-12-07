package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler extends Thread{
	private ServerSocket serverSocket;
	private ServerSender serverSender;
	private ServerReceiver serverReceiver;
	private ServerMonitor serverMonitor;
	/**
	 * Start the server handler thread which take care of socket between server and client
	 * @param port
	 * @param serverMonitor
	 * @throws IOException
	 */
	public ServerHandler(int port ,ServerMonitor serverMonitor) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSender = new ServerSender(serverMonitor);
		serverReceiver = new ServerReceiver(serverMonitor);
		this.serverMonitor = serverMonitor;
		serverSender.start();
		serverReceiver.start();
	}
	
	/**
	 * Get the thread going.
	 */
	public void run(){
		while(!Thread.interrupted()){
				Socket socket;
				try {
					socket = serverSocket.accept();
					serverMonitor.openNewConnection(socket);
					serverMonitor.endConnection();
					socket.close();
				} catch (IOException e) {
				} catch (InterruptedException e) {
				}
		}	
	}
}