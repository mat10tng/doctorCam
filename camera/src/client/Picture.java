package client;

public class Picture {
	private byte[] picture;
	private byte[] timestamp;
	private int id;

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
	
	public byte[] getPicture(){
		return picture;
		
	}
	public byte[] getTimeStamp(){
		return timestamp;
	}
	
	public int getId(){
		return id;
	}

}
