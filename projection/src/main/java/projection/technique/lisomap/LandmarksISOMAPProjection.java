/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.lisomap;

import datamining.neighbors.ANN;
import datamining.neighbors.Pair;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import mdsj.ClassicalScaling;
import projection.technique.Projection;
import projection.technique.isomap.Dijkstra;
import projection.util.ConnectedGraphGenerator;

/**
 *
 * @author PC
 */
public class LandmarksISOMAPProjection implements Projection {

    private static final float EPSILON = 0.01f;
    private int numNeighbors;

    public LandmarksISOMAPProjection() {
        this.numNeighbors = 8;
    }

    @Override
    public AbstractMatrix project(DistanceMatrix dmat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AbstractMatrix project(AbstractMatrix matrix, AbstractDissimilarity diss) throws IOException {
        long start = System.currentTimeMillis();

        int size = matrix.getRowCount();

        //getting the landmarks randomly
        int nrlandmarks = (int) Math.sqrt(size);
        ArrayList<Integer> landmarks = getLandmarks(matrix, diss, nrlandmarks);

        //creating a graph with its nearest neighbors
        ANN ann = new ANN(numNeighbors);
        Pair[][] neighborhood = ann.execute(matrix, diss);

        //assuring the connectivity (????)
        ConnectedGraphGenerator congraph = new ConnectedGraphGenerator();
        congraph.execute(neighborhood, matrix, diss);

        //creating the landmaks distance matrix
        double[][] input = new double[nrlandmarks][size];

        //calculating the shortest paths
        Dijkstra dijkstra = new Dijkstra(neighborhood);
        for (int i = 0; i < landmarks.size(); i++) {
            float[] distances = dijkstra.execute(landmarks.get(i));

            for (int j = 0; j < distances.length; j++) {
                input[i][j] = distances[j];

                if (Double.isNaN(input[i][j]) || Double.isInfinite(input[i][j])) {
                    System.out.println("Error.... L-ISOMAP");
                }
            }
        }

        //executing the landmaks mds
        double[][] output = new double[2][size];
        ClassicalScaling.lmds(input, output);

        //creating the final projection
        DenseMatrix projection = new DenseMatrix();
        ArrayList<Integer> ids = matrix.getIds();
        float[] classData = matrix.getClassData();
        ArrayList<String> labels = matrix.getLabels();

        for (int i = 0; i < size; i++) {
            float[] vect = new float[2];
            vect[0] = (float) output[0][i];
            vect[1] = (float) output[1][i];

            if (labels.isEmpty()) {
                projection.addRow(new DenseVector(vect, ids.get(i), classData[i]));
            } else {
                projection.addRow(new DenseVector(vect, ids.get(i), classData[i]), labels.get(i));
            }
        }

        long finish = System.currentTimeMillis();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                "Executing the Landmarks ISOMAP algorithm time: {0}s", (finish - start) / 1000.0f);

        return projection;
    }

    private ArrayList<Integer> getLandmarks(AbstractMatrix matrix, AbstractDissimilarity diss, int size) {
        ArrayList<Integer> landmarks = new ArrayList<Integer>();

        HashSet<Integer> sample = new HashSet<Integer>();
        Random random = new Random(System.currentTimeMillis());

        while (sample.size() < size) {
            int index = (int) (random.nextFloat() * (matrix.getRowCount()));
            if (index < matrix.getRowCount() && !sample.contains(index)) {
                AbstractVector row1 = matrix.getRow(index);

                boolean add = true;
                Iterator<Integer> iterator = sample.iterator();
                while (iterator.hasNext()) {
                    AbstractVector row2 = matrix.getRow(iterator.next());

                    if (diss.calculate(row1, row2) < EPSILON) {
                        add = false;
                    }
                }

                if (add) {
                    sample.add(index);
                }
            }
        }

        Iterator<Integer> iterator = sample.iterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            landmarks.add(index);

            System.out.println(index);
        }

        return landmarks;
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