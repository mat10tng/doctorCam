package GUIControl;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class CameraPanel extends JPanel {

	public CameraPanel() {
		super(new GridLayout(1,2));
		this.add(new CameraButton("cam 1"));
		this.add(new CameraButton("cam 2"));
	}

}
