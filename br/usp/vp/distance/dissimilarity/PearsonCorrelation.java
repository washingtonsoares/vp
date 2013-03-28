package br.usp.vp.distance.dissimilarity;

import matrix.AbstractVector;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import analysis.Analysis;
import analysis.AnalysisParameters;
import analysis.VarPairQueue;
import analysis.results.MinimalResult;
import data.Dataset;
import distance.dissimilarity.AbstractDissimilarity;

public class PearsonCorrelation implements AbstractDissimilarity {
    
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
}
