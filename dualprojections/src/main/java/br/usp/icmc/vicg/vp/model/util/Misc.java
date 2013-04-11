package br.usp.icmc.vicg.vp.model.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Misc {

	public static double[][] convertFloatsToDoubles(float[][] input) {
        if (input == null) {
            return null; // Or throw an exception - your choice
        }
        double[][] output = new double[input.length][];
        for (int i = 0; i < input.length; i++) {
            output[i] = new double[input[i].length];
            for (int j = 0; j < input[i].length; j++) {
                output[i][j] = input[i][j];
            }
        }
        return output;
    }
	
	public static double[] convertFloatsToDoubles(float[] input) {
		
        if (input == null) {
            return null; // Or throw an exception - your choice
        }
        double[] output = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i];
        }
        return output;
    }
	
	public static void saveToPngImageFile(JPanel panel, 
			String output) throws IOException {
		try {
			Dimension size = panel.getSize(); //get the current size of the panel
			BufferedImage buffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
			panel.paint(buffer.getGraphics());
			ImageIO.write(buffer, "png", new File(output));
		} catch (IOException ex) {
			Logger.getLogger(panel.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
}
