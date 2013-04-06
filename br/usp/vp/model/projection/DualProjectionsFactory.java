package br.usp.vp.model.projection;

import java.io.IOException;

import matrix.AbstractMatrix;
import projection.technique.Projection;
import projection.technique.mds.ClassicalMDSProjection;
import br.usp.vp.model.projection.distance.DimensionsDistanceMatrix;
import br.usp.vp.model.projection.distance.DimensionsDistanceMatrixFactory;
import br.usp.vp.model.projection.distance.dissimilarity.MIC;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class DualProjectionsFactory {

	public static DualProjections getInstance(AbstractMatrix dataMatrix) {

		// Create Distance Matrices
		DistanceMatrix elemDmat = new DistanceMatrix(dataMatrix, new Euclidean());
		DimensionsDistanceMatrix dimDmat = DimensionsDistanceMatrixFactory
				.getInstance(dataMatrix, new MIC());

		// Project data
		Projection projTech = new ClassicalMDSProjection();
		AbstractMatrix itemsProj = projTech.project(elemDmat);
		AbstractMatrix dimsProj = projTech.project(dimDmat);

		try {
			// Calculate Stress by Row
			float[] elemStress = Stress.getStressByRow(elemDmat, itemsProj);
			float[] dimStress = Stress.getStressByRow(dimDmat, dimsProj);
			itemsProj.setKlass(elemStress);
			dimsProj.setKlass(dimStress);
			
		}catch (IOException e) {
			
			System.err.println("ERROR: " + e.getMessage());
		}
		return new DualProjections(itemsProj, dimsProj);
	}
}
