/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.sammon;

import vispipelinebasics.annotations.Param;
import vispipelinebasics.interfaces.AbstractComponent;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import matrix.AbstractMatrix;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class SammonMappingProjectionComp implements AbstractComponent<SammonMappingProjectionComp.Input, SammonMappingProjectionComp.Output> {

    private static final long serialVersionUID = 1L;
    private DissimilarityType dissimilarityType;
    private int numIterations;
    private float magicFactor;

    public SammonMappingProjectionComp() {
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
        this.numIterations = 50;
        this.magicFactor = 0.3f;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        //projecting
        SammonMappingProjection sammon = new SammonMappingProjection();
        AbstractMatrix projection;

        sammon.setMagicFactor(magicFactor);
        sammon.setNumberIterations(numIterations);

        if (in.matrix != null) { //using a matrix
            AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
            projection = sammon.project(in.matrix, diss);
        } else { // if (dmat != null) { //using a distance matrix
            projection = sammon.project(in.distanceMatrix);
        }

        return new Output(projection);
    }

    public static class Input implements InputInterface {

        public AbstractMatrix matrix;
        public DistanceMatrix distanceMatrix;

        public Input(@Param(name = "points matrix") AbstractMatrix matrix) {
            if (matrix == null) {
                throw new IllegalArgumentException("A points matrix must be provided.");
            }
            this.matrix = matrix;
            this.distanceMatrix = null;
        }

        public Input(@Param(name = "distance matrix") DistanceMatrix distanceMatrix) {
            if (distanceMatrix == null) {
                throw new IllegalArgumentException("A distance matrix must be provided.");
            }
            this.matrix = null;
            this.distanceMatrix = distanceMatrix;
        }
    }

    public static class Output implements OutputInterface {

        public AbstractMatrix projection;

        public Output(AbstractMatrix projection) {
            this.projection = projection;
        }
    }

    /**
     * @return the number of iterations
     */
    public int getNumberIterations() {
        return numIterations;
    }

    /**
     * @param numIterations the number of iterations to set
     */
    public void setNumberIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    /**
     * @return the magic factor
     */
    public float getMagicFactor() {
        return magicFactor;
    }

    /**
     * @param magicFactor the magic factor to set
     */
    public void setMagicFactor(float magicFactor) {
        this.magicFactor = magicFactor;
    }

    /**
     * @return the dissimilarity type
     */
    public DissimilarityType getDissimilarityType() {
        return dissimilarityType;
    }

    /**
     * @param dissimilarityType the dissimilarity type to set
     */
    public void setDissimilarityType(DissimilarityType dissimilarityType) {
        this.dissimilarityType = dissimilarityType;
    }

    public boolean isDistanceMatrixProvided() {
        // return (dmat != null);
        // FIXME - is this method really needed?
        return false;
    }

    public int getNumberInstances() {
        // FIXME - XXX
        /* if (matrix != null) {
        return matrix.getRowCount();
        } else if (dmat != null) {
        return dmat.getElementCount();
        } */

        return 0;
    }
}
