/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.lle;

import vispipelinebasics.annotations.VisComponent;
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
@VisComponent(hierarchy = "Projection.Technique",
name = "Local Linear Embedding (LLE)",
description = "Project points from a multidimensional space to the plane "
+ "preserving the neighborhood relations.",
howtocite = "Roweis, S. T.; Saul, L. K. Nonlinear dimensionality "
+ "reduction by locally linear embedding. Science, v. 290, n. 5500, "
+ "p. 2323�2326, 2000.")
public class LLEProjectionComp implements AbstractComponent<LLEProjectionComp.Input, LLEProjectionComp.Output> {

    private static final long serialVersionUID = 1L;
    private int numNeighbors;
    private DissimilarityType dissimilarityType;

    public LLEProjectionComp() {
        this.numNeighbors = 10;
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        //projecting
        LLEProjection lle = new LLEProjection();
        lle.setNumberNeighbors(numNeighbors);
        AbstractMatrix projection;

        if (in.matrix != null) { //using a matrix
            AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
            projection = lle.project(in.matrix, diss);
        } else { // if (dmat != null) { //using a distance matrix
            projection = lle.project(in.distanceMatrix);
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
     * @return the number of neighbors
     */
    public int getNumberNeighbors() {
        return numNeighbors;
    }

    /**
     * @param numNeighbors the number of neighbors to set
     */
    public void setNumberNeighbors(int numNeighbors) {
        this.numNeighbors = numNeighbors;
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
}
