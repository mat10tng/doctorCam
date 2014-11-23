package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


//UNDONE
public class UpdateModeButton extends JButton implements ActionListener {
	private int syncMode;
	private int movieMode;

	
	public UpdateModeButton(){
		super("Update Mode");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
