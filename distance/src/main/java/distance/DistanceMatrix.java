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
package distance;

import distance.dissimilarity.AbstractDissimilarity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import matrix.AbstractMatrix;
import visualizationbasics.util.Util;

/**
 * This class represents distances between elements. It stores the
 * distance from every element to all other elements.
 * 
 * @author Fernando Vieira Paulovich
 */
public class DistanceMatrix implements Cloneable {

    protected ArrayList<Integer> ids;
    protected ArrayList<String> labels;
    protected float[] cdata;
    protected float[][] distmatrix;
    protected int nrElements; //the number of points
    protected float maxDistance; //Maximun distance in the distmatrix
    protected float minDistance; //Minimum distance in the distmatrix

    public DistanceMatrix(int nrElements) {
        this.maxDistance = Float.NEGATIVE_INFINITY;
        this.minDistance = Float.POSITIVE_INFINITY;
        this.nrElements = nrElements;
        this.distmatrix = new float[nrElements - 1][];

        for (int i = 0; i < this.nrElements - 1; i++) {
            this.distmatrix[i] = new float[i + 1];
        }

        this.ids = new ArrayList<Integer>();
        this.labels = new ArrayList<String>();
        for (int i = 0; i < nrElements; i++) {
            this.ids.add(i);
            this.labels.add(Integer.toString(i));
        }

        this.cdata = new float[nrElements];
        Arrays.fill(this.cdata, 0);
    }

    /**
     * This constructor create a distance distmatrix with distances for
     * one to all other points passed as argument.
     * @param matrix
     * @param diss
     * @throws java.io.IOException 
     */
    public DistanceMatrix(AbstractMatrix matrix, AbstractDissimilarity diss) {
        this.nrElements = matrix.getRowCount();
        this.maxDistance = Float.NEGATIVE_INFINITY;
        this.minDistance = Float.POSITIVE_INFINITY;

        //Create and fill the distance distmatrix
        this.distmatrix = new float[this.nrElements - 1][];

        for (int i = 0; i < this.nrElements - 1; i++) {
            this.distmatrix[i] = new float[i + 1];

            for (int j = 0; j < this.distmatrix[i].length; j++) {
                float distance = diss.calculate(matrix.getRow(i + 1), matrix.getRow(j));
                this.setDistance(i + 1, j, distance);
            }
        }

        this.labels = matrix.getLabels();
        this.ids = matrix.getIds();
        this.cdata = matrix.getClassData();
    }

    protected DistanceMatrix() {
        this.ids = new ArrayList<Integer>();
        this.labels = new ArrayList<String>();
    }

    /**
     * This method modify a distance in the distance matriz.
     * @param indexA The number of the first point.
     * @param indexB The number of the second point.
     * @param value The new value for the distance between the two points.
     */
    public void setDistance(int indexA, int indexB, float value) {
        assert (indexA >= 0 && indexA < nrElements && indexB >= 0 && indexB < nrElements) :
                "ERROR: index out of bounds!";

        if (indexA != indexB) {
            if (indexA < indexB) {
                this.distmatrix[indexB - 1][indexA] = value;
            } else {
                this.distmatrix[indexA - 1][indexB] = value;
            }

            if (minDistance > value && value >= 0.0f) {
                minDistance = value;
            }

            if (maxDistance < value && value >= 0.0f) {
                maxDistance = value;
            }
        }
    }

    /**
     * This method returns the distance between two points.
     * @param indexA The number of the first point.
     * @param indexB The number of the second point.
     * @return The distance between the two points.
     */
    public float getDistance(int indexA, int indexB) {
        assert (indexA >= 0 && indexA < nrElements && indexB >= 0 && indexB < nrElements) :
                "ERROR: index out of bounds!";

        if (indexA == indexB) {
            return 0.0f;
        }

        if (indexA < indexB) {
            return this.distmatrix[indexB - 1][indexA];
        } else {
            return this.distmatrix[indexA - 1][indexB];
        }
    }

    /**
     * This method returns the maximum distance stored on the distance distmatrix.
     * @return Returns the maximun distance stored.
     */
    public float getMaxDistance() {
        return maxDistance;
    }

    /**
     * This method returns the minimum distance stored on the distance distmatrix.
     * @return Returns the minimun distance stored.
     */
    public float getMinDistance() {
        return minDistance;
    }

    /**
     * This method returns the number of points where distances are stored
     * on the distance distmatrix.
     * @return The number of points.
     */
    public int getElementCount() {
        return nrElements;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DistanceMatrix clonedmat = new DistanceMatrix(this.nrElements);
        clonedmat.maxDistance = this.maxDistance;
        clonedmat.minDistance = this.minDistance;

        for (int i = 0; i < this.distmatrix.length; i++) {
            System.arraycopy(this.distmatrix[i], 0, clonedmat.distmatrix[i], 0, this.distmatrix[i].length);
        }

        clonedmat.ids = new ArrayList<Integer>();
        clonedmat.ids.addAll(ids);
        clonedmat.cdata = Arrays.copyOf(cdata, cdata.length);
        clonedmat.labels = new ArrayList<String>();
        clonedmat.labels.addAll(labels);

        return clonedmat;
    }

