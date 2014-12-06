package test;

import java.io.IOException;

import server.*;

public class TestHTTPAndRegular6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String adress = "argus-5.student.lth.se";
		int port = 5555;
		ServerMonitor serverMonitor = new ServerMonitor();
		CamListener camListener = new CamListener(serverMonitor,adress,port);
		camListener.start();
		
		try {
			ServerHandler serverHandler = new ServerHandler(2002, serverMonitor);
			serverHandler.start();
			JPEGHTTPServer theServer = new JPEGHTTPServer(3334,serverMonitor);
			theServer.start();
		} catch (IOException e) {
			System.out.println("server problem in arg5");
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
