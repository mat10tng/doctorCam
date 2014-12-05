package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import client.ClientMonitor;
import client.ConnectionData;
import client.Constants;
import client.IpInformation;

public class RemoveCameraButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 7968778894256285503L;
	private ClientMonitor clientMonitor;
	private HashMap<Integer, IpInformation> ipinformation;
	private ImageIcon killcam;

	public RemoveCameraButton(HashMap<Integer, IpInformation> ipinformations,
			ClientMonitor clientMonitor) {
		super("Remove Cam");
		this.ipinformation = ipinformations;
		this.clientMonitor = clientMonitor;
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		addActionListener(this);
		killcam = new ImageIcon("killcamera.png");
		setIcon(killcam);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (ipinformation.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No connected cameras");
		} else {
			IpInformation removeip = (IpInformation) JOptionPane
					.showInputDialog(null, "Select Camera to kill",
							"Active Cameras", JOptionPane.QUESTION_MESSAGE,
							null, ipinformation.values().toArray(),
							ipinformation.get(0));
			if (removeip != null) {
				ipinformation.remove(removeip.getId());
				clientMonitor.addConnectionData(new ConnectionData(removeip,
						Constants.ConnectionActions.CLOSE_CONNECTION));
			}
		}
	}

}
