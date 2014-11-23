package client;


public class ConnenctionData {
	public static final int CLOSE_CONNECTION=0;
	public static final int OPEN_CONNECTION=1;
	
	private String IP;
	private int port;
	private int ID;
	private int action;
	
	public ConnenctionData(String IP,int port,int ID,int action){
		this.IP=IP;
		this.port=port;
		this.ID=ID;
		this.action=action;
	}
	String getIP(){
		return IP;
	}
	int getPort(){
		return port;
	}
	int getID(){
		return ID;
	}
	int getAction(){
		return action;
	}
}
