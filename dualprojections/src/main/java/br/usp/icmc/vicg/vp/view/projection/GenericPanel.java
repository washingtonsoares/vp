package br.usp.icmc.vicg.vp.view.projection;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JPanel;

import projection.model.ProjectionModel;
import visualizationbasics.coordination.AbstractCoordinator;
import visualizationbasics.model.AbstractModel;
import visualizationbasics.view.ModelViewer;

public class GenericPanel extends JPanel implements ModelViewer {
	
	private static final long serialVersionUID = 1L;
	
	protected static final int PADDING = 60;
	
	protected AbstractModel model;
	private ArrayList<AbstractCoordinator> coordinators;
	
	protected Rectangle viewport;
	protected BufferedImage image;
	protected boolean highQuality;
	
	public GenericPanel(AbstractModel model) {
		
		this.coordinators = new ArrayList<AbstractCoordinator>();
		this.highQuality = true;
		
		this.setModel(model);
		
		this.setBackground(java.awt.Color.WHITE);
		
		viewport = new Rectangle();
		
		this.addComponentListener(new ResizeListener());
	}
	
	public void setModel(AbstractModel model) {
		
        if (this.model != null) {
        	
            detachFromObserver();
        }

        this.model = model;

        if (model != null) {
        	
            attachToObserver();
        }
    }
	
	public AbstractModel getModel() {
		
        return model;
    }
	
	public void setHighQualityRender(boolean highqualityrender) {

		this.highQuality = highqualityrender;

		clearImage();
		repaint();
	}
	
	public boolean getHighQualityRender() {

		return highQuality;
	}

	public Container getContainer() {
		
        return this;
    }
	
	@Override
	public void paintComponent(java.awt.Graphics g) {
		
		super.paintComponent(g);

		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

		// Draw elements
		drawElements(g2);
	}
	
	private void drawElements(java.awt.Graphics2D g) {

		if (model != null && image == null) {
			
			// Get the current size of the panel
			Dimension size = getSize(); 
			
			// Create image buffer
			image = new BufferedImage(size.width, size.height, 
					BufferedImage.TYPE_INT_RGB);

			// Create graphic buffer
			java.awt.Graphics2D g2Buffer = image.createGraphics();
			
			// Sets context color
			g2Buffer.setColor(this.getBackground());
			
			// Fills background
			g2Buffer.fillRect(0, 0, size.width, size.height);

			// Configure rendering quality
			if (highQuality) {
				g2Buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
			} else {
				g2Buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_OFF);
			}

			// Draw elements on image
			((ProjectionModel) model).draw(image, highQuality);

			// Release graphics buffer
			g2Buffer.dispose();
		}

		// Now draw the image
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {

		if (model != null) {
			clearImage();
			repaint();
		}
	}
	
	private void detachFromObserver() {
		
		 this.model.deleteObserver(this);

         for (int i = 0; i < coordinators.size(); i++) {
             coordinators.get(i).deleteModel(this.model);
         }
	}
	
	private void attachToObserver() {
		
		model.addObserver(this);
	}
	
	public void addCoordinator(AbstractCoordinator coordinator) {
		
        if (coordinator != null) {
        	
            if (!coordinators.contains(coordinator)) {
            	
                coordinators.add(coordinator);
                coordinator.addModel(model);
            }
        }
    }
	
	public ArrayList<AbstractCoordinator> getCoordinators() {
		
        return coordinators;
    }
	
	protected void reshapeViewport() {
		
		int width = getSize().width - PADDING;
		int height = getSize().height - PADDING;
		
		if (width > PADDING && height > PADDING) {
			
			viewport.x = PADDING / 2;
			viewport.y = PADDING / 2;
			viewport.width = width;
			viewport.height = height;
			// TODO: Change this to a generic fashion
			((ProjectionModel) model).setViewport(viewport);				
		}
		updateImage();
	}

	public void clearImage() {
		
		image = null;
	}
	
	public void updateImage() {

		this.clearImage();
		this.repaint();
	}
	
	public void setViewerBackground(Color bg) {

		setBackground(bg);
		clearImage();
		repaint();
	}

	class ResizeListener extends ComponentAdapter {
		
		@Override
		public void componentResized(ComponentEvent arg0) {
			
			reshapeViewport();
		}
	}
}
