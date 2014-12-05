package GUIControl;

import java.awt.GridLayout;

import javax.swing.JPanel;

import client.ClientMonitor;
import client.PictureMonitor;

public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5947509521707507082L;

	public ControlPanel(ClientMonitor clientMonitor,PictureMonitor pictureMonitor) {
		super(new GridLayout(2, 1));
		this.add(new CameraPanel(clientMonitor));
		this.add(new ModePanel(clientMonitor,pictureMonitor));
	}

}
