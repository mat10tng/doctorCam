package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *Waits for a package sent from the client. 
 *Then updates the Monitor attributes clientMode or 
 *closeRequested depending on the type of package.	 
 */
public class ServerReceiver extends Thread{
	private InputStream inStream;
	private ServerMonitor serverMonitor;
	private boolean terminate;
	private static final int END_CONNECTION = 0;
	private static final int MODE_ACTION = 1;
	
	public ServerReceiver(ServerMonitor serverMonitor) throws IOException {
		this.serverMonitor = serverMonitor;
	}
	public void run(){
		while(!Thread.interrupted()){
			
			try {
				inStream = serverMonitor.getInputStream();
				int id = inStream.read();
				switch(id){
				case END_CONNECTION:
					serverMonitor.receivedTerminateConnection();
					break;
				case MODE_ACTION:
					serverMonitor.setMode(inStream.read());
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}


}
