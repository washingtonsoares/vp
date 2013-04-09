/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.projclus;

import projection.technique.idmap.IDMAPProjection.InitializationType;
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
public class ProjClusProjectionComp implements AbstractComponent<ProjClusProjectionComp.Input, ProjClusProjectionComp.Output> {

    private static final long serialVersionUID = 1L;
    private InitializationType initializationType;
    private DissimilarityType dissimilarityType;
    private int numIterations;
    private float fractionDelta;
    private float clusterFactor;

    public ProjClusProjectionComp() {
        this.initializationType = InitializationType.FASTMAP;
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
        this.numIterations = 50;
        this.fractionDelta = 8.0f;
        this.clusterFactor = 4.5f;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        //projecting
        ProjClusProjection projclus = new ProjClusProjection();
        AbstractMatrix projection;
        projclus.setFractionDelta(fractionDelta);
        projclus.setInitialization(initializationType);
        projclus.setNumberIterations(numIterations);
        projclus.setClusterFactor(clusterFactor);

        if (in.matrix != null) { //using a matrix
            AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
            projection = projclus.project(in.matrix, diss);
        } else { // if (dmat != null) { //using a distance matrix
            projection = projclus.project(in.distanceMatrix);
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
     * @return the initialization type
     */
    public InitializationType getInitialization() {
        return initializationType;
    }

    /**
     * @param initializationType the initialization type to set
     */
    public void setInitialization(InitializationType initializationType) {
        this.initializationType = initializationType;
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
     * @return the fraction delta
     */
    public float getFractionDelta() {
        return fractionDelta;
    }

    /**
     * @param fractionDelta the fraction delta to set
     */
    public void setFractionDelta(float fractionDelta) {
        this.fractionDelta = fractionDelta;
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

    /**
     * @return the cluster factor
     */
    public float getClusterFactor() {
        return clusterFactor;
    }

    /**
     * @param clusterFactor the cluster factor to set
     */
    public void setClusterFactor(float clusterFactor) {
        this.clusterFactor = clusterFactor;
    }

    public boolean isDistanceMatrixProvided() {
        // return (dmat != null);
        // FIXME - is this method really needed?
        return false;
    }
}
