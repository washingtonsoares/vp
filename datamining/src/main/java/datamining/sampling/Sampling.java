/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining.sampling;

import datamining.clustering.BKmeans;
import distance.dissimilarity.AbstractDissimilarity;
import java.util.*;
import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.MatrixFactory;
import matrix.VectorFactory;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;

/**
 *
 * @author Fernando
 */
public class Sampling {

    public enum SampleType {

        RANDOM("Random sampling"),
        CLUSTERING_CENTROID("Cluestring centroid sampling"),
        CLUSTERING_MEDOID("Cluestring medoid sampling"),
        MAXMIN("Max-min sampling"),
        SPAM("Spam");

        private SampleType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
        private final String name;
    }

    public Sampling(SampleType sampletype, int samplesize) {
        this.sampletype = sampletype;
        this.samplesize = samplesize;
    }

    public AbstractMatrix execute(AbstractMatrix matrix, AbstractDissimilarity diss) {
        AbstractMatrix sampledata = null;

        if (sampletype == SampleType.CLUSTERING_MEDOID) {
            BKmeans bkmeans = new BKmeans(samplesize);
            bkmeans.execute(diss, matrix);
            int[] medoids = bkmeans.getMedoids(matrix);

            sampledata = MatrixFactory.getInstance(matrix.getClass());
            for (int i = 0; i < medoids.length; i++) {
                if (matrix.getLabels().size() > 0) {
                    sampledata.addRow(matrix.getRow(medoids[i]), matrix.getLabel(medoids[i]));
                } else {
                    sampledata.addRow(matrix.getRow(medoids[i]));
                }
            }
        } else if (sampletype == SampleType.CLUSTERING_CENTROID) {
            BKmeans bkmeans = new BKmeans(samplesize);
            bkmeans.execute(diss, matrix);
            sampledata = bkmeans.getCentroids();
        } else if (sampletype == SampleType.MAXMIN) {
            Random random = new Random(System.currentTimeMillis());

            ArrayList<Integer> pivots = new ArrayList<Integer>();
            pivots.add(random.nextInt(matrix.getRowCount()));

            for (int i = 0; i < samplesize - 1; i++) {
                float maxdist = Float.NEGATIVE_INFINITY;
                int maxpivot = 0;

                int size = matrix.getRowCount();
                for (int j = 0; j < size; j++) {
                    float mindist = Float.POSITIVE_INFINITY;
                    int minpivot = 0;

                    for (int k = 0; k < pivots.size(); k++) {
                        float dist = diss.calculate(matrix.getRow(j), matrix.getRow(pivots.get(k)));

                        //getting the smallest distance between the points and the pivots
                        if (mindist > dist) {
                            mindist = dist;
                            minpivot = j;
                        }
                    }

                    //getting the largest distance between the smallest distances
                    if (maxdist < mindist) {
                        maxdist = mindist;
                        maxpivot = minpivot;
                    }
                }

                pivots.add(maxpivot);
            }

            //creating the sample matrix
            sampledata = MatrixFactory.getInstance(matrix.getClass());

            Iterator<Integer> it = pivots.iterator();
            while (it.hasNext()) {
                int index = it.next();
                AbstractVector row = matrix.getRow(index);

                if (matrix.getLabels().size() > 0) {
                    sampledata.addRow(VectorFactory.getInstance(matrix.getClass(),
                            row.toArray(), row.getId(), row.getKlass()), matrix.getLabel(index));
                } else {
                    sampledata.addRow(VectorFactory.getInstance(matrix.getClass(),
                            row.toArray(), row.getId(), row.getKlass()));
                }
            }
        } else if (sampletype == SampleType.SPAM) {
            float[] maxs = new float[matrix.getDimensions()];
            float[] mins = new float[matrix.getDimensions()];

            Arrays.fill(maxs, Float.NEGATIVE_INFINITY);
            Arrays.fill(mins, Float.POSITIVE_INFINITY);

            int size = matrix.getRowCount();
            for (int i = 0; i < size; i++) {
                float[] array = matrix.getRow(i).toArray();

                for (int j = 0; j < array.length; j++) {
                    if (maxs[j] < array[j]) {
                        maxs[j] = array[j];
                    }

                    if (mins[j] > array[j]) {
                        mins[j] = array[j];
                    }
                }
            }

            sampledata = new DenseMatrix();

            Random rand = new Random(System.currentTimeMillis());

            for (int i = 0; i < samplesize; i++) {
                float[] vect = new float[matrix.getDimensions()];

                for (int j = 0; j < vect.length; j++) {
                    vect[j] = rand.nextFloat() * (maxs[j] - mins[j]) + mins[j];
                }

                sampledata.addRow(new DenseVector(vect));
            }
        } else {
            //create the sample matrix data
            sampledata = MatrixFactory.getInstance(matrix.getClass());

            //creating index to help on avoid duplicate or similar vectors
            HashSet<Integer> sample = new HashSet<Integer>();
            Random random = new Random(System.currentTimeMillis());
            int count = 0;

            while (sample.size() < samplesize && count < 2 * matrix.getRowCount()) {
                count++;
                int index = random.nextInt(matrix.getRowCount());

                if (!sample.contains(index)) {
                    AbstractVector row = matrix.getRow(index);
                    boolean similar = false;

                    for (int i = 0; i < sampledata.getRowCount(); i++) {
                        if (similar(row, sampledata.getRow(i))) {
                            System.out.println("similar vectors...");
                            similar = true;
                            break;
                        }
                    }

                    if (!similar) {
                        sampledata.addRow(VectorFactory.getInstance(matrix.getClass(),
                                row.toArray(), row.getId(), row.getKlass()));
                        sample.add(index);
                    }
                }
            }
        }

        return sampledata;
    }

    private boolean similar(AbstractVector v1, AbstractVector v2) {
        float[] othervalues = v1.getValues();
        float[] values = v2.getValues();

        if (othervalues.length != values.length) {
            return false;
        }

        for (int i = 0; i < values.length; i++) {
            if (Math.abs(values[i] - othervalues[i]) > EPSILON) {
                return false;
            }
        }

        return true;
    }
    private static final float EPSILON = 0.0000001f;
    private SampleType sampletype;
    private int samplesize;
}
