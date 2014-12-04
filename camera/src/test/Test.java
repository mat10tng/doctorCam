package test;

import java.io.IOException;

import server.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String adress = "argus-8.student.lth.se";
		int port = 8080;
		ServerMonitor serverMonitor = new ServerMonitor();
		CamListener camListener = new CamListener(serverMonitor,adress,port);
		camListener.start();
		try {
			ServerHandler serverHandler = new ServerHandler(2001, serverMonitor);
			serverHandler.start();
		} catch (IOException e) {
			System.out.println("server problem");
			e.printStackTrace();
		}
	}

}
