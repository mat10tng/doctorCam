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
		if (button1 != null) {
			this.add(button1);
		}
		if (button2 != null) {
			this.add(button2);
		}
		if(button3 != null){
			this.add(button3);
		}
		if(button4 != null){
			this.add(button4);
		}
	}

}
