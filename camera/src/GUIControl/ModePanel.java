package GUIControl;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientMonitor;
import client.PictureMonitor;

public class ModePanel extends JPanel {

	public ModePanel(ClientMonitor clientMonitor,PictureMonitor pictureMonitor) {
		super(new BorderLayout());
		ButtonGroup syncGroup = new ButtonGroup();
		ButtonGroup picGroup = new ButtonGroup();
		ResetPicButton resetPicButton = new ResetPicButton(clientMonitor);
		ResetSyncButton resetSyncButton = new ResetSyncButton(pictureMonitor);
		AsynchronizedButton asyncButton = new AsynchronizedButton(pictureMonitor);
		SynchronizedButton syncButton = new SynchronizedButton(pictureMonitor);
		IdleButton idleButton = new IdleButton(clientMonitor);
		MovieButton movieButton = new MovieButton(clientMonitor);
		AutoSyncButton autoSyncButton = new AutoSyncButton(pictureMonitor);
		AutoPicButton autoPicButton = new AutoPicButton(clientMonitor);
		syncGroup.add(asyncButton);
		syncGroup.add(syncButton);
		syncGroup.add(autoSyncButton);
		picGroup.add(movieButton);
		picGroup.add(idleButton);
		picGroup.add(autoPicButton);
		autoSyncButton.setSelected(true);
		autoPicButton.setSelected(true);
		this.add(new JLabel("Modes:"), BorderLayout.PAGE_START);
		this.add(new ButtonGroupPanel(resetSyncButton,autoSyncButton, asyncButton,syncButton),
				BorderLayout.LINE_START);

		this.add(new ButtonGroupPanel(resetPicButton,autoPicButton,idleButton, movieButton),
				BorderLayout.LINE_END);

	}

}
