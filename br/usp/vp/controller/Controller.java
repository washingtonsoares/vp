package br.usp.vp.controller;

import java.io.IOException;

import matrix.AbstractMatrix;
import matrix.MatrixFactory;
import projection.technique.Projection;
import projection.technique.idmap.IDMAPProjection;
import br.usp.vp.distance.DimensionsDistanceMatrix;
import br.usp.vp.distance.DimensionsDistanceMatrixFactory;
import br.usp.vp.matrix.DataMatrix;
import br.usp.vp.model.projection.dual.DualProjections;
import br.usp.vp.model.tree.InteractionsTree;
import br.usp.vp.projection.Stress;
import br.usp.vp.view.misc.ToolBar;
import br.usp.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.vp.view.tree.InteractionsTreePanel;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

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

		// Create Distance Matrices
		DistanceMatrix elemDmat = new DistanceMatrix(dataMatrix, new Euclidean());
		DimensionsDistanceMatrix dimDmat = DimensionsDistanceMatrixFactory
				.getInstance(dataMatrix, new Euclidean());

		// Project data
		Projection projTech = new IDMAPProjection();
		AbstractMatrix itemsProj = projTech.project(elemDmat);
		AbstractMatrix dimsProj = projTech.project(dimDmat);

		// Calculate Stress by Row
		float[] elemStress = Stress.getStressByRow(elemDmat, itemsProj);
		float[] dimStress = Stress.getStressByRow(dimDmat, dimsProj);
		
		// Set scalar as stress
		itemsProj.setKlass(elemStress);
		dimsProj.setKlass(dimStress);
		
		dualProjections = new DualProjections(itemsProj, dimsProj);
		dualPanel.setProjections(dualProjections);
		
		tree = new InteractionsTree();
		treePanel.setTree(tree);
		tree.addNewVertex(null);
	}
	
	public static void setTreeCurrentVertex(Integer value) {
		
		tree.setCurrentVertex(tree.getVertexAt(value - 1), true);
	}
}
