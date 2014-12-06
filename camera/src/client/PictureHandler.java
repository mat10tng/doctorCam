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
			long latestTime = 0;
			int id = -1;
			while (!isInterrupted()) {
				Picture picture = clientMonitor.getPicture();
				id = pictureMonitor.checkVaildId(id);
				//System.out.println(picture.getLatencyInMS());
				if(id >=0 && picture.getId() != id){
					if ((picture.getTimeStamp()-latestTime)  > synchronizationThreashold) {
						pictureMonitor.setMode(Constants.ViewMode.ASYNC_MODE);
					}
				}
				latestTime = picture.getTimeStamp();
				id = picture.getId();
				pictureMonitor.addPicture(picture);
			}
		} catch (InterruptedException e) {
			// do something
		}
	}

}
