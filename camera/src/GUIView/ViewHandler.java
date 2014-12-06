package GUIView;

import client.Picture;
import client.PictureMonitor;

/**
 * @author Shan
 * 
 */
public class ViewHandler extends Thread {
	private PictureMonitor pictureMonitor;
	private ViewWindow window;
	private int id;

	/**
	 * Creates a Viewhandler which handles all information to a specific view.
	 * 
	 * @param monitor
	 *            : The monitor which handles shared data
	 * @param window
	 *            : The specific view connected to this instance
	 * @param id
	 *            : The unique id
	 */
	public ViewHandler(PictureMonitor pictureMonitor, ViewWindow window, int id) {
		this.pictureMonitor = pictureMonitor;
		this.window = window;
		this.id = id;
	}

	/*
	 * Handles pictures and information to a specific view.
	 * TODO sleeptime and putting out picture
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			while (!isInterrupted()) {
				System.out.println("hello");
				Picture p=pictureMonitor.getPicture(id);
				System.out.println("after monitor");
				if (p.getWaitTime()>0){
					System.out.println("sleeping: "+p.getWaitTime()+" id="+p.getId());
					Thread.sleep(p.getWaitTime());
				}
				window.updateView(p);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		window.close();
	}

}
