/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import visualizationbasics.affinetransformation.TransformationMatrix2D;

/**
 *
 * @author PC
 */
public class LabeledProjectionInstance extends ProjectionInstance {

    public LabeledProjectionInstance(String label, int id, float x, float y) {
        super(id, x, y);
        this.label = label;
        this.size = 4;
    }

    public LabeledProjectionInstance(String label, int id) {
        this(label, id, 0, 0);
    }

    @Override
    public void draw(BufferedImage image, boolean highquality) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        if (highquality) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (model != null) {
            //applying the transformation matrix to the (x,y) coordinates
            TransformationMatrix2D mat = ((ProjectionModel) model).getViewportMatrix();
            float[] coord = mat.apply(x, y);
            int xaux = (int) ((coord[0] <= 0) ? 1 : (coord[0] < image.getWidth()) ? coord[0] : image.getWidth() - 1);
            int yaux = (int) ((coord[1] <= 0) ? 1 : (coord[1] < image.getHeight()) ? coord[1] : image.getHeight() - 1);

            if (selected) {
                int rgbcolor = color.getRGB();
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        image.setRGB(xaux + i, yaux + j, rgbcolor);
                    }
                }

                g2.setColor(Color.GRAY);
                g2.drawRect(xaux - 2, yaux - 2, size + 3, size + 3);
                g2.drawRect(xaux - 1, yaux - 1, size + 1, size + 1);

            } else {
                int rgb = color.getRGB();
                float alpha = ((ProjectionModel) model).getAlpha();

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        simulateAlpha(image, alpha, xaux + i, yaux + j, rgb);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        if (label == null) {
            return Integer.toString(id);
        }

        return label;
    }

    @Override
    public boolean isInside(Point point) {
        if (model != null) {
            //applying the transformation matrix to the (x,y) coordinates
            TransformationMatrix2D mat = ((ProjectionModel) model).getViewportMatrix();
            float[] coord = mat.apply(x, y);
            return (Math.abs(point.x - coord[0]) <= size && Math.abs(point.y - coord[1]) <= size);
        }
        return false;
    }
    
    protected int size;
    protected String label;
}