    public void save(String filename) throws IOException {
        BufferedWriter out = null;

        try {
            out = new BufferedWriter(new FileWriter(filename));

            //writting the number of elements
            out.write(Integer.toString(this.nrElements));
            out.write("\r\n");

            //writing the ids/labels
            if (labels.size() > 0) {
                for (int i = 0; i < labels.size() - 1; i++) {
                    out.write(labels.get(i));
                    out.write(";");
                }

                out.write(labels.get(labels.size() - 1));
                out.write("\r\n");
            } else if (ids.size() > 0) {
                for (int i = 0; i < ids.size() - 1; i++) {
                    out.write(Integer.toString(ids.get(i)));
                    out.write(";");
                }
                out.write(ids.get(ids.size() - 1));
                out.write("\r\n");
            } else {
                for (int i = 0; i < this.nrElements - 1; i++) {
                    out.write(Integer.toString(i) + ";");
                }
                out.write(Integer.toString(this.nrElements - 1) + "\r\n");
            }

            //writing the cdata
            if (cdata != null) {
                for (int i = 0; i < cdata.length - 1; i++) {
                    out.write(Float.toString(cdata[i]));
                    out.write(";");
                }

                out.write(Float.toString(cdata[cdata.length - 1]));
                out.write("\r\n");
            } else {
                for (int i = 0; i < this.nrElements - 1; i++) {
                    out.write("0;");
                }

                out.write("0\r\n");
            }

            for (int i = 0; i < this.distmatrix.length; i++) {
                for (int j = 0; j < this.distmatrix[i].length; j++) {
                    out.write(Float.toString(this.distmatrix[i][j]));

                    if (j < this.distmatrix[i].length - 1) {
                        out.write(";");
                    }
                }

                out.write("\r\n");
            }
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public float[] getClassData() {
        return this.cdata;
    }

    public ArrayList<Integer> getIds() {
        return this.ids;
    }

    public void setClassData(float[] cdata) {
        this.cdata = cdata;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;

        if (labels.isEmpty()) {
            for (Integer i : ids) {
                labels.add(i.toString());
            }
        }
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public static DistanceMatrix loadFromFile(String filename) throws IOException {
        DistanceMatrix distanceMatrix = new DistanceMatrix();
        BufferedReader in = null;

        try {
            ///////////////////////////////////////////////////////////////////
            //getting the header information
            in = new BufferedReader(new FileReader(filename));

            //getting the number of elements
            distanceMatrix.nrElements = Integer.parseInt(in.readLine());

            //getting the elements ids
            StringTokenizer tUrls = new StringTokenizer(in.readLine(), ";");
            distanceMatrix.ids = new ArrayList<Integer>();
            distanceMatrix.labels = new ArrayList<String>();
            while (tUrls.hasMoreTokens()) {
                String id = tUrls.nextToken().trim();

                if (Util.isParsableToInt(id)) {
                    distanceMatrix.ids.add(Integer.parseInt(id));
                } else {
                    distanceMatrix.ids.add(Util.convertToInt(id));
                }

                distanceMatrix.labels.add(id);
            }

            //checking
            if (distanceMatrix.ids.size() != distanceMatrix.nrElements) {
                throw new IOException("The number of ids does not match "
                        + "with the size of matrix (" + distanceMatrix.ids.size()
                        + " - " + distanceMatrix.nrElements + ").");
            }

            //getting the class data
            StringTokenizer tCdata = new StringTokenizer(in.readLine(), ";");
            ArrayList<Float> cdata_aux = new ArrayList<Float>();

            while (tCdata.hasMoreTokens()) {
                String token = tCdata.nextToken();
                cdata_aux.add(Float.parseFloat(token.trim()));
            }

            //checking
            if (distanceMatrix.ids.size() != cdata_aux.size()) {
                throw new IOException("The number of class data items does not match "
                        + "with the size of matrix (" + distanceMatrix.ids.size()
                        + " - " + distanceMatrix.nrElements + ").");
            }

            distanceMatrix.cdata = new float[cdata_aux.size()];
            for (int i = 0; i < distanceMatrix.cdata.length; i++) {
                distanceMatrix.cdata[i] = cdata_aux.get(i);
            }

            ///////////////////////////////////////////////////////////////////
            //creating the distance matrix
            distanceMatrix.maxDistance = Float.NEGATIVE_INFINITY;
            distanceMatrix.minDistance = Float.POSITIVE_INFINITY;
            distanceMatrix.distmatrix = new float[distanceMatrix.nrElements - 1][];

            for (int i = 0; i < distanceMatrix.distmatrix.length; i++) {
                distanceMatrix.distmatrix[i] = new float[i + 1];
            }

            for (int i = 0; i < distanceMatrix.distmatrix.length; i++) {
                String line = in.readLine();
                if (line == null) {
                    throw new IOException("Wrong distance matrix file format.");
                }

                StringTokenizer tDistance = new StringTokenizer(line, ";");
                for (int j = 0; j < distanceMatrix.distmatrix[i].length; j++) {
                    if (!tDistance.hasMoreTokens()) {
                        throw new IOException("Wrong distance matrix file format.");
                    }

                    String token = tDistance.nextToken();
                    float dist = Float.parseFloat(token.trim());
                    distanceMatrix.setDistance(i + 1, j, dist);
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return distanceMatrix;
    }
}
