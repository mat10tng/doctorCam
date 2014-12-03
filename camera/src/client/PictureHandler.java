package client;

import java.util.HashMap;
import java.util.LinkedList;

public class PictureHandler extends Thread{
	private PictureMonitor pictureMonitor;
	private HashMap<Integer,LinkedList<Picture>> pictures;
	public PictureHandler(PictureMonitor pictureMonitor) {
		this.pictureMonitor=pictureMonitor;
		pictures=new HashMap<Integer,LinkedList<Picture>>();
		
	}
	public void registerID(int ID){
		pictures.put(ID,new LinkedList<Picture>());
	}
	public void unregisterID(int ID){
		pictures.remove(ID);
	}
	
	
	
	public void run(){
		 
	}

}
