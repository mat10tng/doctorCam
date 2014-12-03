package GUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import client.ClientMonitor;
import client.ConnectionData;
import client.Constants;

public class CameraButton extends JButton implements ActionListener {
	private ClientMonitor clientMonitor;
	private String ip;
	private int port;
	private int id;
	private ImageIcon cam;
	private ImageIcon killcam;
	private Boolean pressed;
	private String name;

	public CameraButton(String name, String ip, int port, int id, ClientMonitor clientMonitor) {
		super(name);
		this.ip=ip;
		this.port=port;
		this.id=id;
		this.clientMonitor=clientMonitor;
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		this.name = name;
		addActionListener(this);
		cam = new ImageIcon("camera.png");
		killcam = new ImageIcon("killcamera.png");
		pressed = false;
		setIcon(cam);
	}

	/* 
	 * TODO fix proper connection data
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (pressed) {
			setText(name);
			setIcon(cam);
			pressed = false;
			
			clientMonitor.addConnectionData(new ConnectionData(ip,port,id,Constants.ConnectionActions.CLOSE_CONNECTION));
		} else {

			setText("kill " + name);
			setIcon(killcam);
			pressed = true;
			clientMonitor.addConnectionData(new ConnectionData(ip,port,id,Constants.ConnectionActions.OPEN_CONNECTION));
		}
	}

}
