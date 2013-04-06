package br.usp.vp.model.projection;

import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;

public class DimensionsProjectionComp implements AbstractComponent<
	DimensionsProjectionComp.Input,	DimensionsProjectionComp.Output> {
    
	private static final long serialVersionUID = 1L;

	@Override
    public Output execute(Input input) throws VisPipelineException {
        
        return null;
    }
    
    public static class Input implements InputInterface {
    	
    	public AbstractMatrix matrix;
    	public DistanceMatrix dmat;
    	public AbstractDissimilarity diss;
    	
    	public Input(
    			@Param(name = "points matrix") AbstractMatrix matrix,
    			@Param(name= "dissimilarity type") AbstractDissimilarity diss) {
    		
            if (matrix == null) {
            	
                throw new IllegalArgumentException(
                		"A points matrix must be provided.");
            }
            if (diss == null) {
            	
            	throw new IllegalArgumentException(
            			"A dissimilarity type must be provided.");
            }
            
            this.matrix = matrix;
            this.diss = diss;
        }
    	
    	public Input(
    			@Param(name = "distance matrix") DistanceMatrix dmat) {

    		if (dmat == null) {

    			throw new IllegalArgumentException(
    					"A distance matrix must be provided.");
    		}
    		this.dmat = dmat;
    	}
    }
    
    public static class Output implements OutputInterface {
    	
    	public AbstractMatrix projetion;
    	
    	public Output(AbstractMatrix projection) {
    		
    		this.projetion = projection;
    	}
    }
}
