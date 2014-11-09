package server;

public class ServerMonitor {
	private boolean closeRequested; //If true, the connection should be closed.
	private static final int AUTO = 0;
	private static final int FORCED_IDLE = 1;
	private static final int FORCED_MOVIE = 2;
	private int currentFrequency;
	private byte[] lastPicture;
	private boolean sendPicture;
	boolean sendMotionDetected;
	public ServerMonitor(ServerSender ss, ServerReceiver sr) {
		// TODO Auto-generated constructor stub
	}

}
