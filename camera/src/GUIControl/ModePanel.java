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
		AutoButton autoButton = new AutoButton();
		syncGroup.add(asyncButton);
		syncGroup.add(syncButton);
		picGroup.add(movieButton);
		picGroup.add(idleButton);
		this.add(new JLabel("Modes:"), BorderLayout.PAGE_START);
		this.add(new ButtonGroupPanel(autoButton, null),
				BorderLayout.LINE_START);
		this.add(new ButtonGroupPanel(asyncButton, syncButton),
				BorderLayout.CENTER);
		this.add(new ButtonGroupPanel(idleButton, movieButton),
				BorderLayout.LINE_END);

	}

}
