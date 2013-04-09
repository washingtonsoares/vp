/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.technique.pekalska;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.colt.matrix.linalg.Property;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
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
import projection.technique.Projection;
import projection.technique.sammon.SammonMappingProjection;

/**
 *
 * @author PC
 */
public class PekalskaProjection implements Projection {

    private AbstractMatrix sampledata;
    private AbstractMatrix sampleproj;

    @Override
    public AbstractMatrix project(AbstractMatrix matrix, AbstractDissimilarity diss) {
        long start = System.currentTimeMillis();

        //create the sample distance matrix
        if (sampledata == null) {
            int samplesize = (int) Math.sqrt(matrix.getRowCount());
            sampledata = getSampleData(matrix, samplesize);
            sampleproj = projectSampleData(sampledata, diss);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "Sample projection created... sample size: {0}", samplesize);
        } else if (sampleproj == null) {
            sampleproj = projectSampleData(sampledata, diss);
        }

        //finding V where (D_base.V = Y_base)
        int size = sampledata.getRowCount();
        int nrows = size;
        int ncolumns = size;

        //creating matrix D_base
        DoubleMatrix2D D = new DenseDoubleMatrix2D(nrows, ncolumns);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float dist = diss.calculate(sampledata.getRow(i), sampledata.getRow(j));
                D.setQuick(i, j, dist);
            }
        }

        if (Property.ZERO.isSingular(D)) {
            Property.ZERO.generateNonSingular(D);
        }

        //creating Y_base
        DoubleMatrix2D Y = new DenseDoubleMatrix2D(nrows, 2);

        for (int i = 0; i < sampledata.getRowCount(); i++) {
            float[] array = sampleproj.getRow(i).toArray();

            for (int j = 0; j < array.length; j++) {
                Y.setQuick(i, j, array[j]);
            }
        }

        //solving to find V
        LUDecomposition solver = new LUDecomposition(D);
        //QRDecomposition solver = new QRDecomposition(D);
        DoubleMatrix2D result = solver.solve(Y);

        float[][] aux_V = new float[2][];
        aux_V[0] = new float[ncolumns];
        aux_V[1] = new float[ncolumns];

        for (int i = 0; i < result.rows(); i++) {
            aux_V[0][i] = (float) result.getQuick(i, 0);
            aux_V[1][i] = (float) result.getQuick(i, 1);
        }

        DenseMatrix V = new DenseMatrix();
        V.addRow(new DenseVector(aux_V[0]));
        V.addRow(new DenseVector(aux_V[1]));

        DenseMatrix projection = new DenseMatrix();

        ArrayList<Integer> ids = matrix.getIds();
        float[] classData = matrix.getClassData();
        ArrayList<String> labels = matrix.getLabels();

        //finally, calculating the projection (Y_base = D_base.V)
        for (int i = 0; i < matrix.getRowCount(); i++) {
            AbstractVector row = matrix.getRow(i);
            float[] dists = new float[sampledata.getRowCount()];

            for (int j = 0; j < dists.length; j++) {
                dists[j] = diss.calculate(row, sampledata.getRow(j));
            }

            DenseVector distsvect = new DenseVector(dists);

            float[] proj = new float[2];
            proj[0] = distsvect.dot(V.getRow(0));
            proj[1] = distsvect.dot(V.getRow(1));

            if (labels.isEmpty()) {
                projection.addRow(new DenseVector(proj, ids.get(i), classData[i]));
            } else {
                projection.addRow(new DenseVector(proj, ids.get(i), classData[i]), labels.get(i));
            }
        }

        long finish = System.currentTimeMillis();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                "Pekalska Sammon time: {0}s", (finish - start) / 1000.0f);

        return projection;
    }

    @Override
    public AbstractMatrix project(DistanceMatrix dmat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSampleProjection(AbstractMatrix sampleproj) {
        this.sampleproj = sampleproj;
    }

    public void setSampleMatrix(AbstractMatrix samplematrix) {
        this.sampledata = samplematrix;
    }

    private AbstractMatrix projectSampleData(AbstractMatrix sampledata,
            AbstractDissimilarity diss) {
        DistanceMatrix sampledmat = new DistanceMatrix(sampledata, diss);
        SammonMappingProjection sammon = new SammonMappingProjection();
        sammon.setNumberIterations(sampledmat.getElementCount());
        return sammon.project(sampledmat);
    }

    private AbstractMatrix getSampleData(AbstractMatrix matrix, int samplesize) {
        AbstractMatrix sampledata_aux = new DenseMatrix();

        //create the sample matrix data
        HashSet<Integer> sample = new HashSet<Integer>();
        Random random = new Random(System.currentTimeMillis());

        while (sample.size() < samplesize) {
            int index = (int) (random.nextFloat() * (matrix.getRowCount()));
            if (index < matrix.getRowCount()) {
                sample.add(index);
            }
        }

        //creating the sample matrix
        Iterator<Integer> it = sample.iterator();
        while (it.hasNext()) {
            int index = it.next();
            AbstractVector row = matrix.getRow(index);
            sampledata_aux.addRow(new DenseVector(row.toArray()));
        }

        return sampledata_aux;
    }
}
