package client;


/**
 * @author Shan
 *
 */
public class PictureHandler extends Thread {
	private PictureMonitor pictureMonitor;
	private ClientMonitor clientMonitor;
	private long synchronizationThreashold;

	/**
	 * Creates a picturHandler thread which has responsibility of checking if
	 * sync mode or not.
	 * 
	 * @param clientMonitor
	 *            : The monitor where pictures comes from
	 * 
	 * @param pictureMonitor
	 *            : The monitor which pictures goes to
	 */
	public PictureHandler(ClientMonitor clientMonitor,
			PictureMonitor pictureMonitor) {
		this.pictureMonitor = pictureMonitor;
		this.clientMonitor = clientMonitor;
		synchronizationThreashold = 200;
	}

	/*
	 * Gets picture from clientmonitor, which check for asynchron mode of
	 * picture and then inputs the picture to the picturemonitor
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			while (!isInterrupted()) {
				Picture picture = clientMonitor.getPicture();
				if ((System.currentTimeMillis() - picture.getTimeStamp()) > synchronizationThreashold) {
					pictureMonitor.setMode(Constants.ViewMode.ASYNC_MODE);
				}
				pictureMonitor.addPicture(picture);
			}
		} catch (InterruptedException e) {
			// do something
		}
	}

}
