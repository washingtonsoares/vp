package br.usp.vp.distance;

import matrix.AbstractMatrix;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import distance.dissimilarity.AbstractDissimilarity;

public class DimensionsDistanceMatrixFactory {

    public static DimensionsDistanceMatrix getInstance(AbstractMatrix data, 
            AbstractDissimilarity diss) {
    	
    	float[][] points = data.toMatrix();
    	
        AbstractMatrix tMatrix = new DenseMatrix();
        
        for (int i = 0; i < data.getDimensions(); i++) {
        	
        	float[] aux = new float[points.length];
        	
        	for (int j = 0; j < points.length; j++) {
        		
        		aux[j] = points[j][i];
        	}
            
            tMatrix.addRow(new DenseVector(aux));
        }
        tMatrix.setLabels(data.getAttributes());
        
        return new DimensionsDistanceMatrix(tMatrix, diss);
    }
}
