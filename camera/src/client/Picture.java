package client;

import javax.swing.ImageIcon;

/**
 * @author Shan
 *
 */
public class Picture {
	private ImageIcon picture;
	private long waitTime;
	private long timeStamp;
	private int id;
	public int currentViewMode;
	public int currentCameraMode;
	/**
	 * Converts a data package to a picture object.
	 * Represents a picture and all data belonging to that picture
	 * @param data: The data package to be converted
	 * @param id: which network this data originated from
	 */
	public Picture(Byte[] data, int id) {
		this.id  = id;
		waitTime = 0;
		byte[] byteTimestamp = new byte[8];
		byte[] bytePicture = new byte[data.length - 9];
		for (int i = 0; i < data.length; i++) {
			if (i < 8) {
				byteTimestamp[i] = data[i].byteValue();
			} else {
				bytePicture[i - 8] = data[i].byteValue();
			}
		}
		timeStamp=byteTimeToLongTime(byteTimestamp);
		picture=new ImageIcon(bytePicture);
	}
	private long byteTimeToLongTime(byte[] byteTime){
		long time=0;
		for(int i=0;i<8;i++){
			time=time<<8;
			long addTime=(long)byteTime[i];
			time+=addTime;
		}
		return time;
	}
	/**
	 * Returns the picture in byte format
	 * @return: picture in the form of bytes
	 */
	public ImageIcon getPicture(){
		return picture;
	}
	/**
	 * Returns a Timestamp from when the picture was taken
	 * @return: Timestamp in the form of bytes
	 */
	public long getTimeStamp(){
		return timeStamp;
	}
	
	public long getWaitTime(){
		return waitTime;
	}
	
	public void setWaitTime(long latestTime){
		this.waitTime = timeStamp-latestTime;
	}
	/**
	 * The id the data/ picture came from.
	 * @return: id of what network as an int 
	 */
	public int getId(){
		return id;
	}
	public long getLatencyInMS(){
		return System.currentTimeMillis()-timeStamp;
	}
	public String getModeString(){
		String settingsString="";
		settingsString+=" ViewMode="+Constants.ViewMode.toString(currentViewMode);
		settingsString+=" CameraMode="+Constants.CameraMode.toString(currentCameraMode);
		return settingsString;
	}
}
