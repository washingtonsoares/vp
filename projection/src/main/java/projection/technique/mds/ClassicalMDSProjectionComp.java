/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.mds;

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
public class ClassicalMDSProjectionComp implements AbstractComponent<ClassicalMDSProjectionComp.Input, ClassicalMDSProjectionComp.Output> {

    private static final long serialVersionUID = 1L;
    private DissimilarityType dissimilarityType;

    public ClassicalMDSProjectionComp() {
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        //projecting
        ClassicalMDSProjection mds = new ClassicalMDSProjection();
        AbstractMatrix projection;

        if (in.matrix != null) { //using a matrix
            AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
            projection = mds.project(in.matrix, diss);
        } else { // if (dmat != null) { //using a distance matrix
            projection = mds.project(in.distanceMatrix);
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
     * @return the dissimilarity type
     */
    public DissimilarityType getDissimilarityType() {
        return dissimilarityType;
    }

    /**
     * @param dissimilarityType the dissmilarity type to set
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