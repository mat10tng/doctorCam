package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerMonitor {
	public static final int AUTO_MODE = 2;
	public static final int IDLE_MODE = 0;
	public static final int MOVIE_MODE = 1;
	
	public static final int ID_SIZE = 1;
	public static final int LEN_SIZE = 4;
	public static final int TS_SIZE = 8;

	public static final byte[] PICTURE_DATA_ID = { 0 };
	private Socket socket;
	private int currentMode; // Depends on clientMode as well
								// as if motion is detected by theCamera H.W.
	private byte[] lastPictureData; // The most recent pictureData from Camera H.W.
	private byte[] lastJPEG;
	
	private boolean aliveJPEG;
	private boolean newPictureData;
	private boolean detectedMotion;
	private boolean streamAlive;
	private boolean endConnection;

	
	//private boolean streamHTTP;
	/**
	 * Creates a servermonitor, which handles all communication between hardware and the network.
	 */
	public ServerMonitor() {
		
		currentMode = AUTO_MODE;
		newPictureData = false;
		detectedMotion = false;
		streamAlive = false;
		endConnection = false;
		aliveJPEG=false;
	}

	// CamListener only 
	public synchronized int getCurrentMode() {
		return currentMode;
	}

	public synchronized void detectedMotion() {
		detectedMotion = true;
		notifyAll();
	}
	
	public synchronized void updatePictureData(byte[] jpeg, byte[] currentTime,
			int dataLength){
		lastPictureData = new byte[ID_SIZE + LEN_SIZE + TS_SIZE
				+ dataLength];
		int offset = setData(lastPictureData, PICTURE_DATA_ID,ID_SIZE, 0);
		offset = setData(lastPictureData, intToByte(dataLength),LEN_SIZE, offset);
		offset = setData(lastPictureData, currentTime,TS_SIZE, offset);
		offset = setData(lastPictureData, lastJPEG,dataLength, offset);
		notifyNewPicture();
	}
	private void notifyNewPicture(){
		newPictureData = true;
		notifyAll();
	}

	//ServerHandler only
	
	public synchronized void openNewConnection(Socket s) {
		this.socket = s;		
		streamAlive = true;
		endConnection = false;
		notifyAll();
	}
	
	public synchronized void endConnection() throws InterruptedException, IOException {
		while (!endConnection) {
			wait();
		}
		notifyAll();
	}

	
	//ServerRecevier only 
	
	public synchronized InputStream getInputStream() throws IOException,
			InterruptedException {
		while (!streamAlive) {
			wait();
		}
		return socket.getInputStream();
	}
	
	public synchronized void receivedTerminateConnection() {
		endConnection = true;
		streamAlive = false;
		notifyAll();
	}

	//ServerSender only
	public synchronized OutputStream getOutputStream() throws IOException,
			InterruptedException {
		while (!streamAlive){
			wait();
		}
		return socket.getOutputStream();
	}

	public synchronized byte[] getPictureData() throws InterruptedException {
		while (!newPictureData || !streamAlive) {
			wait();
		}
		newPictureData = false;
		return lastPictureData;
	}

	public synchronized boolean newMotionData() throws InterruptedException {
		boolean temp = detectedMotion;
		detectedMotion = false;
		return temp;
	}

	
	// others share
	public synchronized void setMode(int read) {
		currentMode=read;
		notifyNewPicture();
	}

	private int setData(byte[] lastPictureData, byte[] data,int length, int offset) {
		for (int i = offset; i < length+offset; i++) {
			lastPictureData[i] = data[i - offset];
		}
		return offset + length;
	}

	private byte[] intToByte(int data) {
		byte[] bytes = new byte[4];
		int index = 0;
		bytes[index++] = (byte) ((data & 0xff000000)>>24);
		bytes[index++] = (byte) ((data & 0x00ff0000)>>16);
		bytes[index++] = (byte) ((data & 0x0000ff00)>>8);
		bytes[index++] = (byte) ((data & 0x000000ff));
		

		return bytes;
	}

	public synchronized void updateJpeg(byte[] jpeg, int length){
		lastJPEG = new byte[length];
		setData(this.lastJPEG,jpeg,length,0);
		aliveJPEG = true;
		notifyAll();
	}
	public synchronized byte[] getLastJPEG() throws InterruptedException{
		while (!aliveJPEG){
			wait();
		}
		return lastJPEG;
	}


}
