package client;

import java.util.LinkedList;
import java.util.HashMap;

public class ClientMonitor {
	private LinkedList<ConnectionData> queue;
	private HashMap<Integer,LinkedList<ClientSendData>> sendData;
	private LinkedList<Picture> pictures;
	private int cameraMode;
	private boolean forcedMode;
	
	
	/**
	 * Creates a ClientMonitor which handles the main traffic between network and GUI.
	 */
	public ClientMonitor() {
		forcedMode=false;
		queue = new LinkedList<ConnectionData>();
		sendData=new HashMap<Integer,LinkedList<ClientSendData>>();
		pictures= new LinkedList<Picture>();
		cameraMode=Constants.CameraMode.IDLE_MODE;
	}
	
	
	/**
	 * Called by CrtlListener when a new camera is to be connected 
	 * or a connection is to be closed
	 * */
	public synchronized void addConnectionData(ConnectionData data){
		if(data.getAction()==Constants.ConnectionActions.OPEN_CONNECTION){
			sendData.put(data.getID(), new LinkedList<ClientSendData>());
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
		byte[] modeData=new byte[2];
		switch(mode){
		case(Constants.CameraMode.IDLE_MODE):
			modeData=Constants.CameraMode.getIdleBytes();
			break;
		case(Constants.CameraMode.MOVIE_MODE):
			modeData=Constants.CameraMode.getMovieBytes();
			break;
		default:
			System.out.println("wrong in assSendData: "+this.toString());
			System.exit(1);
		}
		ClientSendData changeModeData=new ClientSendData(Constants.ClientSendTypes.SENDDATA,modeData);
		for (LinkedList<ClientSendData> queue:sendData.values()){
			queue.add(changeModeData);
		}	
	}
	/**
	 * Called by ClientSender thread to get next package to send.
	 * Blocking until ready to send package
	 * 
	 * @return ClientSendData - Data to be sent to server
	 */
	public synchronized ClientSendData getOutgoingData(int id) throws InterruptedException {
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
		if (mode != Constants.CameraMode.AUTO_MODE) {
			cameraMode=mode;
			forcedMode = true;
			addSendData(mode);
		} else {
			cameraMode=Constants.CameraMode.IDLE_MODE;
			forcedMode = false;
			addSendData(Constants.CameraMode.IDLE_MODE);
		}
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
}
