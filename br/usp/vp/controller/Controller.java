package br.usp.vp.controller;

import java.io.IOException;

import matrix.AbstractMatrix;
import matrix.MatrixFactory;
import br.usp.vp.matrix.DataMatrix;
import br.usp.vp.model.projection.dual.DualProjections;
import br.usp.vp.model.projection.dual.DualProjectionsFactory;
import br.usp.vp.model.tree.DualProjectionsVertex;
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
	
	public Controller(ToolBar toolBar, DualProjectionsPanel dualPanel,
			InteractionsTreePanel treePanel) {
		
		Controller.toolBar = toolBar;
		Controller.dualPanel = dualPanel;
		Controller.treePanel = treePanel;
	}
	
	public static void loadDataCSV(String filename, Integer labelIndex, 
			Integer[] ignoreIndices) throws IOException {
		
		dataMatrix = new DataMatrix(filename, labelIndex, ignoreIndices);
		((DataMatrix) dataMatrix).load();
	}
	
	public static void loadData(String filename) throws Exception {
		
		dataMatrix = MatrixFactory.getInstance(filename);
	}
	
	public static void init() throws IOException {

		dualProjections = DualProjectionsFactory.getInstance(dataMatrix);
		dualPanel.setProjections(dualProjections);
		
		tree = new InteractionsTree();
		treePanel.setTree(tree);
		DualProjectionsVertex newVertex = new DualProjectionsVertex(
				tree.getGraph().getNumVertices() + 1, dualProjections);
		tree.addNewVertex(newVertex);
	}
	
	public static void changeContextTo(Integer value) {
		
		DualProjectionsVertex newCurrent = (DualProjectionsVertex) tree.getVertexAt(value - 1);
		
		dualPanel.setProjections(newCurrent.getDualProjections());
		tree.setCurrentVertex(newCurrent, true);
	}
}
