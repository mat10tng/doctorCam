package GUIView;

import client.PictureMonitor;

/**
 * @author Shan
 * 
 */
public class ViewHandler extends Thread {
	private PictureMonitor monitor;
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
	public ViewHandler(PictureMonitor monitor, ViewWindow window, int id) {
		this.monitor = monitor;
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
				monitor.getPicture(id);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		window.close();
	}

}
