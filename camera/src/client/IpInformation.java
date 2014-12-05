package client;

public class IpInformation {
	private String host;
	private int port;
	private int id;
	public IpInformation(String host, int port, int id){
		this.host = host;
		this.port = port;
		this.id = id;
	}
	
	public String getHost(){
		return host;
	}
	
	public int getPort(){
		return port;
	}
	
	public int getId(){
		return id;
	}
	
	public String toString(){
		return (host + ", " + port);
	}
}
