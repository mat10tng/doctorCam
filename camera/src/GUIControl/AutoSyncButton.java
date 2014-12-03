package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import client.ClientMonitor;
import client.Constants;

public class AutoSyncButton extends JRadioButton implements ActionListener{
	private ClientMonitor monitor;
	public AutoSyncButton(ClientMonitor monitor) {
		super("Auto");
		this.monitor = monitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		monitor.setViewMode(Constants.ViewMode.AUTO_MODE);
	}

}
