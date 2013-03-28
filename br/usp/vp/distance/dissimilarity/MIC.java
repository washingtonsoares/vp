package br.usp.vp.distance.dissimilarity;

import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.reader.MatrixReaderComp;
import mine.core.MineParameters;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import vispipelinebasics.exception.VisPipelineException;
import analysis.Analysis;
import analysis.AnalysisParameters;
import analysis.VarPairQueue;
import analysis.results.MinimalResult;
import data.Dataset;
import data.VarPairData;
import distance.dissimilarity.AbstractDissimilarity;

public class MIC implements AbstractDissimilarity {
    
    public float calculate(double[][] data) {
        
        PearsonsCorrelation pearson = new PearsonsCorrelation(data);
        
        RealMatrix corMat = pearson.getCorrelationMatrix();
        
        Dataset d_ = new Dataset(data, 0);
        
        VarPairQueue pairs_ = new VarPairQueue(d_);
        
        Analysis an = new Analysis(d_, pairs_);
        
        AnalysisParameters ps = new AnalysisParameters();
        
        an.analyzePairs(MinimalResult.class, ps);
        
        an.numResults();
        
        return 0;
    }

	@Override
	public float calculate(AbstractVector v1, AbstractVector v2) {
		
		// TODO Auto-generated method stub
		return 0;
	}
	
public static void main(String[] args) throws VisPipelineException {
        
        // Set filename
        String filename = "D:\\Dropbox\\work\\datasets\\PEX\\Iris.data";
        
        // Read data
        AbstractMatrix matrix = new MatrixReaderComp().
                execute(new MatrixReaderComp.Input(filename)).matrix;
        
//        double[][] data = convertFloatsToDoubles(matrix.toMatrix());
        float[][] data = matrix.toMatrix();
        
//        PearsonsCorrelation pearson = new PearsonsCorrelation(data);
        
//        RealMatrix corMat = pearson.getCorrelationMatrix();
        
        Dataset d_ = new Dataset(data, 0);
        
        VarPairQueue pairs_ = new VarPairQueue(d_);
        
        Analysis an = new Analysis(d_, pairs_);
        
        AnalysisParameters ps = new AnalysisParameters();
        
        an.analyzePairs(MinimalResult.class, ps);
        
        VarPairData vp = new VarPairData(data[1], data[2]);
        
        for (int i = 0; i < data[1].length; i++) {
            
            System.out.print(data[1][i] + " ");
        }
        System.out.println();

        for (int i = 0; i < data[1].length; i++) {

            System.out.print(data[2][i] + " ");
        }
        System.out.println();

        MineParameters mp = new MineParameters();
        
        MinimalResult res = Analysis.getResult(MinimalResult.class, vp, mp);
        
        float fr = res.getMIC();
        
        System.out.println(fr);
        
        // Create Distance Matrix of Dimensions
        
        // Project Dimensions
        
    }
}
