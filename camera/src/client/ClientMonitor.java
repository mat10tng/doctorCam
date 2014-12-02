package client;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientMonitor {
	private ArrayList<ConnectionData> queue;
	private HashMap<Integer,ArrayList<ClientSendData>> sendData;
	public ClientMonitor() {
		queue = new ArrayList<ConnectionData>();
		sendData=new HashMap<Integer,ArrayList<ClientSendData>>();
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	 * Called by CrtlListener when a new camera is to be connected 
	 * or a connection is to be closed
	 * */
	public synchronized void addConnectionData(ConnectionData data){
		queue.add(data);
	}
	
	
	public synchronized ConnectionData getConnectionData() throws InterruptedException{
		while(queue.isEmpty()){
			wait();
		}
		return queue.remove(0);
		
	}

	/*
	 * Called by ClientSender thread to get next package to send.
	 * Blocking until ready to send package
	 */
	public ClientSendData writeToOutput(int id) throws InterruptedException {
		while(sendData.get(id).isEmpty()){
			wait();
		}
		return sendData.get(id).remove(0);
	}

}
