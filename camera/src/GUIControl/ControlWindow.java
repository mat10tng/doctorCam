package GUIControl;

import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import client.*;

public class ControlWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5227371010759534852L;

	public ControlWindow(ClientMonitor clientMonitor,
			PictureMonitor pictureMonitor) {
		super("Control");
		ImageIcon img = new ImageIcon("control.png");
		setIconImage(img.getImage());
		this.add(new ControlPanel(clientMonitor, pictureMonitor));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		setSize(300, 350);
		setResizable(false);
	}

	public static void main(String args[]) {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO ERROR HANDLING
		}
		ClientMonitor cmonitor = new ClientMonitor();
		PictureMonitor pmonitor = new PictureMonitor();
		ProcessHandler procHand = new ProcessHandler(cmonitor, pmonitor);
		PictureHandler picHand = new PictureHandler(cmonitor, pmonitor);
		procHand.start();
		picHand.start();
		new ControlWindow(cmonitor, pmonitor);
	}

	public void windowClosing(WindowEvent e) {
		// send your socket its close message and shut everything down
		System.exit(0);
	}

}