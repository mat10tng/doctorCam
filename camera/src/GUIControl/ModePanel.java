package GUIControl;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientMonitor;

public class ModePanel extends JPanel {

	public ModePanel(ClientMonitor monitor) {
		super(new BorderLayout());
		ButtonGroup syncGroup = new ButtonGroup();
		ButtonGroup picGroup = new ButtonGroup();
		AsynchronizedButton asyncButton = new AsynchronizedButton(monitor);
		SynchronizedButton syncButton = new SynchronizedButton(monitor);
		IdleButton idleButton = new IdleButton(monitor);
		MovieButton movieButton = new MovieButton(monitor);
		AutoSyncButton autoSyncButton = new AutoSyncButton(monitor);
		AutoPicButton autoPicButton = new AutoPicButton(monitor);
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
