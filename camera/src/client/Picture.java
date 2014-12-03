package client;

/**
 * @author Shan
 *
 */
public class Picture {
	private byte[] picture;
	private byte[] timestamp;
	private int id;

	/**
	 * Converts a data package to a picture object.
	 * Represents a picture and all data belonging to that picture
	 * @param data: The data package to be converted
	 * @param id: which network this data originated from
	 */
	public Picture(Byte[] data, int id) {
		this.id  = id;
		timestamp = new byte[8];
		picture = new byte[data.length - 9];
		for (int i = 0; i < data.length; i++) {
			if (i < 8) {
				timestamp[i] = data[i].byteValue();
			} else {
				picture[i - 8] = data[i].byteValue();
			}
		}
	}
	
	/**
	 * Returns the picture in byte format
	 * @return: picture in the form of bytes
	 */
	public byte[] getPicture(){
		return picture;
		
	}
	/**
	 * Returns a Timestamp from when the picture was taken
	 * @return: Timestamp in the form of bytes
	 */
	public long getTimeStamp(){
		return 0;
	}
	
	public long getWaitTime(){
		return 0;
	}
	
	/**
	 * The id the data/ picture came from.
	 * @return: id of what network as an int 
	 */
	public int getId(){
		return id;
	}

}
