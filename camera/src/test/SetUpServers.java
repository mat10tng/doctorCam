package test;

import java.io.IOException;
import java.util.ArrayList;

import server.CamListener;
import server.JPEGHTTPServer;
import server.ServerHandler;
import server.ServerMonitor;

public class SetUpServers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nbrOfServers=7;
		int baseStreamServerPort=2001;
		int baseHTTPServerIP=3001;
		ArrayList<ServerMonitor> monitors = new ArrayList<ServerMonitor>();
		ArrayList<CamListener> camListeners = new ArrayList<CamListener>();
		for(int i=1;i<=nbrOfServers;i++){
			monitors.add(new ServerMonitor());
			camListeners.add(new CamListener(monitors.get(i-1),generateAdress(i),generatePortNbr(i)));
		}
//		String adress = "argus-5.student.lth.se";
//		int port = 5555;
//		ServerMonitor serverMonitor = new ServerMonitor();
//		CamListener camListener = new CamListener(serverMonitor,adress,port);
//		camListener.start();
		
		try {
			for(int i=0;i<nbrOfServers;i++){
				camListeners.get(i).start();
				ServerHandler serverHandler = new ServerHandler(baseStreamServerPort+i, monitors.get(i));
				serverHandler.start();
				JPEGHTTPServer theServer = new JPEGHTTPServer(baseHTTPServerIP+i,monitors.get(i));
				theServer.start();
			}
//			ServerHandler serverHandler = new ServerHandler(2002, serverMonitor);
//			serverHandler.start();
//			JPEGHTTPServer theServer = new JPEGHTTPServer(3334,serverMonitor);
//			theServer.start();
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
	private static int generatePortNbr(int currentID){
		int port=0;
		for(int i=1;i<=1000;i*=10){
			port+=i*currentID;
		}
		return port;
	}
	private static String generateAdress(int currentID){
		return "argus-"+currentID+".student.lth.se";
	}
}
