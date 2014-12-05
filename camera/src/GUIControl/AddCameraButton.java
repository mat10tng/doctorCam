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

public class AddCameraButton extends JButton implements ActionListener {

	private static final long serialVersionUID = -9116970192513641279L;
	private ClientMonitor clientMonitor;
	private HashMap<Integer, IpInformation> ipinformation;
	private ImageIcon cam;


	public AddCameraButton(HashMap<Integer, IpInformation> ipinformations,
			ClientMonitor clientMonitor) {
		super("Add Cam");
		this.ipinformation = ipinformations;
		this.clientMonitor = clientMonitor;
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		addActionListener(this);
		cam = new ImageIcon("camera.png");
		setIcon(cam);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int id = 0;
		boolean found = false;
		for (int i = 0; i < 8; i++) {
			if (!ipinformation.containsKey(i)) {
				id = i;
				found = true;
				break;
			}

		}
		if (found) {
			String ip = JOptionPane.showInputDialog("Enter ip address: ");
			int port = Integer.parseInt(JOptionPane
					.showInputDialog("Enter port: "));
			ipinformation.put(id, new IpInformation(ip, port,id));
			clientMonitor.addConnectionData(new ConnectionData(ip,port,id,Constants.ConnectionActions.OPEN_CONNECTION));
		}else{
			JOptionPane.showMessageDialog(null, "Cannot connect to more cameras");
		}

	}

}
