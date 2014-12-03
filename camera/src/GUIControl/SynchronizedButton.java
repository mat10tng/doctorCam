package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import client.ClientMonitor;
import client.Constants;

public class SynchronizedButton extends JRadioButton implements ActionListener {
	private ClientMonitor monitor;
	public SynchronizedButton(ClientMonitor monitor) {
		super("Synchronize");
		this.monitor = monitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		monitor.setViewMode(Constants.ViewMode.SYNC_MODE);	
	}
}
