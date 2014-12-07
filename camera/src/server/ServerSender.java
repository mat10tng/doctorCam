package server;

import java.io.IOException;
import java.io.OutputStream;

	/**
	 * Server receiver take care of input stream. Take in different 
	 * command from client when the thread is running
	 */
public class ServerSender extends Thread{
	private ServerMonitor serverMonitor;
	private OutputStream outputStream;
	public static final byte[] MOTION_DETECTED_ID = { 1 };

	/**
	 * 
	 * @param serverMonitor
	 */
	public ServerSender(ServerMonitor serverMonitor) {
		this.serverMonitor = serverMonitor;
		
	}
	public void run(){
		while(!Thread.interrupted()){
			try {
				outputStream = serverMonitor.getOutputStream();
				byte[] data=serverMonitor.getPictureData();
				outputStream.write(data);
				if(serverMonitor.newMotionData()) outputStream.write(MOTION_DETECTED_ID);
				outputStream.flush();
			}
			catch (IOException e) {
			}
			catch (InterruptedException e) {
			}
		}
	}

}
