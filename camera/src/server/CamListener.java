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
	private ServerMonitor sm;

	@Override
	public void run() {
		AxisM3006V camera = new AxisM3006V();
		camera.init();
		camera.connect();
		for (int i = 0; i < 100; i++) {
			byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
			camera.getJPEG(jpeg, 0);
			if (camera.motionDetected()) {
				sm.setMode(2);
				notifyAll();
			} else {
				sm.setMode(1);
			}
		}
	}
}
