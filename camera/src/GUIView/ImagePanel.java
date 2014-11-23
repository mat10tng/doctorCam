package GUIView;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private JLabel videoFeed;
	public ImagePanel(ImageIcon picture) {
		videoFeed = new JLabel(picture);
		this.add(videoFeed);
	}
	
	public void setPicture(ImageIcon picture){
		
	}

}
