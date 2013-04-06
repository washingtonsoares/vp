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
import br.usp.vp.matrix.DataMatrix;
import br.usp.vp.model.projection.dual.DualProjections;
import br.usp.vp.model.projection.dual.DualProjectionsFactory;
import br.usp.vp.model.tree.DPVertex;
import br.usp.vp.model.tree.InteractionsTree;
import br.usp.vp.view.misc.ToolBar;
import br.usp.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.vp.view.tree.InteractionsTreePanel;

public class Controller {

	// Data
	private AbstractMatrix dataMatrix;
	
	// Models
	private DualProjections dualProjections;
	private InteractionsTree tree;
	
	// Views
	private ToolBar toolBar;
	private DualProjectionsPanel dualPanel;
	private InteractionsTreePanel treePanel;
	
	public void initView(JPanel toolbarSlot, JPanel dualPanelSlot,
			JPanel treePanelSlot) {
		
		// Tool bar
		toolBar = new ToolBar();
		toolbarSlot.add(toolBar);
		toolbarSlot.validate();
		
		// Dual panel
		dualPanel = new DualProjectionsPanel();
		dualPanel.setBackground(Color.WHITE);
		dualPanelSlot.add(dualPanel);
		dualPanelSlot.validate();
		
		treePanel = new InteractionsTreePanel();
		treePanel.setBackground(Color.WHITE);
		treePanelSlot.add(treePanel);
		treePanelSlot.validate();
	}
	
	public void initModels(AbstractMatrix dataMatrix) {
		
		this.dataMatrix = dataMatrix;
		dualProjections = DualProjectionsFactory.getInstance(dataMatrix);
		tree = new InteractionsTree();
	}
	
	public void attachModelsToView() {
		
		dualPanel.attach(dualProjections);
		treePanel.attach(tree);
		
		addVertexToTree(dualProjections);
	}
	
	private void addVertexToTree(DualProjections dualProjection) {
		
		DPVertex newVertex = new DPVertex(
				tree.getGraph().getNumVertices() + 1, dualProjections, dualPanel);
		tree.addNewVertex(newVertex);
		treePanel.getComponent().layoutGraph();
		treePanel.revalidate();
	}
	
	public void changeContextTo(Integer value) {
		
		DPVertex newCurrent = (DPVertex) tree.getVertexAt(value - 1);
		this.dualProjections = newCurrent.getDualProjections();
		dualPanel.attach(newCurrent.getDualProjections());
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
			addNewProjections(DualProjectionsFactory.getInstance(selectedData));
		}
	}
	
	private void addNewProjections(DualProjections newProjections) {
		
		this.dualProjections = newProjections;
		dualPanel.attach(newProjections);
		addVertexToTree(dualProjections);
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
		return selectedData;
	}
	
	public AbstractMatrix loadDataCSV(String filename, Integer labelIndex, 
			Integer[] ignoreIndices) throws IOException {
		
		DataMatrix data = new DataMatrix(filename, labelIndex, ignoreIndices);
		data.load();
		return data;
	}
	
	public AbstractMatrix loadData(String filename) throws Exception {
		
		return MatrixFactory.getInstance(filename);
	}
}










