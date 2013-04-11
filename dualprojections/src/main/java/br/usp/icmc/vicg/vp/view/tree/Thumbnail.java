package br.usp.icmc.vicg.vp.view.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Thumbnail extends JPanel {

	private static final long serialVersionUID = -717704078928091686L;
	
	public static final int WIDTH = 240;
	public static final int HEIGHT = 125;
	
	public Thumbnail(JPanel panel) {
		
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		Dimension size = panel.getSize(); 
		BufferedImage buffer = new BufferedImage(size.width, size.height, 
				BufferedImage.TYPE_INT_RGB);
		panel.paint(buffer.getGraphics());
		
		Image scaledImage = buffer.getScaledInstance(
				Thumbnail.WIDTH, Thumbnail.HEIGHT,Image.SCALE_SMOOTH);
		
	    JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
	    
	    this.add(picLabel);
	    this.repaint();
	}
}
