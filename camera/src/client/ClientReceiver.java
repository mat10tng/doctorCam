package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * @author Shan
 * 
 */
public class ClientReceiver extends Thread {
	private ClientMonitor monitor;
	private InputStream input;
	private int id;

	/**
	 * Creates a ClientReceiver thread which receives data from a specific
	 * source.
	 * 
	 * @param monitor
	 *            : Monitor which dictates what to send and when
	 * @param inputStream
	 *            : the stream which information comes from
	 * @param id
	 *            : Which network this thread belongs to (ergo camera)
	 */
	public ClientReceiver(ClientMonitor monitor, InputStream inputStream, int id) {
		this.monitor = monitor;
		this.input = inputStream;
		this.id = id;
	}

	/*
	 * Blocked by inputstream until package is getting sent. Takes the package,
	 * identifies it and does the appropriate monitor operation. Dies by
	 * SocketException (when socket closes)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {

		try {
			while (!isInterrupted()) {
				// wait for package to be read
				byte[] packageType = new byte[1];
				input.read(packageType);
				// proccess package to information.
				// TODO CONSTANTS ADDED FROM SERVERSIDE
				switch (packageType[0]) {
				case ((byte)0 /* Picture Package */):
					int length = 0;
					byte[] lengthInBytes = new byte[4];
					input.read(lengthInBytes);
					length = byteToInt(lengthInBytes);

					byte[] dataPackage = new byte[length + 8];
					int readLength =  input.read(dataPackage);
					while(readLength < length+8){
						readLength+=input.read(dataPackage,readLength,(length+8-readLength));
					}
					monitor.addPicture(new Picture(dataPackage, id));
					break;
				case ((byte)1 /* Motion package */):
					monitor.motionDetected();
					break;
				default:
					throw new SocketException();
				}
			}
		} catch (SocketException e) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private int byteToInt(byte[] bytes){
		int i= (bytes[0]<<24)&0xff000000|
			       (bytes[1]<<16)&0x00ff0000|
			       (bytes[2]<< 8)&0x0000ff00|
			       (bytes[3]<< 0)&0x000000ff;
		
		return i; 
	}
}
