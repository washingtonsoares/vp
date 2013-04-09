/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.randomproj;

import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando
 */
public class RandomProjectionComp implements AbstractComponent<RandomProjectionComp.Input, RandomProjectionComp.Output> {

    private static final long serialVersionUID = 1L;

    @Override
    public Output execute(Input in) throws VisPipelineException {
        RandomProjection rp = new RandomProjection();
        AbstractMatrix projection;
        projection = rp.project(in.matrix, null);
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
}
