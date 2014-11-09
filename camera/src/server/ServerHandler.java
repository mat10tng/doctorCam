package server;

import java.io.IOException;

public class ServerHandler extends Thread{

	public ServerHandler() {
		// TODO Auto-generated constructor stub
	}
	public void run(){
		while(!Thread.interrupted()){
			ServerReceiver sr = new ServerReceiver(1234);
			ServerSender ss = new ServerSender();
			ServerMonitor sm = new ServerMonitor(ss,sr);
		}
	}
}
