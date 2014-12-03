package client;

public class ClientSendData {
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
	public boolean isCloseConnection(){
		return status==Constants.ClientSendTypes.INTERRUPT;
	}
	public boolean isSendData(){
		return status==Constants.ClientSendTypes.SENDDATA;
	}
}
