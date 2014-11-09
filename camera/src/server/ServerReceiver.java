package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerReceiver extends Thread{
	private ServerSocket ss;
	public ServerReceiver(int port) throws IOException {
		// TODO Auto-generated constructor stub
		ss = new ServerSocket(port);		
	}
	public void StartServerReceiver(){
		
	}

}
