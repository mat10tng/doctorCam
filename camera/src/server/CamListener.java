package server;

//import se.lth.cs.eda040.fakecamera.*;
import se.lth.cs.eda040.proxycamera.AxisM3006V;

/**
 * Waits for the Camera H.W. to generate a picture. When a new picture exists it
 * is fetched and Camera H.W. is checked for motion. The data is then handed to
 * the monitor.
 * 
 * 
 */

public class CamListener extends Thread {
	private ServerMonitor serverMonitor;
	private String adress;
	private int port;
	private final static long IDLE_MODE_TIME = 5000;

	/**
	 * Creates a camera listener which use to communicate with the hardware.
	 */
	public CamListener(ServerMonitor serverMonitor, String adress, int port){
		this.adress = adress;
		this.port = port;
		this.serverMonitor = serverMonitor;

	}
	/**
	 * Start the thread.
	 */
	public void run() {
		AxisM3006V camera = startCamera(adress, port);
		
		byte[] jpeg;
		byte[] currentTime;
		byte[] oldTime = new byte[AxisM3006V.TIME_ARRAY_SIZE];
		long timeDifference;
		int length;

		
		while(!Thread.interrupted()){
			jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
			currentTime = new byte[AxisM3006V.TIME_ARRAY_SIZE];
			length = camera.getJPEG(jpeg, 0);
			camera.getTime(currentTime, 0);
			timeDifference  = timeDiff(currentTime,oldTime);

			if (camera.motionDetected() 
					&& serverMonitor.getCurrentMode() == ServerMonitor.AUTO_MODE) {
				serverMonitor.detectedMotion();
				serverMonitor.setMode(ServerMonitor.MOVIE_MODE);
			}
			switch(serverMonitor.getCurrentMode()){
				case ServerMonitor.MOVIE_MODE:
					serverMonitor.newPictureData(jpeg,currentTime,length);
					break;
				default:
					if(timeDifference > IDLE_MODE_TIME){
						serverMonitor.newPictureData(jpeg,currentTime,length);
						oldTime = currentTime;
					}
					break;

			}
		}
	}
		
	
	/**
	 * Initiate hardware and communication between server and camera.
	 */
	private AxisM3006V startCamera(String adress, int port){
		AxisM3006V camera = new AxisM3006V();
		camera.init();
		camera.setProxy(adress,port);
		camera.connect();
		return camera;
	}
	
	/**
	 *Different in time from two byte array of time stamp.
	 */
	private long timeDiff(byte[] newTime, byte[] oldTime){
		return byteToLong(newTime) - byteToLong(oldTime);
	}
	/**
	 * Convert a byte array to a long
	 */
	private long byteToLong(byte[] byteTime){
		long time=0;
		for(int i=0;i<8;i++){
			time=time<<8;
			long addTime=(long)byteTime[i];
			time+=addTime;
		}
		return time;
	}
}
