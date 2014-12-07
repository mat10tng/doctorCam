package GUIControl;

import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

public class ButtonGroupPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4070068888165902241L;

	public ButtonGroupPanel(AbstractButton button1, AbstractButton button2, AbstractButton button3, AbstractButton button4) {
		super(new GridLayout(4, 1));
			this.add(button1);
			this.add(button2);
			this.add(button3);
			this.add(button4);
	}

}
