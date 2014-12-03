package GUIView;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import client.Picture;

public class ViewWindow extends JFrame {
	private ImagePanel imagePanel;
	private ImageIcon videoFeed;
	private JLabel settings;

	public ViewWindow(String id) {
		super(id);
		ImageIcon img = new ImageIcon("screen.png");
		videoFeed = img;
		settings = new JLabel("placeholder settings");
		imagePanel = new ImagePanel(videoFeed);
		setIconImage(img.getImage());
		setLayout(new BorderLayout());
		this.add(settings, BorderLayout.SOUTH);
		this.add(imagePanel, BorderLayout.NORTH);
		this.setSize(700, 500);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void updateView(Picture picture) {
		imagePanel.setPicture(picture.getPicture());
		// Update picture and settings
	}

	public static void main(String args[]) {
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager
					.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new ViewWindow("Camera 1");
	}

	public void close() {
		this.dispose();
		
	}

}
