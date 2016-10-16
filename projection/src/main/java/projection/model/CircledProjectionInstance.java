/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import visualizationbasics.affinetransformation.TransformationMatrix2D;

/**
 *
 * @author paulovich
 */
public class CircledProjectionInstance extends LabeledProjectionInstance {

	public CircledProjectionInstance(String label, int id, float x, float y, int size) {

		super(label, id, x, y, size);
	}
		
    public CircledProjectionInstance(String label, int id, float x, float y) {
        super(label, id, x, y);
    }

    public CircledProjectionInstance(int id, float x, float y) {
        super(null, id, x, y);
    }

    public CircledProjectionInstance(int id) {
        this(id, 0, 0);
    }

    public CircledProjectionInstance(String label, int id) {
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
                int inssize = size;

                g2.setStroke(new BasicStroke(4.0f));
                inssize *= 1.5;

                g2.setColor(getColor());
                g2.fillOval(xaux - inssize, yaux - inssize, inssize * 2, inssize * 2);

                g2.setColor(Color.DARK_GRAY);
                g2.drawOval(xaux - inssize, yaux - inssize, inssize * 2, inssize * 2);

                g2.setStroke(new BasicStroke(1.0f));
            } else {
                int inssize = size;

                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, ((ProjectionModel) model).getAlpha()));

                g2.setColor(getColor());
                g2.fillOval(xaux - inssize, yaux - inssize, inssize * 2, inssize * 2);

                g2.setColor(Color.BLACK);
                g2.drawOval(xaux - inssize, yaux - inssize, inssize * 2, inssize * 2);

                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
            }

            //show the label associated to this instance
            if (showlabel) {
                drawLabel(g2, xaux, yaux);
            }
        }
    }

    @Override
    public boolean isInside(Point point) {
        if (model != null) {
            //applying the transformation matrix to the (x,y) coordinates
            TransformationMatrix2D mat = ((ProjectionModel) model).getViewportMatrix();
            float[] coord = mat.apply(x, y);
            return (Math.hypot(point.x - coord[0], point.y - coord[1]) <= size);
        }
        return false;
    }
}
