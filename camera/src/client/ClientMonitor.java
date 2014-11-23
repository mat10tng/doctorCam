package client;

import java.util.ArrayList;

public class ClientMonitor {
	private ArrayList<ConnectionData> queue;
	public ClientMonitor() {
		queue = new ArrayList<ConnectionData>();
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	 * Called by CrtlListener when a new camera is to be connected 
	 * or a connection is to be closed
	 * */
	public synchronized void addConnectionData(ConnectionData data){
		queue.add(data);
	}
	
	
	public synchronized ConnectionData getConnectionData(){
		ConnectionData data=null;
		while(queue.isEmpty()){
			data=queue.remove(0);
			break;
		}
		return data;
		
	}

}
