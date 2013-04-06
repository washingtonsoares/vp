package br.usp.vp.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.MatrixFactory;
import matrix.dense.DenseMatrix;
import projection.model.ProjectionModel;
import visualizationbasics.model.AbstractInstance;
import br.usp.vp.model.data.DataMatrix;
import br.usp.vp.model.projection.DualProjections;
import br.usp.vp.model.projection.DualProjectionsFactory;
import br.usp.vp.model.tree.DPVertex;
import br.usp.vp.model.tree.InteractionsTree;
import br.usp.vp.view.misc.ToolBar;
import br.usp.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.vp.view.tree.InteractionsTreePanel;

public class Controller {

	// Data
	private AbstractMatrix dataMatrix;
	
	// Models
	private InteractionsTree tree;
	
	// Views
	private ToolBar toolBar;
	private InteractionsTreePanel treePanel;
	
	// Dual Panel Slot
	private JPanel dualPanelSlot;
	
	public void initView(JPanel toolbarSlot, JPanel dualPanelSlot,
			JPanel treePanelSlot) {
		
		// Tool bar
		toolBar = new ToolBar();
		toolbarSlot.add(toolBar);
		toolbarSlot.validate();
		
		// Dual panel
		this.dualPanelSlot = dualPanelSlot;
		
		// Tree panel
		treePanel = new InteractionsTreePanel();
		treePanel.setBackground(Color.WHITE);
		treePanelSlot.add(treePanel);
		treePanelSlot.validate();
	}
	
	public void initModels(AbstractMatrix dataMatrix) {
		
		this.dataMatrix = dataMatrix;
		
		tree = new InteractionsTree();
		treePanel.attach(tree);
		treePanel.revalidate();
		
		addVertexToTree(DualProjectionsFactory.getInstance(dataMatrix));
	}
	
	public void addVertexToTree(DualProjections dualProjections) {
		
		// Create new vertex
		DualProjectionsPanel dualPanel = createDualPanel(dualProjections);
		DPVertex newVertex = new DPVertex(tree.getGraph().getNumVertices() + 1,
				dualProjections, dualPanel);
		
		setCurrentProjection(newVertex);
		
		tree.addNewVertex(newVertex);
		treePanel.getComponent().layoutGraph();
		treePanel.revalidate();
	}
	
	private DualProjectionsPanel createDualPanel(DualProjections dualProjections) {
		
		DualProjectionsPanel dualPanel = new DualProjectionsPanel();
		dualPanel.setBackground(Color.WHITE);
		dualPanel.attach(dualProjections);

		return dualPanel;
	}
	
	private void setCurrentProjection(DPVertex vertex) {
		
		dualPanelSlot.removeAll();
		dualPanelSlot.add(vertex.getDualPanel());
		dualPanelSlot.revalidate();
		dualPanelSlot.repaint();
	}
	
	public void changeContextToVertex(Integer value) {
		
		DPVertex newCurrent = (DPVertex) tree.getVertexAt(value - 1);
		setCurrentProjection(newCurrent);
		tree.setCurrentVertex(newCurrent, true);
	}
	
	public void reprojectItems() {
		
		// Get current item model
		DPVertex vertex = (DPVertex) tree.getCurrentVertex();
		ProjectionModel model = vertex.getDualProjections().getItemsModel();
		
		// Create new data matrix
		AbstractMatrix selectedData = getSelectedData(
				model.getSelectedInstances());
		
		if (selectedData != null) {
			
			// Create and add new projections
			addVertexToTree((DualProjectionsFactory.getInstance(selectedData)));
		}
	}
	
	private AbstractMatrix getSelectedData(ArrayList<AbstractInstance> selected) {
		
		DenseMatrix selectedData = new DenseMatrix();
		
		if (selected.isEmpty()) {
			
			return null;
		}
		
		for (AbstractInstance inst : selected) {
			
			Integer id = inst.getId();
			AbstractVector row = dataMatrix.getRow(id);
			selectedData.addRow(row);
		}
		selectedData.setAttributes(dataMatrix.getAttributes());
		
		return selectedData;
	}
	
	public AbstractMatrix loadData(String filename, Integer labelIndex, 
			Integer[] ignoreIndices) throws IOException {
		
		DataMatrix data = new DataMatrix(filename, labelIndex, ignoreIndices);
		data.load();
		return data;
	}
	
	public AbstractMatrix loadData(String filename) throws Exception {
		
		return MatrixFactory.getInstance(filename);
	}
}










