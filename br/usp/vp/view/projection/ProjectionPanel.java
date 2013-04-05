package br.usp.vp.view.projection;

import java.awt.Point;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import projection.model.ProjectionInstance;
import projection.model.ProjectionModel;
import projection.model.Scalar;
import visualizationbasics.model.AbstractInstance;

public class ProjectionPanel extends InterativePanel {

	private static final long serialVersionUID = 1L;
	
	// Configuration
	private boolean showLabel = true;

	private Scalar scalar;
	
	// Labels
	private ProjectionInstance labelsItems;
	private Point labelPositions;

	public ProjectionPanel(ProjectionModel model) {
		
		super(model);
		
		this.setScalar(model);
		
		this.addMouseMotionListener(new MouseMotionListener());
	}
	
	public void setScalar(ProjectionModel model) {
		
		scalar = model.getSelectedScalar();

		if (scalar == null) {

			scalar = model.getScalars().get(0);
		}
		
		model.setSelectedScalar(scalar);
	}
	
	@Override
	public void paintComponent(java.awt.Graphics g) {
		
		super.paintComponent(g);

		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

		//Draw the rectangle to select the instances
		if (selectionSource == null || selectionTarget == null) {

			//Draw the instance label                
			if (showLabel && labelsItems != null && labelPositions != null 
					&& !labelsItems.isSelected()) {

				drawInstancesLabels(g2);
			}
		} 
	}

	public Scalar getScalar() {

		return scalar;
	}

	public boolean getShowInstanceLabel() {

		return showLabel;
	}

	public void setShowInstanceLabel(boolean showinstancelabel) {

		this.showLabel = showinstancelabel;

		clearImage();
		repaint();
	}

	private void drawInstancesLabels(java.awt.Graphics2D g) {

		labelsItems.drawLabel(g, labelPositions.x, labelPositions.y);
	}

	public void colorAs(Scalar scalar) {

		if (model != null) {
			((ProjectionModel) model).setSelectedScalar(scalar);
			model.notifyObservers();
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
	}
}
