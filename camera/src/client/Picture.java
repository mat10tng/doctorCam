package client;

import java.util.Date;

import javax.swing.ImageIcon;

/**
 * @author Shan
 * 
 */
public class Picture implements Comparable<Object> {
	private ImageIcon picture;
	private long waitTime;
	private long timeStamp;
	private int id;
	public int currentViewMode;
	public int currentCameraMode;

	/**
	 * Converts a data package to a picture object. Represents a picture and all
	 * data belonging to that picture
	 * 
	 * @param data
	 *            : The data package to be converted
	 * @param id
	 *            : which network this data originated from
	 */
	public Picture(byte[] dataPackage, int id) {
		this.id = id;
		waitTime = 0;
		byte[] byteTimestamp = new byte[8];
		byte[] bytePicture = new byte[dataPackage.length - 8];
		for (int i = 0; i < dataPackage.length; i++) {
			if (i < 8) {
				byteTimestamp[i] = dataPackage[i];
			} else {
				bytePicture[i - 8] = dataPackage[i];
			}
		}
		timeStamp = byteToLong(byteTimestamp);
		//System.out.println("timestamp= "+new Date(timeStamp)+" for id="+id);
		//System.out.println("currentTime= "+new Date(System.currentTimeMillis()));
		picture = new ImageIcon(bytePicture);
	}

	/**
	 * Convert a byte array to a long
	 */
	private long byteToLong(byte[] bytes){
		long i=
				(bytes[7] & 0xFFL) |
				(bytes[6] & 0xFFL) << 8 |
				(bytes[5] & 0xFFL) << 16 |
				(bytes[4] & 0xFFL) << 24 |
				(bytes[3] & 0xFFL) << 32|
				(bytes[2] & 0xFFL) << 40 |
				(bytes[1] & 0xFFL) << 48 |
				(bytes[0] & 0xFFL) << 56;

		return i; 

	}

	/**
	 * Returns the picture in byte format
	 * 
	 * @return: picture in the form of bytes
	 */
	public ImageIcon getPicture() {
		return picture;
	}

	/**
	 * Returns a Timestamp from when the picture was taken
	 * 
	 * @return: Timestamp in the form of bytes
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long latestTime) {
		long newWaitTime = timeStamp - latestTime;
//		if(newWaitTime<0){
//			System.out.println("Negative WaitTime for picture from Camera with ID="+id);
//		}
		this.waitTime = newWaitTime > 0 ? newWaitTime : 0;
	}

	/**
	 * The id the data/ picture came from.
	 * 
	 * @return: id of what network as an int
	 */
	public int getId() {
		return id;
	}

	public long getLatencyInMS() {
		return System.currentTimeMillis() - timeStamp;
	}

	public String getModeString() {
		String settingsString = "";
		settingsString += " ViewMode="
				+ Constants.ViewMode.toString(currentViewMode);
		settingsString += " CameraMode="
				+ Constants.CameraMode.toString(currentCameraMode);
		return settingsString;
	}

	@Override
	public int compareTo(Object o) {
		Picture that = (Picture) o;
		return Long.compare(this.timeStamp, that.timeStamp);
	}
}
