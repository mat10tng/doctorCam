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

	private int currentMode; // Depends on clientMode as well
								// as if motion is detected by theCamera H.W.
	private byte[] lastPictureData; // The most recent picture from Camera H.W.
	private boolean newPictureData;
	private boolean detectedMotion;

	private boolean newStream;
	private Socket socket;

	private boolean endConnection;

	public ServerMonitor() {
		setMode(AUTO_MODE);
		newPictureData = false;
		newStream = false;
		endConnection = false;
		detectedMotion = false;
	}

	// CamListener only 
	public synchronized int getCurrentMode() {
		return currentMode;
	}

	public synchronized void detectedMotion() {
		detectedMotion = true;
		notifyAll();
	}
	
	public synchronized void newPictureData(byte[] jpeg, byte[] currentTime,
			int dataLength) {
		// TODO Auto-generated method stub
		newPictureData = true;
		lastPictureData = new byte[ID_SIZE + LEN_SIZE + TS_SIZE
				+ dataLength];
		int offset = setData(lastPictureData, PICTURE_DATA_ID,ID_SIZE, 0);
		offset = setData(lastPictureData, intToByte(dataLength),LEN_SIZE, offset);
		offset = setData(lastPictureData, currentTime,TS_SIZE, offset);
		offset = setData(lastPictureData, jpeg,dataLength, offset);
		notifyAll();
	}

	//ServerHandler only
	
	public synchronized void openNewConnection(Socket s) {
		this.socket = s;
		newStream = true;
		notifyAll();
	}
	
	public synchronized void endConnection() throws InterruptedException {
		while (!endConnection) {
			wait();
		}
		System.out.println("i should come here after received end coneection");
		newStream = false;
		endConnection = false;
		notifyAll();
	}

	
	//ServerRecevier only 
	
	public synchronized InputStream getInputStream() throws IOException,
			InterruptedException {
		while (!newStream) wait();
		System.out.println("i should not be here inputstream after connection end");
		return socket.getInputStream();
	}
	
	public synchronized void receivedTerminateConnection() throws IOException {
		System.out.println("Connection has been terminate");
		endConnection = true;
		notifyAll();
	}

	//ServerSender only
	public synchronized OutputStream getOutputStream() throws IOException,
			InterruptedException {
		while (!newStream) wait();
		return socket.getOutputStream();
	}

	public synchronized byte[] getPictureData() throws InterruptedException {
		while (!newPictureData) {
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
		switch (read) {
		case AUTO_MODE:
			currentMode = AUTO_MODE;
			System.out.println("Autobot roll out");
			break;
			
		case IDLE_MODE:
			currentMode = IDLE_MODE;
			System.out.println("This is idle mode");
			break;
		case MOVIE_MODE:
			currentMode = MOVIE_MODE;
			System.out.println("We got movie night");
			break;
		}
		notifyAll();
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

}
