package server;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ConnectionData;
import client.Constants;
import client.IpInformation;


public class StartServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "You will now set up a DR.Cam server which you can connect to.\n"
				+ "Make sure you have a proxy server running on an argus(1-8) camera");
		String camID = JOptionPane.showInputDialog("Enter camera ID (1-8): ");
		if(camID==null){
			closeSetup();
		}
		String cameraProxyPortString= JOptionPane.showInputDialog("Enter the port of the camera proxy: ");
		if(cameraProxyPortString==null){
			closeSetup();
		}
		String streamPortString = JOptionPane.showInputDialog("Enter the port at which the Stream Server will operate: ");
		if(streamPortString==null){
			closeSetup();
		}
		String httpPortString = JOptionPane.showInputDialog("Enter the port at which the HTTP Server will operate: ");
		if(httpPortString==null){
			closeSetup();
		}
		int cameraProxyPort= Integer.parseInt(cameraProxyPortString);
		int streamPort = Integer.parseInt(streamPortString);
		int httpPort = Integer.parseInt(httpPortString);
		
		String adress = generateAdress(camID);
		ServerMonitor serverMonitor = new ServerMonitor();
		CamListener camListener = new CamListener(serverMonitor,adress,cameraProxyPort);
		camListener.start();

		try {
			ServerHandler serverHandler = new ServerHandler(streamPort, serverMonitor);
			serverHandler.start();
			JPEGHTTPServer theServer = new JPEGHTTPServer(httpPort,serverMonitor);
			theServer.start();
			System.out.println("Server running...");
			JOptionPane.showMessageDialog(null, "Stream server running at port:"+streamPort+
					"\n HTTP server running at port:"+httpPort+"\n\nPress OK to close server ;) ");
			System.out.println("Server closed...");
			System.exit(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static String generateAdress(String currentID){
		return "argus-"+currentID+".student.lth.se";
	}
	private static void closeSetup(){
		JOptionPane.showMessageDialog(null, "Server setup closed");
		System.exit(0);
	}
}
