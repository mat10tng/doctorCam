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
	public ServerHandler(int port ,ServerMonitor serverMonitor) throws IOException {
		// TODO Auto-generated constructor stub
		serverSocket = new ServerSocket(port);
		serverSender = new ServerSender(serverMonitor);
		serverReceiver = new ServerReceiver(serverMonitor);
		serverSender.start();
		serverReceiver.start();
	}
	public void run(){
		while(!Thread.interrupted()){
			try {
				Socket s = serverSocket.accept();
				InputStream inStream = s.getInputStream();
				OutputStream outStream = s.getOutputStream();		
				serverSender.setNewOutStream(outStream);
				serverReceiver.setNewInStream(inStream);
				while(!serverReceiver.endConnection()){
					wait();
				}
				s.close();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
