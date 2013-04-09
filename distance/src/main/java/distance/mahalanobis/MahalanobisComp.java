/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distance.mahalanobis;

import distance.DistanceMatrix;
import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class MahalanobisComp implements AbstractComponent<MahalanobisComp.Input, MahalanobisComp.Output> {

    private static final long serialVersionUID = 1L;

    @Override
    public Output execute(Input in) throws VisPipelineException {
        Mahalanobis m = new Mahalanobis();
        return new Output(m.getDistanceMatrix(in.matrix));
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

        public DistanceMatrix distanceMatrix;

        public Output(DistanceMatrix distanceMatrix) {
            this.distanceMatrix = distanceMatrix;
        }
    }
}
