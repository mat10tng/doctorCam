package GUIControl;

import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

public class ButtonGroupPanel extends JPanel {

	public ButtonGroupPanel(AbstractButton button1, AbstractButton button2) {
		super(new GridLayout(2, 1));
		if (button1 != null) {
			this.add(button1);
		}
		if (button2 != null) {
			this.add(button2);
		}
	}

}
