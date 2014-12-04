package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler extends Thread{
	private ServerSocket serverSocket;
	private ServerSender serverSender;
	private ServerReceiver serverReceiver;
	private ServerMonitor serverMonitor;
	public ServerHandler(int port ,ServerMonitor serverMonitor) throws IOException {
		// TODO Auto-generated constructor stub
		serverSocket = new ServerSocket(port);
		serverSender = new ServerSender(serverMonitor);
		serverReceiver = new ServerReceiver(serverMonitor);
		this.serverMonitor = serverMonitor;
		serverSender.start();
		serverReceiver.start();
	}
	public void run(){
		while(!Thread.interrupted()){
				Socket socket;
				try {
					socket = serverSocket.accept();
					serverMonitor.openNewConnection(socket);
					serverMonitor.endConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		}	
	}
}