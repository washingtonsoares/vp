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
	private static AbstractMatrix dataMatrix;
	
	// Models
	private static DualProjections dualProjections;
	private static InteractionsTree tree;
	
	// Views
	private static ToolBar toolBar;
	private static DualProjectionsPanel dualPanel;
	private static InteractionsTreePanel treePanel;
	
	public static void loadDataCSV(String filename, Integer labelIndex, 
			Integer[] ignoreIndices) throws IOException {
		
		dataMatrix = new DataMatrix(filename, labelIndex, ignoreIndices);
		((DataMatrix) dataMatrix).load();
	}
	
	public static void loadData(String filename) throws Exception {
		
		dataMatrix = MatrixFactory.getInstance(filename);
	}
	
	public static void init(JPanel toolbarParent, JPanel dualPanelParent,
			JPanel treePanelParent)  {
		
		// Create Models
		dualProjections = DualProjectionsFactory.getInstance(dataMatrix);
		tree = new InteractionsTree();
		
		// Create views
		toolBar = new ToolBar();
		
		dualPanel = new DualProjectionsPanel();
		dualPanel.setBackground(Color.WHITE);
		
		treePanel = new InteractionsTreePanel(tree);
		treePanel.setBackground(Color.WHITE);
		
		
		// Attach data
		dualPanel.setProjections(dualProjections);
		addVertexToTree(dualProjections);
		
		toolbarParent.add(toolBar);
		dualPanelParent.add(dualPanel);
		treePanelParent.add(treePanel);
	}
	
	public static void changeContextTo(Integer value) {
		
		DPVertex newCurrent = (DPVertex) tree.getVertexAt(value - 1);
		Controller.dualProjections = newCurrent.getDualProjections();
		dualPanel.setProjections(newCurrent.getDualProjections());
		tree.setCurrentVertex(newCurrent, true);
	}
	
	public static void reprojectItems() {
		
		// Get current item model
		DPVertex vertex = (DPVertex) tree.getCurrentVertex();
		ProjectionModel model = vertex.getDualProjections().getItemsModel();
		
		// Create new data matrix
		AbstractMatrix selectedData = getSelectedData(
				model.getSelectedInstances());
		
		// Create and add new projections
		addNewProjections(DualProjectionsFactory.getInstance(selectedData));
	}
	
	private static void addVertexToTree(DualProjections dualProjection) {
		
		DPVertex newVertex = new DPVertex(
				tree.getGraph().getNumVertices() + 1, dualProjections, dualPanel);
		tree.addNewVertex(newVertex);
		treePanel.getComponent().layoutGraph();
		treePanel.revalidate();
	}
	
	private static void addNewProjections(DualProjections newProjections) {
		
		Controller.dualProjections = newProjections;
		dualPanel.setProjections(newProjections);
		addVertexToTree(dualProjections);
	}
	
	private static AbstractMatrix getSelectedData(ArrayList<AbstractInstance> selected) {
		
		DenseMatrix selectedData = new DenseMatrix();
		
		for (AbstractInstance inst : selected) {
			
			Integer id = inst.getId();
			AbstractVector row = dataMatrix.getRow(id);
			selectedData.addRow(row);
		}
		return selectedData;
	}
}










