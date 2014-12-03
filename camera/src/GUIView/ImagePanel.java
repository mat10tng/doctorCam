package GUIView;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private JLabel videoFeed;
	public ImagePanel(ImageIcon picture) {
		super();
		videoFeed = new JLabel(picture);
		videoFeed.setSize(640, 480);
		this.add(videoFeed);
	}
	
	public void setPicture(ImageIcon picture){
		videoFeed.setIcon(picture);
		videoFeed.repaint();
	}

}
