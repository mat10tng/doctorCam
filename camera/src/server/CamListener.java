package server;

import se.lth.cs.eda040.fakecamera.*;

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

	public CamListener(ServerMonitor serverMonitor, String adress, int port){
		this.adress = adress;
		this.port = port;
		this.serverMonitor = serverMonitor;
	}
	public void run() {
		AxisM3006V camera = new AxisM3006V();
		byte[] jpeg;
		byte[] currentTime;
		byte[] oldTime = {0,0,0,0,0,0,0,0};
		long timeDifference;
		int length;
		camera.init();
		camera.setProxy(adress,port);
		camera.connect();
		
		while(!Thread.interrupted()){
			jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
			currentTime = new byte[AxisM3006V.TIME_ARRAY_SIZE];
			timeDifference  = timeDiff(currentTime,oldTime);

			length = camera.getJPEG(jpeg, 0);
			camera.getTime(currentTime, 0);

			if (camera.motionDetected() 
					&& serverMonitor.getCurrentMode() == ServerMonitor.IDLE_MODE) {
				serverMonitor.detectedMotion();
			}
			switch(serverMonitor.getCurrentMode()){
			case ServerMonitor.IDLE_MODE:
				if(timeDifference > IDLE_MODE_TIME) 
					serverMonitor.newPictureData(jpeg,currentTime,length);
				break;
			case ServerMonitor.MOVIE_MODE:
					serverMonitor.newPictureData(jpeg,currentTime,length);
				break;
			}
			oldTime = currentTime;
		}
	}
	private long timeDiff(byte[] newTime, byte[] oldTime){
		return byteToLong(newTime) - byteToLong(oldTime);
	}
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
