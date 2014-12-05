package GUIControl;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import client.ClientMonitor;
import client.Constants;
import client.IpInformation;

public class CameraPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9065614995531898669L;

	public CameraPanel(ClientMonitor monitor) {
		super(new GridLayout(1, 2));
		HashMap<Integer,IpInformation> ipinformation = new HashMap<Integer,IpInformation>();
		this.add(new AddCameraButton(ipinformation, monitor));
		this.add(new RemoveCameraButton(ipinformation, monitor));
	}

}
