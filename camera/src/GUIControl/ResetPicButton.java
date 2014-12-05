package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import client.ClientMonitor;
import client.Constants;

public class ResetPicButton extends JButton implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 271179536632315336L;
	private ClientMonitor clientMonitor;

	public ResetPicButton(ClientMonitor clientMonitor) {
		super("Reset");
		this.setSize(50, 50);
		this.clientMonitor = clientMonitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		clientMonitor.setCameraMode(Constants.CameraMode.AUTO_MODE);
		
	}

}
