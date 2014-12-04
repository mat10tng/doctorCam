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
	public static final byte[] MOTION_DETECTED_ID = { 1 };


	public ServerSender(ServerMonitor serverMonitor) {
		// TODO Auto-generated constructor stub
		this.serverMonitor = serverMonitor;
		
	}
	public void run(){
		while(!Thread.interrupted()){
			try {
				outputStream = serverMonitor.getOutputStream();
				byte[] data=serverMonitor.getPictureData();
				int length=data.length;
				System.out.println(length);
				
				outputStream.write(data);
				if(serverMonitor.newMotionData()) outputStream.write(MOTION_DETECTED_ID);
				outputStream.flush();

			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
