/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining.dimensionalityreduction;

import datamining.dimensionalityreduction.DimensionalityReductionFactory.DimensionalityReductionType;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import matrix.AbstractMatrix;
import vispipelinebasics.annotations.VisComponent;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando V. Paulovic
 */
@VisComponent(hierarchy = "Transformation",
name = "Dimensionality Reduction",
description = "reduce the data dimensionality to a defined value smaller than the "
+ "original dimensionality.")
public class DimensionalityReductionComp implements AbstractComponent<DimensionalityReductionComp.Input, DimensionalityReductionComp.Output> {

    private static final long serialVersionUID = 1L;
    private DissimilarityType dissimilarityType;
    private DimensionalityReductionType type;
    private int numDimensions;

    public DimensionalityReductionComp() {
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
        this.type = DimensionalityReductionType.PCA;
        this.numDimensions = 2;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        AbstractDimensionalityReduction red = DimensionalityReductionFactory.getInstance(type, numDimensions);
        AbstractDissimilarity diss = DissimilarityFactory.getInstance(dissimilarityType);
        return new Output(red.reduce(in.matrix, diss));
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

        public AbstractMatrix reducedMatrix;

        public Output(AbstractMatrix reducedMatrix) {
            this.reducedMatrix = reducedMatrix;
        }
    }

    /**
     * @return the nrdimensions
     */
    public int getNumberDimensions() {
        return numDimensions;
    }

    /**
     * @param nrdimensions the nrdimensions to set
     */
    public void setNumberDimensions(int nrdimensions) {
        this.numDimensions = nrdimensions;
    }

    /**
     * @return the type
     */
    public DimensionalityReductionType getDimensionalityReductionType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setDimensionalityReductionType(DimensionalityReductionType type) {
        this.type = type;
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

    public int numberOriginalDimensions() {
        // return input.getDimensions();
        // FIXME - XXX
        return 0;
    }
}
