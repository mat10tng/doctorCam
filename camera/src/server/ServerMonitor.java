package server;

public class ServerMonitor {
	private static final int IDLE_MODE = 1;
	private static final int MOVIE_MODE = 2;
	private int currentFrequency; // Depends on clientMode as well 
									//as if motion is detected by theCamera H.W.
	private byte[] lastPicture; // The most recent picture from Camera H.W.
	boolean sendMotionDetected; // Depends on Camera H.W. and clientMode.
	public ServerMonitor() {
		setMode(IDLE_MODE);
	}
	
	
	public void setMode(int read) {
		switch(read){
		case IDLE_MODE:
			currentFrequency = 20;
			System.out.println("this is idle mode");
			break;
		case MOVIE_MODE:
			currentFrequency = 10;
			System.out.println("We got movie night");
			break;
		}
		notifyAll();
	}
	public int getCurrentFrequency(){
		return currentFrequency;
	}
	public byte[] getPicture() {
		return lastPicture;
	}
	public void newPicture(byte[] newPicture){
		lastPicture = newPicture;
		notifyAll();
	}
}
