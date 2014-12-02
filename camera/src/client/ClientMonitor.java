package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientMonitor {
	private ArrayList<ConnectionData> queue;
	private HashMap<Integer,ArrayList<ClientSendData>> sendData;
	private List<Picture> pictures;
	private boolean motionDetected;
	
	
	/*
	 * Creates a ClientMonitor which handles the main traffic between network and GUI.
	 */
	public ClientMonitor() {
		motionDetected=false;
		queue = new ArrayList<ConnectionData>();
		sendData=new HashMap<Integer,ArrayList<ClientSendData>>();
		pictures= new ArrayList<Picture>();

	}
	
	
	/*
	 * Called by CrtlListener when a new camera is to be connected 
	 * or a connection is to be closed
	 * */
	public synchronized void addConnectionData(ConnectionData data){
		queue.add(data);
	}
	/*
	 * Called by ClientReceiver when a new picture package has arrived
	 * Adds the new picture to a queue of pictures
	 */
	public synchronized void addPicture(Picture picture){
		pictures.add(picture);
	}
	/*
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
	/*
	 * Called by ClientReceiver when new motion detected package has arrived.
	 * Tells the system that motion has been detected.
	 * TODO Switch to movie mode/ handle forced modes.
	 */
	public synchronized void motionDetected(){
		motionDetected=true;
	}
	
	/*
	 * Called by ClientSender thread to get next package to send.
	 * Blocking until ready to send package
	 * 
	 * @return ClientSendData - Data to be sent to server
	 */
	public ClientSendData writeToOutput(int id) throws InterruptedException {
		while(sendData.get(id).isEmpty()){
			wait();
		}
		return sendData.get(id).remove(0);
	}

}
