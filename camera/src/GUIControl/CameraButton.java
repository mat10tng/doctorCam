package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class CameraButton extends JButton implements ActionListener {
	private ImageIcon cam;
	private ImageIcon killcam;
	private Boolean pressed;
	private String name;

	public CameraButton(String name) {
		super(name);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		this.name = name;
		addActionListener(this);
		cam = new ImageIcon("camera.png");
		killcam = new ImageIcon("killcamera.png");
		pressed = false;
		setIcon(cam);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (pressed) {
			setText(name);
			setIcon(cam);
			pressed = false;
		} else {

			setText("kill " + name);
			setIcon(killcam);
			pressed = true;
		}
	}

}
