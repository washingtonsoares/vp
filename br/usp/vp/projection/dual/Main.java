package br.usp.vp.projection.dual;

import java.awt.Dimension;

import javax.swing.JFrame;

import matrix.AbstractMatrix;
import matrix.MatrixFactory;
import projection.model.ProjectionModel;
import projection.model.ProjectionModel.InstanceType;
import projection.technique.Projection;
import projection.technique.mds.ClassicalMDSProjection;
import visualizationbasics.color.ColorScaleFactory.ColorScaleType;
import br.usp.vp.distance.DimensionsDistanceMatrix;
import br.usp.vp.distance.DimensionsDistanceMatrixFactory;
import br.usp.vp.projection.Stress;
import br.usp.vp.view.ProjectionPanel;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class Main {

	public static void testDataset(String filename) throws Exception {

		// Load Matrix
		AbstractMatrix matrix = MatrixFactory.getInstance(filename);

		// Distance Matrices of ...
		// ... elements
		DistanceMatrix elemDmat = new DistanceMatrix(matrix, new Euclidean());
		// ... and dimensions
		DimensionsDistanceMatrix dimDmat = DimensionsDistanceMatrixFactory
				.getInstance(matrix, new Euclidean());

		// Projections of ...
		Projection projTech = new ClassicalMDSProjection();
		// ... elements
		AbstractMatrix itemsProj = projTech.project(elemDmat);
		// ... dimensions
		AbstractMatrix dimsProj = projTech.project(dimDmat);

		// Calculate Stress by Row of ...
		// ... elements
		float[] elemStress = Stress.getStressByRow(elemDmat, itemsProj);
		itemsProj.setKlass(elemStress);
		// ... dimensions
		float[] dimStress = Stress.getStressByRow(dimDmat, dimsProj);
		dimsProj.setKlass(dimStress);

		ProjectionModel projModel = new ProjectionModel();
		projModel.addProjection(itemsProj, InstanceType.CIRCLED_INSTANCE);
		projModel.changeColorScaleType(ColorScaleType.ORANGE_TO_BLUESKY);

		// Plot data ...
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.getContentPane().add(new DualProjectionsPanel(itemsProj, dimsProj, ""));
		frame.getContentPane().add(new ProjectionPanel(projModel, new Dimension(800, 800)));
		frame.pack();
		frame.setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		String iris = "D:\\Dropbox\\work\\datasets\\PEX\\Iris.data";
		String idh = "D:\\Dropbox\\work\\datasets\\PEX\\idh-2006.data";
		String wine = "D:\\Dropbox\\work\\datasets\\PEX\\wine.data";
		String tumor = "D:\\Dropbox\\work\\datasets\\PEX\\primary-tumor.data";
		String diabetes = "D:\\Dropbox\\work\\datasets\\PEX\\diabetes.data";
		String messages4 = "D:\\Dropbox\\work\\datasets\\PEX\\messages4.data";

		testDataset(iris);
	}
}
