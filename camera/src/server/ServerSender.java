package server;

import java.io.OutputStream;

/**
 * Waits for sendPicture or sendMotionDetected, 
 * then sends a picture or a Boolean-value to the client.
 */
public class ServerSender extends Thread{
	

	public ServerSender(OutputStream outStream, ServerMonitor serverMonitor) {
		// TODO Auto-generated constructor stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
	}


}
