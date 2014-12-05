package client;

public class Constants {
	public static class CameraMode {
		public static final int IDLE_MODE = 0;
		public static final int MOVIE_MODE = 1;
		public static final int AUTO_MODE = 2;
		public static String toString(int mode){
			switch(mode){
			case(IDLE_MODE):
				return "idle mode";
			case(MOVIE_MODE):
				return "movie mode";
			case(AUTO_MODE):
				return "auto mode";
			default:
				return "undefined camera mode";
			}
		}
		private static Byte[] getBytes(int mode){
			Byte[] dataPackage=new Byte[2];
			dataPackage[0]=1;
			dataPackage[1]=(byte)mode;
			return dataPackage;
		}
		public static Byte[] getMovieBytes(){
			return getBytes(MOVIE_MODE);
		}
		public static Byte[] getIdleBytes(){
			return getBytes(IDLE_MODE);
		}
		public static Byte[] getAutoBytes(){
			return getBytes(AUTO_MODE);
		}
		public static Byte[] getModeBytes(int mode){
			return getBytes(mode);
		}
	}
	public static class ViewMode {
		public static final int SYNC_MODE = 0;
		public static final int AUTO_MODE = 1;
		public static final int ASYNC_MODE = 2;
		
		public static String toString(int mode){
			switch(mode){
			case(SYNC_MODE):
				return "sync mode";
			case(AUTO_MODE):
				return "auto mode";
			case(ASYNC_MODE):
				return "async mode";
			default:
				return "undefined view mode";
			}
		}
	}

	public class ConnectionActions {
		public static final int CLOSE_CONNECTION = 0;
		public static final int OPEN_CONNECTION = 1;
	}

	public class DebugIps {
		public static final String IP1 = "localhost";
		public static final int PORT1 = 2001;
		public static final int ID1 = 1;
		public static final String IP2 = "localhost";
		public static final int PORT2 = 2002;
		public static final int ID2 = 2;
	}
}
