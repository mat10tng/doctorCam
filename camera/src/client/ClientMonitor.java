package client;

import java.util.Collections;
import java.util.LinkedList;
import java.util.HashMap;

public class ClientMonitor {
	private LinkedList<ConnectionData> queue;
	private HashMap<Integer, LinkedList<Byte[]>> sendData;
	private LinkedList<Picture> pictures;
	private int cameraMode;
	private boolean motiondetected;
	private boolean forcedMode;
	private boolean destroyed;

	/**
	 * Creates a ClientMonitor which handles the main traffic between network
	 * and GUI.
	 */
	public ClientMonitor() {
		forcedMode = false;
		motiondetected = false;
		queue = new LinkedList<ConnectionData>();
		sendData = new HashMap<Integer, LinkedList<Byte[]>>();
		pictures = new LinkedList<Picture>();
		cameraMode = Constants.CameraMode.AUTO_MODE;
		destroyed = false;
	}

	/**
	 * Called by ControlWindow when a new camera is to be connected or a
	 * connection is to be closed
	 * */
	public synchronized void addConnectionData(ConnectionData data) {
		if (data != null
				&& data.getAction() == Constants.ConnectionActions.OPEN_CONNECTION) {
			
			sendData.put(data.getID(), new LinkedList<Byte[]>());
			Byte[] modeData = Constants.CameraMode.getModeBytes(cameraMode);
			addModeDataToConnection(data.getID(), modeData);
		}
		queue.add(data);
		notifyAll();
	}

	/**
	 * Called by ProcessHandler to get new connection information.
	 * 
	 * @return ConnectionData - Data to establish or destroy connection.
	 */
	public synchronized ConnectionData getConnectionData()
			throws InterruptedException {
		while (queue.isEmpty()) {
			wait();
		}
		return queue.pop();
	}	
	
	/**
	 * Removes a connection
	 * @param ID: the connection which should be removed
	 */
	public synchronized void removeConnection(int ID) {
		sendData.remove(ID);
	}
	
	/**
	 * Close all connections, wait until every connection is closed (or
	 * destroyed)
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void closeAllSockets() throws InterruptedException {
		addConnectionData(null);
		while (!destroyed) {
			wait();
		}

	}
	
	/**
	 * Adds packages to all connections currently running.
	 * @param mode: which mode to run.
	 */
	private void addModeDataToAllConnections(int mode) {
		Byte[] modeData = Constants.CameraMode.getModeBytes(mode);
		for (int ID: sendData.keySet()) {
			addModeDataToConnection(ID, modeData);
		}
		
	}

	/**
	 * Add data to be sent to a single connection.
	 * @param ID: The id of the connection
	 * @param mode: The mode to be switched to
	 */
	private void addModeDataToConnection(int ID, Byte[] data) {
		sendData.get(ID).add(data);
	}
	
	
	
	/**
	 * Called by ClientReceiver when a new picture package has arrived Adds the
	 * new picture to a queue of pictures, sorts the queue and then tells the
	 * system new picture is added.
	 */
	public synchronized void addPicture(Picture picture) {
		if (cameraMode == Constants.CameraMode.AUTO_MODE) {
			picture.currentCameraMode = motiondetected ? Constants.CameraMode.MOVIE_MODE
					: Constants.CameraMode.IDLE_MODE;
		} else {
			picture.currentCameraMode = cameraMode;
		}
		
		pictures.add(picture);
		Collections.sort(pictures);
		notifyAll();
	}

	/**
	 * Fetches the most recent picture in the queue
	 * @return First element of the queue of pictures
	 * @throws InterruptedException
	 */
	public synchronized Picture getPicture() throws InterruptedException {
		while (pictures.isEmpty()) {
			wait();
		}
		return pictures.pop();
	}
	
	/**
	 * Called by ClientReceiver when new motion detected package has arrived.
	 * Tells the system that motion has been detected.
	 */
	public synchronized void motionDetected() {
		if (cameraMode != Constants.CameraMode.MOVIE_MODE && !forcedMode) {
			addModeDataToAllConnections(Constants.CameraMode.MOVIE_MODE);
			motiondetected = true;
			notifyAll();
		}
	}

	/**
	 * Called by ClientSender thread to get next package to send. Blocking until
	 * ready to send package
	 * @param id: The id of the connection 
	 * @return senData for the selected connection
	 * @throws InterruptedException
	 */
	public synchronized Byte[] getOutgoingData(int id)
			throws InterruptedException {
		while (sendData.get(id).isEmpty()) {
			wait();
		}
		return sendData.get(id).pop();
	}

	/**
	 * Sets the current Camera Mode
	 * 
	 * @param mode
	 *            : a constant listed in Constants.CameraMode
	 */
	public synchronized void setCameraMode(int mode) {
		if (mode != Constants.CameraMode.AUTO_MODE && mode == cameraMode) {
			forcedMode = true;
		} else {
			forcedMode = false;
			motiondetected = false;
		}
		
		addModeDataToAllConnections(mode);
		cameraMode = mode;
		notifyAll();
	}

	
	/**
	 * Set destory variable to true, to indicate it is okay to kill the system
	 */
	public synchronized void destroyed() {
		destroyed = true;
	}
}
