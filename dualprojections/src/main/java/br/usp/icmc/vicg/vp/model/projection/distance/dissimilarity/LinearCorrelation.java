package br.usp.icmc.vicg.vp.model.projection.distance.dissimilarity;

import matrix.AbstractVector;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import br.usp.icmc.vicg.vp.model.util.Misc;
import distance.dissimilarity.AbstractDissimilarity;

public class LinearCorrelation implements AbstractDissimilarity {
	
	private static PearsonsCorrelation pc = new PearsonsCorrelation();
    
	@Override
	public float calculate(AbstractVector v1, AbstractVector v2) {

		double[] d1 = Misc.convertFloatsToDoubles(v1.getValues());
		double[] d2 = Misc.convertFloatsToDoubles(v2.getValues());
		
		float correlation = (float) Math.abs(pc.correlation(d1, d2));
				
		return 1 - correlation;
	}
}
