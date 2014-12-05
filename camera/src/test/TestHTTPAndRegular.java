package test;

import java.io.IOException;

import server.*;

public class TestHTTPAndRegular {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String adress = "argus-7.student.lth.se";
		int port = 2222;
		ServerMonitor serverMonitor = new ServerMonitor();
		CamListener camListener = new CamListener(serverMonitor,adress,port);
		camListener.start();
		
		try {
			ServerHandler serverHandler = new ServerHandler(2001, serverMonitor);
			serverHandler.start();
			JPEGHTTPServer theServer = new JPEGHTTPServer(3333,serverMonitor);
			theServer.start();
		} catch (IOException e) {
			System.out.println("server problem");
			e.printStackTrace();
		}
		//JPEGHTTPServer theServer = new JPEGHTTPServer(5555);
//		try {
//			theServer.handleRequests();
//		} catch(IOException e) {
//			System.out.println("Error!");
//			theServer.destroy();
//			System.exit(1);
//		}
	}

}
