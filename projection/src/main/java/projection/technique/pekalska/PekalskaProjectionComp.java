/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.pekalska;

import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author PC
 */
public class PekalskaProjectionComp implements AbstractComponent<PekalskaProjectionComp.Input, PekalskaProjectionComp.Output> {

    private static final long serialVersionUID = 1L;
    private DissimilarityType dissmiliarityType;

    public PekalskaProjectionComp() {
        this.dissmiliarityType = DissimilarityType.EUCLIDEAN;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        PekalskaProjection rapidsammon = new PekalskaProjection();
        AbstractMatrix projection;
        AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissmiliarityType);
        projection = rapidsammon.project(in.matrix, diss);
        return new Output(projection);
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
     * @return the dissimilarity type
     */
    public DissimilarityType getDissimilarityType() {
        return dissmiliarityType;
    }

    /**
     * @param dissmiliarityType the dissimilarity type to set
     */
    public void setDissimilarityType(DissimilarityType dissmiliarityType) {
        this.dissmiliarityType = dissmiliarityType;
    }
}
