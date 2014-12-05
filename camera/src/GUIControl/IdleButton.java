package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import client.ClientMonitor;
import client.Constants;

public class IdleButton extends JRadioButton implements ActionListener{
	private ClientMonitor monitor;
	
	public IdleButton(ClientMonitor monitor) {
		super("Idle");
		this.monitor = monitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		monitor.setCameraMode(Constants.CameraMode.IDLE_MODE);

	}

}
