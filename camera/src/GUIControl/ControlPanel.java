package GUIControl;

import java.awt.GridLayout;

import javax.swing.JPanel;

import client.ClientMonitor;

public class ControlPanel extends JPanel {

	public ControlPanel(ClientMonitor monitor) {
		super(new GridLayout(2, 1));
		this.add(new CameraPanel(monitor));
		this.add(new ModePanel(monitor));
	}

}
