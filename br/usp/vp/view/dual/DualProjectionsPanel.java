package br.usp.vp.view.dual;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import matrix.AbstractMatrix;
import projection.model.ProjectionModel;
import projection.model.ProjectionModel.InstanceType;
import visualizationbasics.color.ColorScaleFactory.ColorScaleType;
import br.usp.vp.view.ProjectionPanel;

public class DualProjectionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ProjectionPanel itemsPanel;
	private ProjectionPanel dimsPanel;

	public DualProjectionsPanel(AbstractMatrix itemsProj, AbstractMatrix dimsProj, String title) {

		this.setLayout(new GridLayout(1,2));
		
		Dimension panelsSize = new Dimension(500,500);
		
		itemsPanel = createProjectionPanel(itemsProj, panelsSize);
		dimsPanel = createProjectionPanel(dimsProj, panelsSize);
		
		add(itemsPanel);
		add(dimsPanel);
	}
	
	private ProjectionPanel createProjectionPanel(AbstractMatrix projection,
			Dimension size) {
		
		ProjectionModel projModel = new ProjectionModel();
		projModel.addProjection(projection, InstanceType.CIRCLED_INSTANCE);
		projModel.changeColorScaleType(ColorScaleType.ORANGE_TO_BLUESKY);
		
		ProjectionPanel projPanel = new ProjectionPanel(projModel, size);
		projPanel.setBorder(BorderFactory.createEtchedBorder());
		
		return projPanel;
		
	}
}
