package br.usp.icmc.vicg.vp.model.projection.distance;

import matrix.AbstractMatrix;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;

public class DimensionsDistanceMatrix extends DistanceMatrix {

    public DimensionsDistanceMatrix(AbstractMatrix matrix, AbstractDissimilarity diss) {
        super(matrix, diss);
    }
}
