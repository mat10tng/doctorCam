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
		this.add(new JLabel("Modes:"), BorderLayout.PAGE_START);
		this.add(new ButtonGroupPanel(autoSyncButton, asyncButton,syncButton),
				BorderLayout.LINE_START);

		this.add(new ButtonGroupPanel(autoPicButton,idleButton, movieButton),
				BorderLayout.LINE_END);

	}

}
