/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projection.technique.lsp;

import distance.DistanceMatrix;
import matrix.AbstractMatrix;
import projection.technique.isomap.ISOMAPProjection;

import java.io.IOException;

/**
 *
 * @author paulovich
 */
public class LSPProjection2DISOMAP extends LSPProjection2D {
    @Override
    protected AbstractMatrix projectControlPoints(DistanceMatrix dmat_cp) throws IOException {
        ISOMAPProjection isomap = new ISOMAPProjection();
        isomap.setNumberNeighbors(8);
        return isomap.project(dmat_cp);
    }
}
