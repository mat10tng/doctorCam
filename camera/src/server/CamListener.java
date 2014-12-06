package server;

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
		
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];;
		byte[] currentTime = new byte[AxisM3006V.TIME_ARRAY_SIZE];
		
		
		long oldTime =0;


		long timeDifference;
		int length;

		
		while(!Thread.interrupted()){
			length = camera.getJPEG(jpeg, 0);
			camera.getTime(currentTime, 0);
			if (camera.motionDetected() 
					&& serverMonitor.getCurrentMode() == ServerMonitor.AUTO_MODE) {
				serverMonitor.detectedMotion();
				serverMonitor.setMode(ServerMonitor.MOVIE_MODE);
			}
			serverMonitor.updateJpeg(jpeg,length);
			switch(serverMonitor.getCurrentMode()){
				case ServerMonitor.MOVIE_MODE:
					oldTime = updatePictureData(jpeg,currentTime,length);
					//serverMonitor.notifyNewPicture();
					break;
				default:
					timeDifference = byteToLong(currentTime) - oldTime;
					if(timeDifference > IDLE_MODE_TIME ){
						oldTime = updatePictureData(jpeg,currentTime,length);
						//serverMonitor.notifyNewPicture();
					}else{
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
	 * Convert a byte array to a long
	 */
	private long byteToLong(byte[] bytes){
		long i=
				(bytes[7] & 0xFFL) |
				(bytes[6] & 0xFFL) << 8 |
				(bytes[5] & 0xFFL) << 16 |
				(bytes[4] & 0xFFL) << 24 |
				(bytes[3] & 0xFFL) << 32|
				(bytes[2] & 0xFFL) << 40 |
				(bytes[1] & 0xFFL) << 48 |
				(bytes[0] & 0xFFL) << 56;

		return i; 

	}
	private long updatePictureData(byte[] jpeg, byte[] currentTime, int length){
		serverMonitor.updatePictureData(jpeg,currentTime,length);
		return  byteToLong(currentTime);
	}
}
