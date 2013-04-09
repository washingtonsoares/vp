/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.fastmap;

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
 * @author Fernando
 */
public class FastmapProjection2DComp implements AbstractComponent<FastmapProjection2DComp.Input, FastmapProjection2DComp.Output> {

    private static final long serialVersionUID = 1L;
    private DissimilarityType dissimilarityType;

    public FastmapProjection2DComp() {
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        //projecting
        AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
        FastmapProjection2D fastmap = new FastmapProjection2D();
        return new Output(fastmap.project(in.matrix, diss));
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
        return dissimilarityType;
    }

    /**
     * @param dissimilarityType the dissimilarity type to set
     */
    public void setDissimilarityType(DissimilarityType dissimilarityType) {
        this.dissimilarityType = dissimilarityType;
    }
}
