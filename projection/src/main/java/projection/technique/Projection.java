/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique;

import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import matrix.AbstractMatrix;

import java.io.IOException;

/**
 *
 * @author Fernando V. Paulovic
 */
public interface Projection {

    public AbstractMatrix project(AbstractMatrix matrix, AbstractDissimilarity diss) throws IOException;

    public AbstractMatrix project(DistanceMatrix dmat) throws IOException;
}
