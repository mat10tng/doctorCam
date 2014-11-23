package GUIControl;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ModePanel extends JPanel {

	public ModePanel() {
		super(new BorderLayout());
		ButtonGroup syncGroup = new ButtonGroup();
		ButtonGroup picGroup = new ButtonGroup();
		AsynchronizedButton asyncButton = new AsynchronizedButton();
		SynchronizedButton syncButton = new SynchronizedButton();
		IdleButton idleButton = new IdleButton();
		MovieButton movieButton = new MovieButton();
		AutoSyncButton autoSyncButton = new AutoSyncButton();
		AutoPicButton autoPicButton = new AutoPicButton();
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
