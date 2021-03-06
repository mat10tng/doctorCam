package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import client.Constants;
import client.PictureMonitor;

public class ResetSyncButton extends JButton implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6577952940607441147L;
	private PictureMonitor pictureMonitor;

	public ResetSyncButton(PictureMonitor pictureMonitor) {
		super("Reset");
		this.setSize(50, 50);
		this.pictureMonitor  = pictureMonitor;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		pictureMonitor.setForcedMode(Constants.ViewMode.AUTO_MODE);
		
	}

}
