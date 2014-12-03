package client;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Hans-Johan
 * 
 */
public class PictureMonitor {
	private HashMap<Integer, LinkedList<Picture>> pictures;
	private boolean forcedMode;
	private int mode;
	private long latestTime;

	public PictureMonitor() {
		pictures = new HashMap<Integer, LinkedList<Picture>>();
		forcedMode = false;
		mode = Constants.ViewMode.SYNC_MODE;
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
		if (!forcedMode) {
			this.mode = mode;
		}
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
		return pictures.get(id).pop();
	}

	/**
	 * Adds a picture to a specific Picturequeue. The pictures will then be
	 * retrievable by the corresponding View.
	 * 
	 * @param picture
	 *            the Picture to add
	 */
	public synchronized void addPicture(Picture picture) {
		if (pictures.containsKey(picture.getId())) {
			if(latestTime != 0 && mode == Constants.ViewMode.SYNC_MODE){
				picture.setWaitTime(picture.getTimeStamp()-latestTime);
			}
			latestTime = picture.getTimeStamp();
			pictures.get(picture.getId()).add(picture);
		}
	}
}
