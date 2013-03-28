package br.usp.vp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import projection.model.ProjectionInstance;
import projection.model.ProjectionModel;
import projection.model.Scalar;
import visualizationbasics.affinetransformation.TransformationMatrix2D;
import visualizationbasics.color.ColorScalePanel;
import visualizationbasics.coordination.AbstractCoordinator;
import visualizationbasics.model.AbstractInstance;
import visualizationbasics.model.AbstractModel;
import visualizationbasics.view.JPanelModelViewer;
import visualizationbasics.view.selection.AbstractSelection;

public class CopyOfProjectionPanel extends JPanelModelViewer {

	private static final long serialVersionUID = 1L;

	// Configuration
	private boolean highQuality = true;
	private boolean showLabbel = true;
	private boolean moveInstances = true;

	// Current Scalar
	private Scalar currentScalar;

	// Panel
	private Rectangle viewport;
	private BufferedImage image;
	private ColorScalePanel colorscale;

	// Selection
	private AbstractSelection selection;
	private ProjectionInstance selectedItems;
	private Polygon selectionPolygon;
	private Point selectionSource;
	private Point selectionTarget;
	private Color selectionColor;

	// Labels
	private ProjectionInstance labelsItems;
	private Point labelPositions;

	public CopyOfProjectionPanel(AbstractModel model, Dimension size) {

		this.selectionColor = java.awt.Color.RED;
		this.setBackground(java.awt.Color.WHITE);

//		this.addMouseMotionListener(new MouseMotionListener());
//		this.addMouseListener(new MouseClickedListener());

//		this.setSize(size);
		this.setModel(model);
	}

	@Override
	public void setModel(AbstractModel model) {

		if (model instanceof ProjectionModel) {

			if (model != null) {

				super.setModel(model);

				Scalar scalar = ((ProjectionModel) model).getSelectedScalar();

				if (scalar != null) {

					currentScalar = scalar;
				} 
				else {

					currentScalar = (((ProjectionModel) model).getScalars().get(0));
				}
				
				((ProjectionModel) model).setSelectedScalar(currentScalar);

				colorscale = new ColorScalePanel(null);
				colorscale.setColorTable(((ProjectionModel) model).getColorTable());
				colorscale.setPreferredSize(new Dimension(200, 12));
				colorscale.setBackground(getBackground());

				removeAll();
//				this.add(colorscale);

				//defining the viewport size
				viewport = new Rectangle(30, 30, getSize().width - 60, getSize().height - 60);
				((ProjectionModel) model).setViewport(viewport);

				clearImage();
				repaint();
			}
		}
	}

	public void setViewerBackground(Color bg) {

		setBackground(bg);
		clearImage();
		repaint();
	}

	public Scalar getCurrentScalar() {

		return currentScalar;
	}

	public boolean getHighQualityRender() {

		return highQuality;
	}

	public void setHighQualityRender(boolean highqualityrender) {

		this.highQuality = highqualityrender;

		clearImage();
		repaint();
	}

	public boolean getShowInstanceLabel() {

		return showLabbel;
	}

	public void setShowInstanceLabel(boolean showinstancelabel) {

		this.showLabbel = showinstancelabel;

		clearImage();
		repaint();
	}

	public boolean isMoveInstances() {

		return moveInstances;
	}

	public void setMoveInstance(boolean moveinstances) {

		this.moveInstances = moveinstances;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (model != null) {
			clearImage();
			repaint();
		}
	}

	@Override
	public void addCoordinator(AbstractCoordinator coordinator) {

		super.addCoordinator(coordinator);
	}

	public void updateImage() {

		this.clearImage();
		this.repaint();
	}

	@Override
	public void paintComponent(java.awt.Graphics g) {

		super.paintComponent(g);

		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

		// Draw elements
		drawElements(g2);

		//Draw the rectangle to select the instances
		if (selectionSource != null && selectionTarget != null) {

			drawSelectionRectangle(g2);
		} 
		else { 
			//Draw the instance label                
			if (showLabbel && labelsItems != null && labelPositions != null 
					&& !labelsItems.isSelected()) {

				drawInstancesLabels(g2);
			}
		}
		//drawn the selection polygon
		if (selectionPolygon != null) {

			drawSelectionPolygon(g2);
		}
	}

