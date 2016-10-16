/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import visualizationbasics.affinetransformation.TransformationMatrix2D;
import visualizationbasics.model.AbstractInstance;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class ProjectionInstance extends AbstractInstance {

    public ProjectionInstance(int id, float x, float y) {
        super(id);
        this.x = x;
        this.y = y;
        this.scalars = new ArrayList<Float>();
        this.showlabel = false;
        this.color = Color.BLACK;
    }

    public ProjectionInstance(int id) {
        this(id, 0.0f, 0.0f);
    }

    public void draw(BufferedImage image, boolean highquality) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        if (highquality) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (model != null) {
            TransformationMatrix2D mat = ((ProjectionModel) model).getViewportMatrix();

            //applying the transformation matrix to the (x,y) coordinates
            float[] coord = mat.apply(x, y);
            int xaux = (int) ((coord[0] <= 0) ? 1 : (coord[0] < image.getWidth()) ? coord[0] : image.getWidth() - 1);
            int yaux = (int) ((coord[1] <= 0) ? 1 : (coord[1] < image.getHeight()) ? coord[1] : image.getHeight() - 1);

            if (selected) {
                int rgbcolor = color.getRGB();
                image.setRGB(xaux - 1, yaux - 1, rgbcolor);
                image.setRGB(xaux, yaux - 1, rgbcolor);
                image.setRGB(xaux + 1, yaux - 1, rgbcolor);
                image.setRGB(xaux - 1, yaux, rgbcolor);
                image.setRGB(xaux, yaux, rgbcolor);
                image.setRGB(xaux + 1, yaux, rgbcolor);
                image.setRGB(xaux - 1, yaux + 1, rgbcolor);
                image.setRGB(xaux, yaux + 1, rgbcolor);
                image.setRGB(xaux + 1, yaux + 1, rgbcolor);

                g2.setColor(Color.GRAY);
                g2.drawRect(xaux - 2, yaux - 2, 4, 4);
            } else {
                int rgb = color.getRGB();
                float alpha = ((ProjectionModel) model).getAlpha();
                simulateAlpha(image, alpha, xaux - 1, yaux - 1, rgb);
                simulateAlpha(image, alpha, xaux, yaux - 1, rgb);
                simulateAlpha(image, alpha, xaux + 1, yaux - 1, rgb);
                simulateAlpha(image, alpha, xaux - 1, yaux, rgb);
                simulateAlpha(image, alpha, xaux, yaux, rgb);
                simulateAlpha(image, alpha, xaux + 1, yaux, rgb);
                simulateAlpha(image, alpha, xaux - 1, yaux + 1, rgb);
                simulateAlpha(image, alpha, xaux, yaux + 1, rgb);
                simulateAlpha(image, alpha, xaux + 1, yaux + 1, rgb);
            }

            //show the label associated to this instance
            if (showlabel) {
                drawLabel(g2, xaux, yaux);
            }
        }
    }

    public void drawLabel(Graphics2D g2, int x, int y) {
        //Show the instance label
        String label = toString().trim();

        if (label.trim().length() > 0) {
            if (label.length() > 100) {
                label = label.substring(0, 96) + "...";
            }
        }
        
        java.awt.FontMetrics metrics = g2.getFontMetrics(g2.getFont());

        int width = metrics.stringWidth(label);
        int height = metrics.getAscent();

        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.75f));
        g2.setPaint(java.awt.Color.WHITE);
        g2.fillRect(x + 3, y - 1 - height, width + 4, height + 4);
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(java.awt.Color.DARK_GRAY);
        g2.drawRect(x + 3, y - 1 - height, width + 4, height + 4);
        g2.drawString(label, x + 3, y);
    }

    public boolean isInside(Point point) {
        //applying the transformation matrix to the (x,y) coordinates
        if (model != null) {
            TransformationMatrix2D mat = ((ProjectionModel) model).getViewportMatrix();
            float[] coord = mat.apply(x, y);
            return (Math.abs((coord[0] - point.x)) <= 1 && Math.abs((coord[1] - point.y)) <= 1);
        }
        return false;
    }

    public boolean isInside(Rectangle rect) {
        //applying the transformation matrix to the (x,y) coordinates
        if (model != null) {
            TransformationMatrix2D mat = ((ProjectionModel) model).getViewportMatrix();
            float[] coord = mat.apply(x, y);
            return ((coord[0] >= rect.x) && (coord[0] - rect.x < rect.width))
                    && ((coord[1] >= rect.y) && (coord[1] - rect.y < rect.height));
        }
        return false;
    }

    public boolean isInside(Polygon polygon) {
        if (model != null) {
            //applying the transformation matrix to the (x,y) coordinates
            TransformationMatrix2D mat = ((ProjectionModel) model).getViewportMatrix();
            float[] coord = mat.apply(x, y);
            return polygon.contains(coord[0], coord[1]);
        }
        return false;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;

        if (model != null) {
            model.setChanged();
        }
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;

        if (model != null) {
            model.setChanged();
        }
    }

    public boolean isShowLabel() {
        return showlabel;
    }

    public void setShowLabel(boolean showlabel) {
        this.showlabel = showlabel;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setScalarValue(Scalar scalar, float value) {
        if (model == null) {
            throw new NullPointerException("This instance should be added to a model before using this method!");
        }

        if (scalar != null) {
            int index = ((ProjectionModel) model).getScalars().indexOf(scalar);

            if (scalars.size() > index) {
                scalars.set(index, value);
            } else {
                int size = scalars.size();
                for (int i = 0; i < index - size; i++) {
                    scalars.add(0.0f);
                }
                scalars.add(value);
            }

            scalar.store(value);
        }
    }

    public float getScalarValue(Scalar scalar) {
        if (model == null) {
            throw new NullPointerException("This instance should be added to a model before using this method!");
        }

        if (scalar != null) {
            int index = ((ProjectionModel) model).getScalars().indexOf(scalar);

            if (scalars.size() > index && index > -1) {
                return scalars.get(index);
            }
        }

        return 0.0f;
    }

    public float getNormalizedScalarValue(Scalar scalar) {
        if (model == null) {
            throw new NullPointerException("This instance should be added to a model before using this method!");
        }

        if (scalar != null) {
            int index = ((ProjectionModel) model).getScalars().indexOf(scalar);

            if (scalars.size() > index && index > -1) {
                float value = scalars.get(index);
                return (value - scalar.getMin()) / (scalar.getMax() - scalar.getMin());
            }
        }

        return 0.0f;
    }

    protected void simulateAlpha(BufferedImage image, float alpha, int x, int y, int rgb) {
        try {
            //C = (alpha * (A-B)) + B
            int oldrgb = image.getRGB(x, y);
            int oldr = (oldrgb >> 16) & 0xFF;
            int oldg = (oldrgb >> 8) & 0xFF;
            int oldb = oldrgb & 0xFF;

            int newr = (int) ((alpha * (((rgb >> 16) & 0xFF) - oldr)) + oldr);
            int newg = (int) ((alpha * (((rgb >> 8) & 0xFF) - oldg)) + oldg);
            int newb = (int) ((alpha * ((rgb & 0xFF) - oldb)) + oldb);

            int newrgb = newb | (newg << 8) | (newr << 16);
            image.setRGB(x, y, newrgb);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("simulateAlpha: ArrayIndexOutOfBoundsException (" + x + "," + y + ")");
        }
    }
    
    protected ArrayList<Float> scalars;
    protected float x;
    protected float y;
    protected boolean showlabel;
    protected Color color;
}
