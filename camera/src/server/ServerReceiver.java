package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerReceiver extends Thread{
	private ServerSocket serverSocket;
	public ServerReceiver(int port) throws IOException {
		// TODO Auto-generated constructor stub
		serverSocket = new ServerSocket(port);		
	}
	public void run(){
		while(!Thread.interrupted()){
			try {
				Socket clientSocket;
				clientSocket = serverSocket.accept();
				clientSocket.setTcpNoDelay(true);
				
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	public void close() throws IOException {
		// TODO Auto-generated method stub
		serverSocket.close();
	}

}
