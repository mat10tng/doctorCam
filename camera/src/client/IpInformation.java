package client;

public class IpInformation {
	private String host;
	private int port;
	private int id;

	/**
	 * Creates an ip information class, which contains the information needed to
	 * establish a connection
	 * 
	 * @param host
	 * @param port
	 * @param id
	 */
	public IpInformation(String host, int port, int id) {
		this.host = host;
		this.port = port;
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return (host + ", " + port);
	}

	public boolean equals(Object o) {
		IpInformation ipinf = (IpInformation) (o);
		if (ipinf.host.equals(host)) {
			return ipinf.port == port;

		}
		return false;

	}
}
