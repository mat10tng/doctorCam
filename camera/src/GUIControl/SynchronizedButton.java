package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import client.Constants;
import client.PictureMonitor;

public class SynchronizedButton extends JRadioButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4293168026024657874L;
	private PictureMonitor pictureMonitor;
	public SynchronizedButton(PictureMonitor pictureMonitor) {
		super("Synchronize");
		this.pictureMonitor = pictureMonitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pictureMonitor.setForcedMode(Constants.ViewMode.SYNC_MODE);	
	}
}
