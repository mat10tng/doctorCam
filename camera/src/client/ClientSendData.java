package client;

public class ClientSendData {
	public static final int INTERUPT=0;
	public static final int SENDDATA=1;
	
	private byte[] httpData;
	private int status;
	public ClientSendData(int status, byte[] httpData){
		this.status=status;
		this.httpData=httpData;
	}
	public byte[] getHttpData(){
		return httpData;
	}
	public int getStatus(){
		return status;
	}
}
