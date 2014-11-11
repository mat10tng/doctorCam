package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandler extends Thread{
	private ServerSocket serverSocket;
	public ServerHandler(int port) throws IOException {
		// TODO Auto-generated constructor stub
		serverSocket = new ServerSocket(port);

	}
	public void run(){
		while(!Thread.interrupted()){
			try {
				ServerMonitor serverMonitor = new ServerMonitor();
				
				Socket s = serverSocket.accept();
				InputStream inStream = s.getInputStream();
				OutputStream outStream = s.getOutputStream();
				
				ServerSender ss = new ServerSender(outStream,serverMonitor);
				ServerReceiver sr = new ServerReceiver(inStream,serverMonitor);
				
				wait();
				ss.interrupt();
				sr.interrupt();
				s.close();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
