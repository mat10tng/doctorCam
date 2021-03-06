package server;

import java.io.IOException;
import java.io.InputStream;

/**
	 * Server receiver take care of input stream. Take in different 
	 * command from client when the thread is running 
 */
public class ServerReceiver extends Thread{
	private InputStream inStream;
	private ServerMonitor serverMonitor;
	private static final int END_CONNECTION = -1;
	private static final int MODE_ACTION = 1;
	
	/**
	 * @param serverMonitor
	 * @throws IOException
	 */
	public ServerReceiver(ServerMonitor serverMonitor) throws IOException {
		this.serverMonitor = serverMonitor;
	}
	public void run(){
		while(!Thread.interrupted()){
			try {
				inStream = serverMonitor.getInputStream();
				int id = inStream.read();
				
				switch(id){
				//we are switching on END_CONNECTIONS, but currently no such packages are sent
				case END_CONNECTION:
					serverMonitor.receivedTerminateConnection();
					break;
				case MODE_ACTION:
					serverMonitor.setMode(inStream.read());
					break;
				}
			} catch (IOException e) {
				serverMonitor.receivedTerminateConnection();
			} catch (InterruptedException e) {
			}

		}
	}


}
