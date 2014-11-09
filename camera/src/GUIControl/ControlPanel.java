package GUIControl;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {

	public ControlPanel() {
		super(new GridLayout(2, 1));
		this.add(new CameraPanel());
		this.add(new ModePanel());
	}

}
