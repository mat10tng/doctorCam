package server;

import se.lth.cs.eda040.fakecamera.*;

/**
 * Waits for the Camera H.W. to generate a picture. When a new picture exists it
 * is fetched and Camera H.W. is checked for motion. The data is then handed to
 * the monitor.
 * 
 * 
 */

public class CamListener implements Runnable {
	private ServerMonitor serverMonitor;

	@Override
	public void run() {
		AxisM3006V camera = new AxisM3006V();
		camera.init();
		camera.connect();
		long lastTimeStamp = 0;
		while(!Thread.interrupted()){
			byte[] data = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
			camera.getJPEG(jpeg, 0);
			if (camera.motionDetected()) {
				serverMonitor.setDetected();
				notifyAll();
			}
			switch(serverMonitor.getCurrentMode()){
			byte[] currentTime;
			camera.getTime(currentTime, offset);
			case ServerMonitor.IDLE_MODE:
				if(camera.getTime(target, offset);)
				break;
			case ServerMonitor.MOVIE_MODE:
				
				break;
			}
		}
	}
}
