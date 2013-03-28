package br.usp.vp.projection;

import java.io.IOException;

import matrix.AbstractMatrix;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class Stress {

	public static float[] getStressByRow(DistanceMatrix dmat, 
			AbstractMatrix projection) throws IOException {
		
		DistanceMatrix projDmat = new DistanceMatrix(projection, new Euclidean());
		
		int numElements = dmat.getElementCount();
		
		if (numElements != projDmat.getElementCount()) {
			
			throw new IOException("Distance matrix and projection " +
					"must have the same number of elements.");
		}
		
		float[] rowsStress = new float[numElements];
		
		// Calculate the stress for each element
		for (int i = 0; i < numElements; i++) {
			
			float stress = 0;
			
			for (int j = 0; j < numElements; j++) {
				
				float dn2 = dmat.getDistance(i, j) - projDmat.getDistance(i,j);
				
				stress += Math.abs(dn2);
			}
			
			rowsStress[i] = stress;
		}
		return rowsStress;
	}
}









