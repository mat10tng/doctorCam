package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import client.Constants;
import client.PictureMonitor;

public class AsynchronizedButton extends JRadioButton implements ActionListener {
	private PictureMonitor pictureMonitor;

	public AsynchronizedButton(PictureMonitor pictureMonitor) {
		super("Asynchronize");
		addActionListener(this);
		this.pictureMonitor=pictureMonitor;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		pictureMonitor.setForcedMode(Constants.ViewMode.ASYNC_MODE);
	}

}
