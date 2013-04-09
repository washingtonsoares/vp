/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.stress;

import java.io.IOException;
import projection.stress.StressFactory.StressType;
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
public class StressComp implements AbstractComponent<StressComp.Input, StressComp.Output> {

    private static final long serialVersionUID = 1L;
    private StressType stressType = StressType.KRUSKAL;
    private DissimilarityType dissimilarityType = DissimilarityType.EUCLIDEAN;

    public StressComp() {
        this.stressType = StressType.KRUSKAL;
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        float stress;
        Stress stressCalc = StressFactory.getInstance(stressType);

        if (in.matrix != null) { //using a matrix
            AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
            stress = stressCalc.calculate(in.projection, in.matrix, diss);
        } else { // if (in.distanceMatrix != null) { using a distance matrix
            // Input constructors guarantee that either matrix or distanceMatrix is not null
            // so we don't have to check again
            stress = stressCalc.calculate(in.projection, in.distanceMatrix);
        }

        return new Output(stress);
    }

    public static class Input implements InputInterface {

        public AbstractMatrix projection, matrix;
        public DistanceMatrix distanceMatrix;

        public Input(@Param(name = "projection") AbstractMatrix projection,
                @Param(name = "points matrix") AbstractMatrix matrix) {
            if (projection == null || matrix == null) {
                throw new IllegalArgumentException("A projection and a points matrix must be provided.");
            }
            this.projection = projection;
            this.matrix = matrix;
        }

        public Input(@Param(name = "projection") AbstractMatrix projection,
                @Param(name = "distance matrix") DistanceMatrix distanceMatrix) {
            if (projection == null || distanceMatrix == null) {
                throw new IllegalArgumentException("A projection and a distance matrix must be provided.");
            }
            this.projection = projection;
            this.distanceMatrix = distanceMatrix;
        }
    }

    public static class Output implements OutputInterface {

        public float stress;

        public Output(float stress) {
            this.stress = stress;
        }
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

    /**
     * @return the stress type
     */
    public StressType getStressType() {
        return stressType;
    }

    /**
     * @param stressType the stress type to set
     */
    public void setStressType(StressType stressType) {
        this.stressType = stressType;
    }
}
