package server;

public class ServerMonitor {
	private boolean closeRequested; //If true, the connection should be closed.
	private static final int AUTO = 0;
	private static final int FORCED_IDLE = 1;
	private static final int FORCED_MOVIE = 2;
	private int currentFrequency; // Depends on clientMode as well 
									//as if motion is detected by theCamera H.W.
	private byte[] lastPicture; // The most recent picture from Camera H.W.
	private boolean sendPicture; // Depends on currentFrequency and the time.
	boolean sendMotionDetected; // Depends on Camera H.W. and clientMode.
	public ServerMonitor() {
		// TODO Auto-generated constructor stub
	}
	public void setMode(int read) {
		// TODO Auto-generated method stub
		
	}
	public byte[] getData() {
		// TODO Auto-generated method stub
		return null;
	}
}
