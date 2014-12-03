package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import client.Constants;
import client.PictureMonitor;

public class AutoSyncButton extends JRadioButton implements ActionListener{
	private PictureMonitor pictureMonitor;
	public AutoSyncButton(PictureMonitor pictureMonitor) {
		super("Auto");
		this.pictureMonitor=pictureMonitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pictureMonitor.setForcedMode(Constants.ViewMode.AUTO_MODE);
	}

}
