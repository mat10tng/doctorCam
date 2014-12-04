package server;

public class ServerMonitor {
	private static final int IDLE_MODE = 1;
	private static final int MOVIE_MODE = 2;
	private int currentMode; // Depends on clientMode as well 
									//as if motion is detected by theCamera H.W.
	private byte[] lastPicture; // The most recent picture from Camera H.W.
	private boolean detectedMotion;
	
	public ServerMonitor() {
		setMode(IDLE_MODE);
		detectedMotion = false;
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
	public byte[] getPicture() {
		return lastPicture;
	}
	public synchronized void newPicture(byte[] newPicture){
		lastPicture = newPicture;
		notifyAll();
	}

	public boolean detectedMotion() {
		return detectedMotion;
	}
	public void setDetected(){
		detectedMotion = true;
	}
	public int movieMode() {
		detectedMotion = false;
		return MOVIE_MODE;
	}
}
