package GUIView;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import client.Picture;

public class ViewWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1395053519827135482L;
	private ImagePanel imagePanel;
	private ImageIcon videoFeed;
	private JLabel settings;
	private long lastPictureUpdated;
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
		this.setSize(700, 550);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		lastPictureUpdated=0;
	}

	public void updateView(Picture picture) {
		
		String fpsString="0";
		if(lastPictureUpdated!=0){
			long now=System.currentTimeMillis();
			long timeDifference=now-lastPictureUpdated;
			double fps=1000.0/timeDifference;
			fpsString=String.format("%.2f", fps);
		}
		lastPictureUpdated=System.currentTimeMillis();
		String settingsString="Settings: ";
		settingsString+=picture.getModeString();
		settingsString+=" fps="+fpsString;
		settingsString+=" latency="+picture.getLatencyInMS();
		settings.setText(settingsString);
//		this.setForeground(picture.currentCameraMode == Constants.CameraMode.MOVIE_MODE ? Color.cyan : Color.LIGHT_GRAY);
		imagePanel.setPicture(picture.getPicture());
	}


	public void close() {
		this.dispose();
	}


}
