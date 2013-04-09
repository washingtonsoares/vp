/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining.sampling;

import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import datamining.sampling.Sampling.SampleType;
import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author paulovich
 */
public class SamplingComp implements AbstractComponent<SamplingComp.Input, SamplingComp.Output> {

    private static final long serialVersionUID = 1L;
    private DissimilarityType dissimilarityType;
    private int sampleSize;
    private SampleType sampleType;

    public SamplingComp() {
        this.dissimilarityType = DissimilarityType.EUCLIDEAN;
        this.sampleSize = 0;
        this.sampleType = SampleType.RANDOM;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        AbstractDissimilarity dissimilarity = DissimilarityFactory.getInstance(dissimilarityType);
        Sampling sampling = new Sampling(sampleType, sampleSize);
        AbstractMatrix sampleMatrix = sampling.execute(in.matrix, dissimilarity);
        return new Output(sampleMatrix);
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

        public AbstractMatrix sampleMatrix;

        public Output(AbstractMatrix sampleMatrix) {
            this.sampleMatrix = sampleMatrix;
        }
    }

    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public int getSampleSize() {
        return this.sampleSize;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public DissimilarityType getDissimilarityType() {
        return dissimilarityType;
    }

    public void setDissimilarityType(DissimilarityType dissimilarityType) {
        this.dissimilarityType = dissimilarityType;
    }
}
