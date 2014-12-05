package client;

import java.util.LinkedList;
import java.util.HashMap;

public class ClientMonitor {
	private LinkedList<ConnectionData> queue;
	private HashMap<Integer,LinkedList<Byte[]>> sendData;
	private LinkedList<Picture> pictures;
	private int cameraMode;
	private boolean forcedMode;
	private boolean destroyed;
	
	
	/**
	 * Creates a ClientMonitor which handles the main traffic between network and GUI.
	 */
	public ClientMonitor() {
		forcedMode=false;
		queue = new LinkedList<ConnectionData>();
		sendData=new HashMap<Integer,LinkedList<Byte[]>>();
		pictures= new LinkedList<Picture>();
		cameraMode=Constants.CameraMode.AUTO_MODE;
		destroyed = false;
	}
	
	
	/**
	 * Called by CrtlListener when a new camera is to be connected 
	 * or a connection is to be closed
	 * */
	public synchronized void addConnectionData(ConnectionData data){
		if(data != null && data.getAction()==Constants.ConnectionActions.OPEN_CONNECTION){
			sendData.put(data.getID(), new LinkedList<Byte[]>());
		}
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
		return queue.pop();
	}
	/**
	 * Called by ClientReceiver when new motion detected package has arrived.
	 * Tells the system that motion has been detected.
	 * TODO Switch to movie mode/ handle forced modes.
	 */
	public synchronized void motionDetected(){
		if (cameraMode!=Constants.CameraMode.MOVIE_MODE && !forcedMode){
			addSendData(Constants.CameraMode.MOVIE_MODE);
			notifyAll();
		}
	}
	private void addSendData(int mode){
		Byte[] modeData=new Byte[2];
		switch(mode){
		case(Constants.CameraMode.IDLE_MODE):
			modeData=Constants.CameraMode.getIdleBytes();
			break;
		case(Constants.CameraMode.MOVIE_MODE):
			modeData=Constants.CameraMode.getMovieBytes();
			break;
		case(Constants.CameraMode.AUTO_MODE):
			modeData = Constants.CameraMode.getAutoBytes();
			break;
		default:
			System.out.println("wrong in addSendData: "+this.toString());
			System.exit(1);
		}
		for (LinkedList<Byte[]> queue:sendData.values()){
			queue.add(modeData);
		}	
	}
	/**
	 * Called by ClientSender thread to get next package to send.
	 * Blocking until ready to send package
	 * 
	 * @return ClientSendData - Data to be sent to server
	 */
	public synchronized Byte[] getOutgoingData(int id) throws InterruptedException {
		while(sendData.get(id).isEmpty()){
			wait();
		}
		return sendData.get(id).pop();
	}
	/**
	 * Sets the current Camera Mode
	 * @param mode: a constant listed in Constants.CameraMode
	 */
	public synchronized void setCameraMode(int mode){
		if (mode != Constants.CameraMode.AUTO_MODE && mode == cameraMode) {
			forcedMode = true;
			addSendData(mode);
		} else {
			forcedMode = false;
			addSendData(mode);
		}
		cameraMode=mode;
		notifyAll();
	}
	public synchronized void removeConnection(int ID){
		sendData.remove(ID);
	}
	public synchronized Picture getPicture() throws InterruptedException{
		while(pictures.isEmpty()){
			wait();
		}
		return pictures.pop();
	}


	/**
	 * Close all connections, wait until every connection is closed (or destroyed)
	 * @throws InterruptedException
	 */
	public synchronized void closeAllSockets() throws InterruptedException {
		addConnectionData(null);
		while(!destroyed){
			wait();
		}
		
	}


	/**
	 * Set destory variable to true, to indicate it is okay to kill the system
	 */
	public synchronized void destroyed() {
		destroyed = true;
	}
}
