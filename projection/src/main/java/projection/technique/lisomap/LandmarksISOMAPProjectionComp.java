/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.lisomap;

import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

import java.io.IOException;

/**
 *
 * @author Fernando
 */
public class LandmarksISOMAPProjectionComp implements AbstractComponent<LandmarksISOMAPProjectionComp.Input, LandmarksISOMAPProjectionComp.Output> {

    private static final long serialVersionUID = 1L;
    private int numNeighbors;
    private DissimilarityType dissimilarityType;

    public LandmarksISOMAPProjectionComp() {
        this.numNeighbors = 10;
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        //projecting
        LandmarksISOMAPProjection lisomap = new LandmarksISOMAPProjection();
        lisomap.setNumberNeighbors(numNeighbors);

        AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
        try {
            return new Output(lisomap.project(in.matrix, diss));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Input implements InputInterface {

        public AbstractMatrix matrix;

        public Input(@Param(name = "points matrix") AbstractMatrix matrix) {
            if (matrix == null) {
                throw new IllegalArgumentException("A points matrix must be provided.");
            }
            this.matrix = matrix;
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
}
