package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ClientMonitor {
	private ArrayList<ConnectionData> queue;
	private HashMap<Integer,ArrayList<ClientSendData>> sendData;
	private LinkedList<Picture> pictures;
	private int cameraMode;
	private boolean motionDetected;
	
	
	/**
	 * Creates a ClientMonitor which handles the main traffic between network and GUI.
	 */
	public ClientMonitor() {
		motionDetected=false;
		queue = new ArrayList<ConnectionData>();
		sendData=new HashMap<Integer,ArrayList<ClientSendData>>();
		pictures= new LinkedList<Picture>();
		cameraMode=Constants.CameraMode.AUTO_MODE;
	}
	
	
	/**
	 * Called by CrtlListener when a new camera is to be connected 
	 * or a connection is to be closed
	 * */
	public synchronized void addConnectionData(ConnectionData data){
		queue.add(data);
		notifyAll();
	}
	/**
	 * Called by ClientReceiver when a new picture package has arrived
	 * Adds the new picture to a queue of pictures
	 */
	public synchronized void addPicture(Picture picture){
		picture.currentCameraMode=cameraMode;
		pictures.add(picture);
		notifyAll();
	}
	/**
	 * Called by ProcessHandler to get new connection information.
	 * 
	 * @return ConnectionData - Data to establish or destroy connection.
	 */
	public synchronized ConnectionData getConnectionData() throws InterruptedException{
		while(queue.isEmpty()){
			wait();
		}
		return queue.remove(0);
	}
	/**
	 * Called by ClientReceiver when new motion detected package has arrived.
	 * Tells the system that motion has been detected.
	 * TODO Switch to movie mode/ handle forced modes.
	 */
	public synchronized void motionDetected(){
		motionDetected=true;
		notifyAll();
	}
	
	/**
	 * Called by ClientSender thread to get next package to send.
	 * Blocking until ready to send package
	 * 
	 * @return ClientSendData - Data to be sent to server
	 */
	public synchronized ClientSendData writeToOutput(int id) throws InterruptedException {
		while(sendData.get(id).isEmpty()){
			wait();
		}
		return sendData.get(id).remove(0);
	}
	/**
	 * Sets the current Camera Mode
	 * @param mode: a constant listed in Constants.CameraMode
	 */
	public synchronized void setCameraMode(int mode){
		cameraMode=mode;
		notifyAll();
	}
	public synchronized void addNewConnection(int ID){
		sendData.put(ID, new ArrayList<ClientSendData>());
		notifyAll();
	}
	public synchronized void removeConnection(int ID){
		sendData.remove(ID);
		notifyAll();
	}
	public synchronized Picture getPicture() throws InterruptedException{
		while(pictures.isEmpty()){
			wait();
		}
		return pictures.pop();
	}
	private synchronized boolean connectionExists(int ID){
		return sendData.keySet().contains(ID);
	}
}
