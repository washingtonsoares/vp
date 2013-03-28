/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.vp.distance;

import matrix.AbstractMatrix;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;

/**
 *
 * @author Fatore
 */
public class DimensionsDistanceMatrix extends DistanceMatrix {

    public DimensionsDistanceMatrix(AbstractMatrix matrix, AbstractDissimilarity diss) {
        super(matrix, diss);
    }
}
