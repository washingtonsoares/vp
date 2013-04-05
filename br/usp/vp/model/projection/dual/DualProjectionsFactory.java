package br.usp.vp.model.projection.dual;

import java.io.IOException;

import matrix.AbstractMatrix;
import projection.technique.Projection;
import projection.technique.idmap.IDMAPProjection;
import br.usp.vp.distance.DimensionsDistanceMatrix;
import br.usp.vp.distance.DimensionsDistanceMatrixFactory;
import br.usp.vp.projection.Stress;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class DualProjectionsFactory {

	public static DualProjections getInstance(AbstractMatrix dataMatrix) 
			throws IOException {

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
		
		return new DualProjections(itemsProj, dimsProj);
	}
}
