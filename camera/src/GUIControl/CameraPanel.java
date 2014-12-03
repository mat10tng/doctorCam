package GUIControl;

import java.awt.GridLayout;

import javax.swing.JPanel;

import client.ClientMonitor;

public class CameraPanel extends JPanel {

	public CameraPanel(ClientMonitor monitor) {
		super(new GridLayout(1,2));
		this.add(new CameraButton("cam 1", "tempip1",2001,1, monitor));
		this.add(new CameraButton("cam 2","tempip2",2002,2, monitor));
	}

}
