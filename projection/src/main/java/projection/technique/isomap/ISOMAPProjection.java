/* ***** BEGIN LICENSE BLOCK *****
 *
 * Copyright (c) 2005-2007 Universidade de Sao Paulo, Sao Carlos/SP, Brazil.
 * All Rights Reserved.
 *
 * This file is part of Projection Explorer (PEx).
 *
 * How to cite this work:
 *  
 @inproceedings{paulovich2007pex,
 author = {Fernando V. Paulovich and Maria Cristina F. Oliveira and Rosane 
 Minghim},
 title = {The Projection Explorer: A Flexible Tool for Projection-based 
 Multidimensional Visualization},
 booktitle = {SIBGRAPI '07: Proceedings of the XX Brazilian Symposium on 
 Computer Graphics and Image Processing (SIBGRAPI 2007)},
 year = {2007},
 isbn = {0-7695-2996-8},
 pages = {27--34},
 doi = {http://dx.doi.org/10.1109/SIBGRAPI.2007.39},
 publisher = {IEEE Computer Society},
 address = {Washington, DC, USA},
 }
 *  
 * PEx is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * PEx is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 *
 * This code was developed by members of Computer Graphics and Image
 * Processing Group (http://www.lcad.icmc.usp.br) at Instituto de Ciencias
 * Matematicas e de Computacao - ICMC - (http://www.icmc.usp.br) of 
 * Universidade de Sao Paulo, Sao Carlos/SP, Brazil. The initial developer 
 * of the original code is Fernando Vieira Paulovich <fpaulovich@gmail.com>.
 *
 * Contributor(s): Rosane Minghim <rminghim@icmc.usp.br>
 *
 * You should have received a copy of the GNU General Public License along 
 * with PEx. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */
package projection.technique.isomap;

import distance.DistanceMatrix;
import projection.technique.Projection;
import projection.util.MeshGenerator;
import projection.technique.mds.ClassicalMDSProjection;
import datamining.neighbors.KNN;
import datamining.neighbors.Pair;
import distance.dissimilarity.AbstractDissimilarity;
import matrix.AbstractMatrix;
import matrix.dense.DenseMatrix;
import projection.util.ConnectedGraphGenerator;

import java.io.IOException;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class ISOMAPProjection implements Projection {

    private int numNeighbors;

    public ISOMAPProjection() {
        this.numNeighbors = 10;
    }

    @Override
    public AbstractMatrix project(AbstractMatrix matrix, AbstractDissimilarity diss) throws IOException {
        //the new distance matrix
        DistanceMatrix new_dmat = new DistanceMatrix(matrix.getRowCount());
        new_dmat.setIds(matrix.getIds());
        new_dmat.setClassData(matrix.getClassData());

        //creating a graph with its nearest neighbors
        KNN knn = new KNN(numNeighbors);
        Pair[][] neighborhood = knn.execute(matrix, diss);

        //assuring the connectivity (????)
        ConnectedGraphGenerator cgg = new ConnectedGraphGenerator();
        cgg.execute(neighborhood, matrix, diss);

        Dijkstra d = new Dijkstra(neighborhood);

        for (int i = 0; i < matrix.getRowCount(); i++) {
            float[] dist = d.execute(i);

            for (int j = 0; j < dist.length; j++) {
                new_dmat.setDistance(i, j, dist[j]);

                if (dist[j] == Float.POSITIVE_INFINITY) {
                    System.out.println("Error.... ISOMAP");
                    return new DenseMatrix();
                }
            }
        }

        //projecting using the classical scaling
        ClassicalMDSProjection cmds = new ClassicalMDSProjection();
        return cmds.project(new_dmat);
    }

    @Override
    public AbstractMatrix project(DistanceMatrix dmat) throws IOException {
        //the new distance matrix
        DistanceMatrix new_dmat = new DistanceMatrix(dmat.getElementCount());
        new_dmat.setIds(dmat.getIds());
        new_dmat.setLabels(dmat.getLabels());
        new_dmat.setClassData(dmat.getClassData());

        //creating a graph with its nearest neighbors
        KNN knn = new KNN(numNeighbors);
        Pair[][] neighborhood = knn.execute(dmat);

        //assuring the connectivity (????)
        MeshGenerator meshgen = new MeshGenerator();
        neighborhood = meshgen.execute(neighborhood, dmat);

        Dijkstra d = new Dijkstra(neighborhood);

        for (int i = 0; i < dmat.getElementCount(); i++) {
            float[] dist = d.execute(i);

            for (int j = 0; j < dist.length; j++) {
                new_dmat.setDistance(i, j, dist[j]);
            }
        }

        //projecting using the classical scaling
        ClassicalMDSProjection cmds = new ClassicalMDSProjection();
        return cmds.project(new_dmat);
    }

    /**
     * @return the number of neighbors
     */
    public int getNumberNeighbors() {
        return numNeighbors;
    }

    /**
     * @param numNeighbors the number of neighbors to set
     */
    public void setNumberNeighbors(int numNeighbors) {
        this.numNeighbors = numNeighbors;
    }
}
