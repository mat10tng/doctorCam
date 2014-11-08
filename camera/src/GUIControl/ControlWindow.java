package GUIControl;

import javax.swing.JFrame;

public class ControlWindow extends JFrame {

	public ControlWindow() {
		super("Control");
		this.add(new ControlPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		pack();
	}
	
	public static void main(String args[]){
		new ControlWindow();
	}
}
