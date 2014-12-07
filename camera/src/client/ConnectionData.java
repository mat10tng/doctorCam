package client;

public class ConnectionData {
	private String IP;
	private int port;
	private int ID;
	private int action;

	/**
	 * Creates a connection data class, which contains all the connection data
	 * needed to establish a connection.
	 * 
	 * @param IP
	 *            : the IP address
	 * @param port
	 *            : the port number
	 * @param ID
	 *            : an ID, usually [0,2, ..]
	 * @param action
	 *            : which action to made (close or open)
	 */
	public ConnectionData(String IP, int port, int ID, int action) {
		this.IP = IP;
		this.port = port;
		this.ID = ID;
		this.action = action;
	}

	/**
	 * Creates a connection data class, which contains all the connection data
	 * needed to establish a connection.
	 * 
	 * @param ipinformation
	 *            - the information needed to make a connection
	 * @param action
	 *            - which action to made (close or open)
	 */
	public ConnectionData(IpInformation ipinformation, int action) {
		this.IP = ipinformation.getHost();
		this.port = ipinformation.getPort();
		this.ID = ipinformation.getId();
		this.action = action;
	}

	/**
	 * @return the IP address in string format
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * @return: the port number
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the connection ID
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return the action, found in Constants.ConnectionActions
	 */
	public int getAction() {
		return action;
	}
}
