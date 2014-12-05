package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import client.ClientMonitor;
import client.Constants;

public class MovieButton extends JRadioButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -286773116316524906L;
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
