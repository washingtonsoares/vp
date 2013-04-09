/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining.normalization;

import vispipelinebasics.annotations.Param;
import vispipelinebasics.interfaces.AbstractComponent;
import datamining.normalization.NormalizationFactory.NormalizationType;
import matrix.AbstractMatrix;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class NormalizationComp implements AbstractComponent<NormalizationComp.Input, NormalizationComp.Output> {

    private static final long serialVersionUID = 1L;
    private NormalizationType type;

    public NormalizationComp() {
        this.type = NormalizationType.VECTORS_UNIT_LENGTH;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        AbstractNormalization norm = NormalizationFactory.getInstance(type);
        AbstractMatrix normMatrix = norm.execute(in.matrix);
        return new Output(normMatrix);
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

        public AbstractMatrix normMatrix;

        public Output(AbstractMatrix normMatrix) {
            this.normMatrix = normMatrix;
        }
    }

    /**
     * @return the type
     */
    public NormalizationType getNormalizationType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setNormalizationType(NormalizationType type) {
        this.type = type;
    }
}
