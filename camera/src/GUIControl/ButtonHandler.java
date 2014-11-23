package GUIControl;

import java.util.concurrent.Semaphore;

public class ButtonHandler {
	private Semaphore countingSem;
	
	
	public ButtonHandler(){
		//Set to -1 so that it can't be acquired before released
		countingSem = new Semaphore(-1);
	}
		

}
