package GUIControl;

import java.awt.GridLayout;

import javax.swing.JPanel;

import client.ClientMonitor;
import client.Constants;

public class CameraPanel extends JPanel {

	public CameraPanel(ClientMonitor monitor) {
		super(new GridLayout(1, 2));
		this.add(new CameraButton("cam 1", Constants.DebugIps.IP1,
				Constants.DebugIps.PORT1, Constants.DebugIps.ID1, monitor));
		this.add(new CameraButton("cam 2", Constants.DebugIps.IP2,
				Constants.DebugIps.PORT2, Constants.DebugIps.ID2, monitor));
	}

}
