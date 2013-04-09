package br.usp.icmc.vicg.vp.model.projection.distance;

import java.util.Random;

import matrix.AbstractMatrix;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import distance.dissimilarity.AbstractDissimilarity;

public class DimensionsDistanceMatrixFactory {

    public static DimensionsDistanceMatrix getInstance(AbstractMatrix data, 
            AbstractDissimilarity diss, boolean useClass) {
    	
    	float[][] points = data.toMatrix();
    	
    	int newRowLenght = points.length; 
    	
        AbstractMatrix tMatrix = new DenseMatrix();
        
        // Fill vector
        for (int i = 0; i < data.getDimensions(); i++) {
        	
        	float[] aux = new float[newRowLenght];
        	
        	for (int j = 0; j < points.length; j++) {
        		
        		aux[j] = points[j][i];
        	}
        	
        	DenseVector newVector = new DenseVector(aux);
        	newVector.setId(tMatrix.getRowCount());
            tMatrix.addRow(newVector, data.getAttributes().get(i));
        }
        
        // Use class as a new row
        if (useClass) {
        	
        	float[] classCol = new float[newRowLenght];
        	
        	float minX = -0.01f;
        	float maxX = 0.01f;
        	Random rand = new Random();
        	
        	int i;
        	for (i = 0; i < newRowLenght; i++) {
        		
        		float finalX = rand.nextFloat() * (maxX - minX) + minX;
        		classCol[i] = data.getRow(i).getKlass() + finalX;        	
        	}
        	
        	DenseVector newVector = new DenseVector(classCol);
        	newVector.setId(tMatrix.getRowCount());
            tMatrix.addRow(newVector, "Class");
        	
            for (i = 0; i < tMatrix.getRowCount() - 1; i++) {
            	
            	tMatrix.getRow(i).setKlass(0);
            }
            tMatrix.getRow(i).setKlass(1);
        }
        
        return new DimensionsDistanceMatrix(tMatrix, diss);
    }
}
