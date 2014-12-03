package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import client.ClientMonitor;
import client.Constants;

public class AsynchronizedButton extends JRadioButton implements ActionListener {
	private ClientMonitor monitor;

	public AsynchronizedButton(ClientMonitor monitor) {
		super("Asynchronize");
		addActionListener(this);
		this.monitor = monitor;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		monitor.setViewMode(Constants.ViewMode.ASYNC_MODE);
	}

}
