/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.lsp;

import projection.technique.lsp.LSPProjection2D.ControlPointsType;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.interfaces.AbstractComponent;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import java.util.List;
import matrix.AbstractMatrix;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class LSPProjection2DComp implements AbstractComponent<LSPProjection2DComp.Input, LSPProjection2DComp.Output> {

    private static final long serialVersionUID = 1L;
    private int numIterations;
    private float fractionDelta;
    private int numNeighbors;
    private int numControlPoints;
    private DissimilarityType dissimilarityType;

    public LSPProjection2DComp() {
        this.numControlPoints = 10;
        this.numNeighbors = 10;
        this.fractionDelta = 8.0f;
        this.numIterations = 50;
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        //projecting
        AbstractMatrix projection;
        LSPProjection2D lsp = new LSPProjection2D();
        lsp.setFractionDelta(fractionDelta);
        lsp.setNumberIterations(numIterations);
        lsp.setNumberNeighbors(numNeighbors);
        lsp.setNumberControlPoints(numControlPoints);

        if (in.matrix != null) { //using a matrix
            AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
            lsp.setControlPointsChoice(ControlPointsType.KMEANS);

            if (in.controlPoints != null && in.controlPointsProjection != null) {
                lsp.setControlPoints(in.controlPoints);
                lsp.setControlPointsProjection(in.controlPointsProjection);
            }
            projection = lsp.project(in.matrix, diss);
        } else { // if (dmat != null) { //using a distance matrix
            lsp.setControlPointsChoice(ControlPointsType.KMEDOIDS);
            projection = lsp.project(in.distanceMatrix);
        }

        return new Output(projection);
    }

    public static class Input implements InputInterface {

        public AbstractMatrix matrix;
        public List<Integer> controlPoints;
        public AbstractMatrix controlPointsProjection;
        public DistanceMatrix distanceMatrix;

        public Input(@Param(name = "points matrix") AbstractMatrix matrix) {
            if (matrix == null) {
                throw new IllegalArgumentException("A points matrix must be provided.");
            }
            this.matrix = matrix;
            this.controlPoints = null;
            this.controlPointsProjection = null;
            this.distanceMatrix = null;
        }

        public Input(@Param(name = "points matrix") AbstractMatrix matrix,
                @Param(name = "control points") List<Integer> controlPoints,
                @Param(name = "control points projection") AbstractMatrix controlPointsProjection) {
            if (matrix == null || controlPoints == null || controlPointsProjection == null) {
                throw new IllegalArgumentException("A points matrix, control points and a control points projection must be provided.");
            }
            this.matrix = matrix;
            this.controlPoints = controlPoints;
            this.controlPointsProjection = controlPointsProjection;
            this.distanceMatrix = null;
        }

        public Input(@Param(name = "distance matrix") DistanceMatrix distanceMatrix) {
            if (distanceMatrix == null) {
                throw new IllegalArgumentException("A distance matrix must be provided.");
            }
            this.matrix = null;
            this.controlPoints = null;
            this.controlPointsProjection = null;
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
     * @return the number of control points
     */
    public int getNumberControlPoints() {
        return numControlPoints;
    }

    /**
     * @param numControlPoints the number of control points to set
     */
    public void setNumberControlPoints(int numControlPoints) {
        this.numControlPoints = numControlPoints;
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
        /*if (matrix != null) {
        return matrix.getRowCount();
        } else if (dmat != null) {
        return dmat.getElementCount();
        }*/

        return 0;
    }
}
