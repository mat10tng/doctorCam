package client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PictureMonitor {
	private HashMap<Integer,LinkedList<Picture>> pictures; 
	
	public PictureMonitor(){
		pictures=new HashMap<Integer,LinkedList<Picture>>();
	}
	public synchronized void registerPictureSource(int id){
		pictures.put(id,new LinkedList<Picture>());		
	}
	public synchronized Picture getPicture(int id) throws InterruptedException{
		while (pictures.containsKey(id) && pictures.get(id).isEmpty()){
			wait();
		}
		return pictures.get(id).pop();
	}
	public synchronized void addPicture(int id,Picture picture){
		if (pictures.containsKey(id)) {
			pictures.get(id).add(picture);
		}
	}
}
