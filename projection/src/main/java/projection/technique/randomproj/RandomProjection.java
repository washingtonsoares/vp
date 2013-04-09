/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.randomproj;

import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import java.util.ArrayList;
import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import projection.technique.Projection;

/**
 *
 * @author Fernando
 */
public class RandomProjection implements Projection {

    @Override
    public AbstractMatrix project(DistanceMatrix dmat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AbstractMatrix project(AbstractMatrix matrix, AbstractDissimilarity diss) {
        ArrayList<Integer> ids = matrix.getIds();
        float[] cdata = matrix.getClassData();
        ArrayList<String> labels = matrix.getLabels();
        int size = matrix.getRowCount();

        AbstractMatrix A = createRandomMatrix(matrix.getDimensions());

        AbstractMatrix projection = new DenseMatrix();

        for (int i = 0; i < size; i++) {
            AbstractVector row = matrix.getRow(i);

            float[] vec = new float[2];
            vec[0] = row.dot(A.getRow(0));
            vec[1] = row.dot(A.getRow(1));

            if (labels.isEmpty()) {
                projection.addRow(new DenseVector(vec, ids.get(i), cdata[i]));
            } else {
                projection.addRow(new DenseVector(vec, ids.get(i), cdata[i]), labels.get(i));
            }
        }

        return projection;
    }

    private AbstractMatrix createRandomMatrix(int size) {
        float[][] mat = new float[2][size];
        float factor = (float) (Math.sqrt(3) / Math.sqrt(2));

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                double prob = Math.random();

                if (prob <= (1.0 / 6.0)) {
                    mat[i][j] = factor;
                } else if (prob > (1.0 / 6.0) && prob <= (1.0 / 3.0)) {
                    mat[i][j] = -factor;
                } else {
                    mat[i][j] = 0.0f;
                }
            }
        }

        AbstractMatrix R = new DenseMatrix();
        R.addRow(new DenseVector(mat[0]));
        R.addRow(new DenseVector(mat[1]));

        return R;
    }
}
