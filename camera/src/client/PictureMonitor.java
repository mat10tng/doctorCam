package client;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Hans-Johan
 * @edit Shan
 * 
 */
public class PictureMonitor {
	private HashMap<Integer, LinkedList<Picture>> pictures;
	private boolean forcedMode;
	private int viewMode;
	private long latestTime;

	/**
	 * Creates a picture monitor which handles which view windows should have a
	 * certain picture. Also handles if sync mode or not
	 */
	public PictureMonitor() {
		pictures = new HashMap<Integer, LinkedList<Picture>>();
		forcedMode = false;
		viewMode = Constants.ViewMode.SYNC_MODE;
		latestTime = 0;
	}

	/**
	 * Switches the mode to the mode sent in. If forcedMode is enabled do
	 * nothing.
	 * 
	 * @param mode
	 *            : the mode which should be enabled
	 */
	public synchronized void setMode(int mode) {
		System.out.println("set mode: " + mode);
		if (!forcedMode) {
			this.viewMode = mode;
		}
	}

	/**
	 * Checks if the id is in use (ergo the connection with id exists)
	 * 
	 * @param id
	 *            : The id to be checked
	 * @return -1 if there is none otherwise the valid id
	 */
	public synchronized int checkVaildId(int id) {
		if (pictures.containsKey(id)) {
			return id;
		}
		return -1;
	}

	/**
	 * Forced the viewing mode to a certain mode. If auto is sent in, the mode
	 * switches to synchronized mode and forced mode is turned off
	 * 
	 * @param mode
	 *            : the mode which should be enabled
	 */
	public synchronized void setForcedMode(int mode) {
		if (mode != Constants.ViewMode.AUTO_MODE) {
			setMode(mode);
			forcedMode = true;
		} else {
			setMode(Constants.ViewMode.SYNC_MODE);
			forcedMode = false;
		}
	}

	/**
	 * Redisters a new Picture source which will be able to fetch pictures.
	 * 
	 * @param id
	 *            : The connection ID
	 */
	public synchronized void registerPictureSource(int id) {
		pictures.put(id, new LinkedList<Picture>());
	}

	/**
	 * Unredisters a new Picture source which will be able to fetch pictures.
	 * 
	 * @param id
	 *            The connection ID
	 */
	public synchronized void removePictureSource(int id) {
		pictures.remove(id);
	}

	/**
	 * @param id
	 *            : The connection ID
	 * @return a Picture object, containing displayinfo and more..
	 * @throws InterruptedException
	 */
	public synchronized Picture getPicture(int id) throws InterruptedException {
		while (pictures.containsKey(id) && pictures.get(id).isEmpty()) {
			wait();
		}
		Picture picture = pictures.get(id).pop();
		System.out.println(picture.getModeString());
		return picture;
	}

	/**
	 * Adds a picture to a specific Picturequeue. The pictures will then be
	 * retrievable by the corresponding View. Notifies all when a picture has
	 * arrived
	 * 
	 * @param picture
	 *            the Picture to add
	 */
	public synchronized void addPicture(Picture picture) {
		if (pictures.containsKey(picture.getId())) {
			if (pictures.size() > 1) {
				if (latestTime != 0 && viewMode == Constants.ViewMode.SYNC_MODE) {
					picture.setWaitTime(latestTime);
				}
				latestTime = picture.getTimeStamp();
			}
			picture.currentViewMode = viewMode;
			pictures.get(picture.getId()).add(picture);
			notifyAll();
		}
	}
}
