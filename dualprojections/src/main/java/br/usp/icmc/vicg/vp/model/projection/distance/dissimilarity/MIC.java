package br.usp.icmc.vicg.vp.model.projection.distance.dissimilarity;

import matrix.AbstractVector;
import mine.core.MineParameters;
import analysis.Analysis;
import analysis.results.MinimalResult;
import data.VarPairData;
import distance.dissimilarity.AbstractDissimilarity;

public class MIC implements AbstractDissimilarity {
    
	@Override
	public float calculate(AbstractVector v1, AbstractVector v2) {
		
		VarPairData vp = new VarPairData(v1.getValues(), v2.getValues());
		MineParameters mp = new MineParameters();
		
		MinimalResult res = Analysis.getResult(MinimalResult.class, vp, mp);
		
		float mic = res.getMIC();
		
		return 1 - mic;
	}
}
