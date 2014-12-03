package client;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Hans-Johan
 *
 */
public class PictureMonitor {
	private HashMap<Integer,LinkedList<Picture>> pictures; 
	private boolean forcedMode;
	public PictureMonitor(){
		pictures=new HashMap<Integer,LinkedList<Picture>>();
		forcedMode = false;
	}
	
	public synchronized void setMode(int mode){
		
	}
	
	public synchronized void setForcedMode(int mode){
		setMode(mode);
		forcedMode = true;
	}
	/**
	 * Redisters a new Picture source which will be able to fetch pictures.
	 * @param id: The connection ID
	 */
	public synchronized void registerPictureSource(int id){
		pictures.put(id,new LinkedList<Picture>());		
	}

	/**
	 * Unredisters a new Picture source which will be able to fetch pictures.
	 * @param id The connection ID
	 */
	public synchronized void removePictureSource(int id){
		pictures.remove(id);	
	}
	
	
	/**
	 * @param id: The connection ID
	 * @return a Picture object, containing displayinfo and more..
	 * @throws InterruptedException
	 */
	public synchronized Picture getPicture(int id) throws InterruptedException{
		while (pictures.containsKey(id) && pictures.get(id).isEmpty()){
			wait();
		}
		return pictures.get(id).pop();
	}
	/**
	 * Adds a picture to a specific Picturequeue.
	 * The pictures will then be retrievable by the corresponding 
	 * View.
	 * @param id: The connection ID
	 * @param picture the Picture to add
	 */
	public synchronized void addPicture(int id,Picture picture){
		if (pictures.containsKey(id)) {
			pictures.get(id).add(picture);
		}
	}
}