	private void drawElements(java.awt.Graphics2D g) {

		if (model != null && image == null) {

			// Get the current size of the panel
			Dimension size = getSize(); 
			
			// Create image buffer
			image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);

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

	private void drawSelectionRectangle(java.awt.Graphics2D g) {

		int x = selectionSource.x;
		int width = selectionTarget.x - selectionSource.x;

		int y = selectionSource.y;
		int height = selectionTarget.y - selectionSource.y;

		if (selectionSource.x > selectionTarget.x) {
			x = selectionTarget.x;
			width = selectionSource.x - selectionTarget.x;
		}

		if (selectionSource.y > selectionTarget.y) {
			y = selectionTarget.y;
			height = selectionSource.y - selectionTarget.y;
		}
		g.setColor(selectionColor);
		g.drawRect(x, y, width, height);

		g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.45f));
		g.fillRect(x, y, width, height);
	}

	private void drawInstancesLabels(java.awt.Graphics2D g) {

		labelsItems.drawLabel(g, labelPositions.x, labelPositions.y);
	}

	private void drawSelectionPolygon(java.awt.Graphics2D g) {

		g.setColor(selectionColor);
		g.drawPolygon(selectionPolygon);

		g.setComposite(java.awt.AlphaComposite.
				getInstance(java.awt.AlphaComposite.SRC_OVER, 0.45f));
		g.fillPolygon(selectionPolygon);
	}

	public void saveToPngImageFile(String filename) throws IOException {
		try {
			Dimension size = getSize(); //get the current size of the panel
			BufferedImage buffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
			paint(buffer.getGraphics());
			ImageIO.write(buffer, "png", new File(filename));
		} catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void clearImage() {
		image = null;
	}

	public void colorAs(Scalar scalar) {

		if (model != null) {
			((ProjectionModel) model).setSelectedScalar(scalar);
			model.notifyObservers();
		}
	}

	public void cleanSelectedInstances() {

		if (model != null) {
			model.cleanSelectedInstances();
			model.notifyObservers();
		}
	}

	public void removeSelectedInstances() {

		if (model != null) {
			model.removeSelectedInstances();
			model.notifyObservers();
		}
	}

	public void zoomIn() {
		if (model != null) {
			//set the new viewport
			viewport.width = (int) (viewport.width * 1.1);
			viewport.height = (int) (viewport.height * 1.1);
			((ProjectionModel) model).setViewport(viewport);

			//Change the size of the panel
			Dimension size = new Dimension(viewport.getSize());
			size.width = size.width + 2 * viewport.x;
			size.height = size.height + 2 * viewport.y;
			setPreferredSize(size);
			setSize(size);

			model.notifyObservers();
		}
	}

	public void zoomOut() {
		if (model != null) {
			//set the new viewport
			viewport.width = (int) (viewport.width * 0.909);
			viewport.height = (int) (viewport.height * 0.909);
			((ProjectionModel) model).setViewport(viewport);

			//Change the size of the panel
			Dimension size = new Dimension(viewport.getSize());
			size.width = size.width + 2 * viewport.x;
			size.height = size.height + 2 * viewport.y;
			setPreferredSize(size);
			setSize(size);

			model.notifyObservers();
		}
	}

	public ArrayList<ProjectionInstance> getSelectedInstances(Polygon polygon) {
		ArrayList<ProjectionInstance> selected = new ArrayList<ProjectionInstance>();

		if (model != null) {
			selected = ((ProjectionModel) model).getInstancesByPosition(polygon);
		}

		return selected;
	}

	public ArrayList<ProjectionInstance> getSelectedInstances(Point source, Point target) {
		ArrayList<ProjectionInstance> selinstances = new ArrayList<ProjectionInstance>();

		if (model != null) {
			int x = Math.min(source.x, target.x);
			int width = Math.abs(source.x - target.x);

			int y = Math.min(source.y, target.y);
			int height = Math.abs(source.y - target.y);

			Rectangle rect = new Rectangle(x, y, width, height);
			selinstances = ((ProjectionModel) model).getInstancesByPosition(rect);
		}

		return selinstances;
	}

	@Override
	public final void setBackground(Color bg) {

		super.setBackground(bg);

		if (this.colorscale != null) {
			this.colorscale.setBackground(bg);
		}
	}

	public void removeInstancesWithScalar(float val) {
		if (model != null) {
			ArrayList<AbstractInstance> insts = new ArrayList<AbstractInstance>();
			Scalar scalar = ((ProjectionModel) model).addScalar("cdata");
			for (AbstractInstance ai : model.getInstances()) {
				if (((ProjectionInstance) ai).getScalarValue(scalar) == val) {
					insts.add(ai);
				}
			}

			model.removeInstances(insts);
			model.notifyObservers();
		}
	}

	class MouseMotionListener extends MouseMotionAdapter {

		@Override
		public void mouseMoved(java.awt.event.MouseEvent evt) {
			super.mouseMoved(evt);

			if (model != null) {
				labelsItems = ((ProjectionModel) model).getInstanceByPosition(evt.getPoint());

				if (labelsItems != null) {
					labelPositions = evt.getPoint();
				} else {
					labelPositions = null;
				}

				repaint();
			}
		}

		@Override
		public void mouseDragged(java.awt.event.MouseEvent evt) {
			if (selectedItems != null) {
				if (model.hasSelectedInstances()) {
					TransformationMatrix2D inv = ((ProjectionModel) model).getViewportMatrix().inverse();
					float[] coord = inv.apply(evt.getX(), evt.getY());
					coord[0] -= selectedItems.getX();
					coord[1] -= selectedItems.getY();

					for (AbstractInstance ai : model.getSelectedInstances()) {
						ProjectionInstance pi = (ProjectionInstance) ai;
						pi.setX(coord[0] + pi.getX());
						pi.setY(coord[1] + pi.getY());
					}

					model.setChanged();
					updateImage();
				}
			} else if (selectionSource != null) {
				selectionTarget = evt.getPoint();
			} else if (selectionPolygon != null) {
				selectionPolygon.addPoint(evt.getX(), evt.getY());
			}

			repaint();
		}

	}

	class MouseClickedListener extends MouseAdapter {

		@Override
		public void mouseClicked(java.awt.event.MouseEvent evt) {
			super.mouseClicked(evt);

			if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
				if (model != null) {
					ProjectionInstance instance = ((ProjectionModel) model).getInstanceByPosition(evt.getPoint());
					if (instance != null) {
						model.setSelectedInstance(instance);
						model.notifyObservers();
					}
				}
			} else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
				cleanSelectedInstances();
			}
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent evt) {
			super.mousePressed(evt);

			if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
				if (model != null) {
					ProjectionInstance instance = ((ProjectionModel) model).getInstanceByPosition(evt.getPoint());

					if (instance != null) {
						if (moveInstances) {
							if (model.getSelectedInstances().contains(instance)) {
								selectedItems = instance;
							}
						}
					} else {
						selectionSource = evt.getPoint();
					}
				}
			} else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
				selectionPolygon = new java.awt.Polygon();
				selectionPolygon.addPoint(evt.getX(), evt.getY());
			}
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent evt) {
			super.mouseReleased(evt);

			if (model != null) {
				if ((selectionSource != null && selectionTarget != null) || selectionPolygon != null) {
					ArrayList<ProjectionInstance> instances = null;

					if (selectionPolygon != null) {
						instances = getSelectedInstances(selectionPolygon);
					} else {
						instances = getSelectedInstances(selectionSource, selectionTarget);
					}

					if (instances != null) {

						if (selection != null) {
							selection.selected(new ArrayList<AbstractInstance>(instances));
						}
					}
				}
			}

			selectionPolygon = null;
			selectedItems = null;
			selectionSource = null;
			selectionTarget = null;
		}
	}
}
