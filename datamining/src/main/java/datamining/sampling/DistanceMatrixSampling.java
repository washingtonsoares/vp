/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datamining.sampling;

import distance.DistanceMatrix;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Fernando
 */
public class DistanceMatrixSampling {

    public enum SampleType {

        RANDOM("Random sampling");

        private SampleType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        private final String name;
    }

    public DistanceMatrixSampling(SampleType sampletype, int samplesize) {
        this.sampletype = sampletype;
        this.samplesize = samplesize;
    }

    public ArrayList<Integer> execute(DistanceMatrix dmat) throws IOException {
        ArrayList<Integer> sample = new ArrayList<Integer>();

        if (sampletype == DistanceMatrixSampling.SampleType.RANDOM) {
            //creating index to help on avoid duplicate instances
            HashSet<Integer> sampleaux = new HashSet<Integer>();

            int dmatsize = dmat.getElementCount();

            Random random = new Random(System.currentTimeMillis());
            int count = 0;

            while (sample.size() < samplesize && count++ < 2 * dmatsize) {
                int index = random.nextInt(dmatsize);

                if (!sampleaux.contains(index)) {
                    boolean similar = false;

                    Iterator<Integer> it = sampleaux.iterator();
                    while (it.hasNext()) {
                        if (dmat.getDistance(index, it.next()) < EPSILON) {
                            System.out.println("similar instances...");
                            similar = true;
                            break;
                        }
                    }

                    if (!similar) {
                        sampleaux.add(index);
                        sample.add(index);
                    }
                }
            }
        }

        return sample;
    }

    public static DistanceMatrix getDistanceMatrix(DistanceMatrix dmat, ArrayList<Integer> sampleids) {
        //creating the new distance matrix
        DistanceMatrix sampledmat = new DistanceMatrix(sampleids.size());

        float[] cdata = new float[sampleids.size()];
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<String> labels = new ArrayList<String>();

        //copying the distance values
        Iterator<Integer> it1 = sampleids.iterator();
        for (int i = 0; it1.hasNext(); i++) {
            Integer index1 = it1.next();

            Iterator<Integer> it2 = sampleids.iterator();
            for (int j = 0; it2.hasNext(); j++) {
                Integer index2 = it2.next();
                sampledmat.setDistance(i, j, dmat.getDistance(index1, index2));
            }

            cdata[i] = dmat.getClassData()[index1];
            if (!dmat.getIds().isEmpty()) {
                ids.add(dmat.getIds().get(index1));
            }
            if (!dmat.getLabels().isEmpty()) {
                labels.add(dmat.getLabels().get(index1));
            }
        }

        sampledmat.setClassData(cdata);
        sampledmat.setIds(ids);
        sampledmat.setLabels(labels);

        return sampledmat;
    }

    private static final float EPSILON = 0.0000001f;
    private SampleType sampletype;
    private int samplesize;
}
