/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining.weighing;

import java.util.List;
import matrix.AbstractMatrix;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class AttributeWeighingComp implements AbstractComponent<AttributeWeighingComp.Input, AttributeWeighingComp.Output> {

    private static final long serialVersionUID = 1L;
    private transient List<Float> weights;

    @Override
    public Output execute(Input in) throws VisPipelineException {
        // AbstractNormalization norm = NormalizationFactory.getInstance(type);
        //output = norm.execute(input);
        AbstractMatrix weighedMatrix;
        float[][] points = null;

        if (in.matrix instanceof DenseMatrix) {
            points = new float[in.matrix.getRowCount()][];

            for (int i = 0; i < points.length; i++) {
                points[i] = in.matrix.getRow(i).getValues();
            }
        } else {
            points = in.matrix.toMatrix();
        }

        float[][] wPoints = new float[points.length][in.matrix.getDimensions()];
        for (int col = 0; col < in.matrix.getDimensions(); col++) {
            float weight = this.weights.get(col);
            for (int row = 0; row < in.matrix.getRowCount(); row++) {
                wPoints[row][col] = weight * points[row][col];
            }
        }

        weighedMatrix = new DenseMatrix();
        for (int i = 0; i < in.matrix.getRowCount(); i++) {
            weighedMatrix.addRow(new DenseVector(wPoints[i], in.matrix.getRow(i).getId(), in.matrix.getRow(i).getKlass()));
        }
        weighedMatrix.setAttributes(in.matrix.getAttributes());

        return new Output(weighedMatrix);
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

        public AbstractMatrix weighedMatrix;

        public Output(AbstractMatrix weighedMatrix) {
            this.weighedMatrix = weighedMatrix;
        }
    }

    public void setWeights(List<Float> weights) {
        // FIXME - properly copy container
        this.weights = weights;
    }
}
