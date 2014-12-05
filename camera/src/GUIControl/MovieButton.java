package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import client.ClientMonitor;
import client.Constants;

public class MovieButton extends JRadioButton implements ActionListener {
	private ClientMonitor monitor;
	public MovieButton(ClientMonitor monitor) {
		super("Movie");
		this.monitor = monitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		monitor.setCameraMode(Constants.CameraMode.MOVIE_MODE);
	}

}
