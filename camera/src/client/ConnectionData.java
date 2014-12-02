package client;


public class ConnectionData {
	public static final int CLOSE_CONNECTION=0;
	public static final int OPEN_CONNECTION=1;
	
	private String IP;
	private int port;
	private int ID;
	private int action;
	
	public ConnectionData(String IP,int port,int ID,int action){
		this.IP=IP;
		this.port=port;
		this.ID=ID;
		this.action=action;
	}
	public String getIP(){
		return IP;
	}
	public  int getPort(){
		return port;
	}
	public int getID(){
		return ID;
	}
	public  int getAction(){
		return action;
	}
}
