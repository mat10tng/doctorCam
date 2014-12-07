package GUIView;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3974631698609579489L;
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
