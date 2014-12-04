package server;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Waits for sendPicture or sendMotionDetected, 
 * then sends a picture or a Boolean-value to the client.
 */
public class ServerSender extends Thread{
	private ServerMonitor serverMonitor;
	private OutputStream outputStream;
	private boolean newStream;

	public ServerSender(ServerMonitor serverMonitor) {
		// TODO Auto-generated constructor stub
		this.serverMonitor = serverMonitor;
		
	}
	public void run(){
		while(!Thread.interrupted()){
			while(!newStream)
				try {
					wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			try {
				wait();
				outputStream.write( serverMonitor.getData());
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void setNewOutStream(OutputStream outStream) {
		this.outputStream = outStream;
	}


}
