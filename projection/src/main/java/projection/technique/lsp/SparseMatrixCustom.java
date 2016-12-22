package projection.technique.lsp;

import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.sparse.Element;
import matrix.sparse.SparseMatrix;
import matrix.sparse.SparseVector;
import visualizationbasics.util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Washington on 28/11/16.
 */
public class SparseMatrixCustom extends SparseMatrix {

    @Override
    public void load(String filename) throws IOException {
        this.rows = new ArrayList();
        this.attributes = new ArrayList();
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(filename));
            char[] e = in.readLine().trim().toCharArray();
            if(e.length != 2) {
                throw new IOException("Wrong format of header information.");
            }

            if(e[0] != 83) {
                throw new IOException("Wrong matrix format. It is not the sparse format.");
            }

            int nrobjs = Integer.parseInt(in.readLine());
            int nrdims = Integer.parseInt(in.readLine());
            String line = in.readLine();
            StringTokenizer t1 = new StringTokenizer(line, ";");

            while(t1.hasMoreTokens()) {
                String t2 = t1.nextToken();
                this.attributes.add(t2.trim());
            }

            if(this.attributes.size() > 0 && this.attributes.size() != nrdims) {
                throw new IOException("The number of attributes does not match with the dimensionality of matrix (" + this.attributes.size() + " - " + nrdims + ").");
            }

            while((line = in.readLine()) != null && line.trim().length() > 0) {
                StringTokenizer t21 = new StringTokenizer(line, ";");
                String id = t21.nextToken().trim();
                float klass = 0.0F;
                ArrayList values = new ArrayList();

                while(t21.hasMoreTokens()) {
                    String token = t21.nextToken();
                    StringTokenizer t3;
                    int index;
                    float value;
                    if(e[1] == 89) {
                        if(t21.hasMoreTokens()) {
                            t3 = new StringTokenizer(token, ":");
                            index = Integer.parseInt(t3.nextToken().trim());
                            value = Float.parseFloat(t3.nextToken().trim());
                            values.add(new Element(index, value));
                        } else {
                            klass = Float.parseFloat(token.trim());
                        }
                    } else {
                        if(e[1] != 78) {
                            throw new IOException("Unknown class data option");
                        }

                        t3 = new StringTokenizer(token, ":");
                        index = Integer.parseInt(t3.nextToken().trim());
                        value = Float.parseFloat(t3.nextToken().trim());
                        values.add(new Element(index, value));
                    }
                }

                if(Util.isParsableToInt(id)) {
                    this.addRow(new SparseVector(values, Integer.parseInt(id), klass, nrdims));
                } else {
                    this.addRow(new SparseVector(values, Util.convertToInt(id), klass, nrdims), id);
                }
            }

            if(this.getRowCount() != nrobjs) {
                throw new IOException("The number of vectors does not match with the matrix size (" + this.getRowCount() + " - " + nrobjs + ").");
            }
        } catch (FileNotFoundException var24) {
            throw new IOException("File " + filename + " does not exist!");
        } catch (IOException var25) {
            throw new IOException(var25.getMessage());
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException var23) {
                    Logger.getLogger(SparseMatrixCustom.class.getName()).log(Level.SEVERE, (String)null, var23);
                }
            }

        }

    }


}
