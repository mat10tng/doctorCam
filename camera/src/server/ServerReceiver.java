package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerReceiver extends Thread{
	private InputStream inStream;
	public ServerReceiver(InputStream inStream, ServerMonitor serverMonitor) throws IOException {
		// TODO Auto-generated constructor stub
		this.inStream = inStream;		
	}
	public void run(){
		while(!Thread.interrupted()){

		}
	}

}
