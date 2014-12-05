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
		//make left button group
		ButtonGroup syncGroup = new ButtonGroup();
		// create all da left buttons
		ResetSyncButton resetSyncButton = new ResetSyncButton(pictureMonitor);
		AsynchronizedButton asyncButton = new AsynchronizedButton(pictureMonitor);
		SynchronizedButton syncButton = new SynchronizedButton(pictureMonitor);
		AutoSyncButton autoSyncButton = new AutoSyncButton(pictureMonitor);
		// add all da buttons to da house
		syncGroup.add(asyncButton);
		syncGroup.add(syncButton);
		syncGroup.add(autoSyncButton);
		// we start with auto 
		autoSyncButton.setSelected(true);

		//make right button group
		ButtonGroup picGroup = new ButtonGroup();
		// create all da right buttons
		IdleButton idleButton = new IdleButton(clientMonitor);
		MovieButton movieButton = new MovieButton(clientMonitor);
		ResetPicButton resetPicButton = new ResetPicButton(clientMonitor);
		AutoPicButton autoPicButton = new AutoPicButton(clientMonitor);
		//add to picGroup
		picGroup.add(movieButton);
		picGroup.add(idleButton);
		picGroup.add(autoPicButton);
		//also start with autoMode
		autoPicButton.setSelected(true);
		


		
		this.add(new JLabel("Modes:"), BorderLayout.PAGE_START);
		this.add(new ButtonGroupPanel(resetSyncButton,autoSyncButton, asyncButton,syncButton),
				BorderLayout.LINE_START);

		this.add(new ButtonGroupPanel(resetPicButton,autoPicButton,idleButton, movieButton),
				BorderLayout.LINE_END);

	}

}
