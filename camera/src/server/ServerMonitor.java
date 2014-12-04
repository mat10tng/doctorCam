package server;

public class ServerMonitor {
	public static final int IDLE_MODE = 1;
	public static final int MOVIE_MODE = 2;
	public static final int ID_SIZE= 1;
	public static final int LEN_SIZE = 4;
	public static final int TS_SIZE = 8;
	
	public static final byte[] MOTION_DETECTED_ID = {1};
	public static final byte[] PICTURE_DATA_ID = {1};

	private int currentMode; // Depends on clientMode as well 
									//as if motion is detected by theCamera H.W.
	private byte[] lastPictureData; // The most recent picture from Camera H.W.
	private boolean newData;
	private boolean detectedMotion;
	
	public ServerMonitor() {
		setMode(IDLE_MODE);
		newData = false;
	}
	
	public synchronized void setMode(int read) {
		switch(read){
		case IDLE_MODE:
			currentMode = IDLE_MODE;
			System.out.println("this is idle mode");
			break;
		case MOVIE_MODE:
			currentMode =MOVIE_MODE;
			System.out.println("We got movie night");
			break;
		}
		notifyAll();
	}
	public int getCurrentMode(){
		return currentMode;
	}
	public synchronized byte[] getData() {
		if(detectedMotion){
			detectedMotion = false;
			return MOTION_DETECTED_ID;
		}
		return lastPictureData;
	}

	
	public synchronized int movieMode() {
		detectedMotion = false;
		notifyAll();
		return currentMode;
	}
	
	public synchronized  boolean newData() {
		return newData;
	}
	
	public synchronized void  detectedMotion() {
		detectedMotion = true;
		newData = true;
		notifyAll();
	}
	
	public void newPictureData(byte[] jpeg, byte[] currentTime, int dataLength) {
		// TODO Auto-generated method stub
		
		byte[] lastPictureData = new byte[ID_SIZE+LEN_SIZE+TS_SIZE+ dataLength];
		
		int offset = setData(lastPictureData,PICTURE_DATA_ID,0);
		offset = setData(lastPictureData,intToByte(dataLength),offset);
		offset = setData(lastPictureData,currentTime,offset);
		offset = setData(lastPictureData,jpeg,offset);
		notifyAll();
	}
	
	
	private int setData(byte[] lastPictureData,byte[] data, int offset){
		for(int i = offset; i<data.length;i++){
			lastPictureData[i] = data[i-offset];
		}
		return offset+ data.length;
	}
	
	private byte[] intToByte(int data){
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
		    bytes[i] = (byte)(data >>> (i * 8));
		}
		return bytes;
	}

}
