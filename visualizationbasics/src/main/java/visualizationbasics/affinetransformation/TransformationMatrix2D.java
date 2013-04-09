/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualizationbasics.affinetransformation;

import java.util.Arrays;

/**
 * Represents a transformation matrix in 2D. The first operation called
 * (translate(), rotate(), or scale()) is the first to be applied.
 *
 * @author Fernando V. Paulovich
 */
public class TransformationMatrix2D {

    private TransformationMatrix2D() {
        mat = new float[3][3];
        Arrays.fill(mat[0], 0);
        Arrays.fill(mat[1], 0);
        Arrays.fill(mat[2], 0);
    }

    /**
     * Create a new instance of "TransformationMatrix2D".
     *
     * @return The new instance.
     */
    public static TransformationMatrix2D newInstance() {
        return new TransformationMatrix2D();
    }

    /**
     * Apply a rotation anti-clockwise by theta to the current matrix
     * (this*R(theta)).
     *
     * @param theta The rotation angle (in degrees).
     * @return The matrix (this*R(theta)).
     */
    public final TransformationMatrix2D rotate(float theta) {
        TransformationMatrix2D rotmat = new TransformationMatrix2D();
        rotmat.loadIdentity();
        rotmat.mat[0][0] = (float) Math.cos(Math.toRadians(theta));
        rotmat.mat[0][1] = -(float) Math.sin(Math.toRadians(theta));
        rotmat.mat[1][0] = (float) Math.sin(Math.toRadians(theta));
        rotmat.mat[1][1] = (float) Math.cos(Math.toRadians(theta));
        this.mat = rotmat.mult(this).mat;
        return this;
    }

    /**
     * Apply a scale to the current matrix (this*S(x,y)).
     *
     * @param x The x scale factor.
     * @param y The y scale factor.
     * @return The matrix (this*S(x,y)).
     */
    public final TransformationMatrix2D scale(float x, float y) {
        TransformationMatrix2D scalemat = new TransformationMatrix2D();
        scalemat.loadIdentity();
        scalemat.mat[0][0] = x;
        scalemat.mat[1][1] = y;
        this.mat = scalemat.mult(this).mat;
        return this;
    }

    /**
     * Apply a translation to the current matrix (this*T(x,y)).
     *
     * @param x The x translation step.
     * @param y The y translation step.
     * @return The matrix (this*T(x,y)).
     */
    public final TransformationMatrix2D translate(float x, float y) {
        TransformationMatrix2D transmat = new TransformationMatrix2D();
        transmat.loadIdentity();
        transmat.mat[0][2] = x;
        transmat.mat[1][2] = y;
        this.mat = transmat.mult(this).mat;
        return this;
    }

    /**
     * Load identity to the current matrix.
     */
    public final void loadIdentity() {
        Arrays.fill(mat[0], 0);
        Arrays.fill(mat[1], 0);
        Arrays.fill(mat[2], 0);
        mat[0][0] = mat[1][1] = mat[2][2] = 1;
    }

    /**
     * Multiply the current matrix by "matrix" (this*matrix).
     *
     * @param matrix The matrix to multiply.
     * @return The resulting matrix (this*matrix).
     */
    public final TransformationMatrix2D mult(TransformationMatrix2D matrix) {
        TransformationMatrix2D multmat = new TransformationMatrix2D();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                multmat.mat[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    multmat.mat[i][j] = multmat.mat[i][j] + mat[i][k] * matrix.mat[k][j];
                }
            }
        }

        return multmat;
    }

    /**
     * Apply the current matrix to the position (x,y).
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The new position modified by the current matrix.
     */
    public final float[] apply(float x, float y) {
        float[] newpoint = new float[2];
        newpoint[0] = mat[0][0] * x + mat[0][1] * y + mat[0][2];
        newpoint[1] = mat[1][0] * x + mat[1][1] * y + mat[1][2];
        return newpoint;
    }

    public TransformationMatrix2D inverse() {
        TransformationMatrix2D inv = new TransformationMatrix2D();

        //calculating the determinant 
        float det = mat[0][0] * (mat[2][2] * mat[1][1] - mat[2][1] * mat[1][2])
                - mat[1][0] * (mat[2][2] * mat[0][1] - mat[2][1] * mat[0][2])
                + mat[2][0] * (mat[1][2] * mat[0][1] - mat[1][1] * mat[0][2]);

        //inverting the matrix
        inv.mat[0][0] = (mat[2][2] * mat[1][1] - mat[2][1] * mat[1][2]) / det;
        inv.mat[0][1] = (-(mat[2][2] * mat[0][1] - mat[2][1] * mat[0][2])) / det;
        inv.mat[0][2] = (mat[1][2] * mat[0][1] - mat[1][1] * mat[0][2]) / det;

        inv.mat[1][0] = (-(mat[2][2] * mat[1][0] - mat[2][0] * mat[1][2])) / det;
        inv.mat[1][1] = (mat[2][2] * mat[0][0] - mat[2][0] * mat[0][2]) / det;
        inv.mat[1][2] = (-(mat[1][2] * mat[0][0] - mat[1][0] * mat[0][2])) / det;

        inv.mat[2][0] = (mat[2][1] * mat[1][0] - mat[2][0] * mat[1][1]) / det;
        inv.mat[2][1] = (-(mat[2][1] * mat[0][0] - mat[2][0] * mat[0][1])) / det;
        inv.mat[2][2] = (mat[1][1] * mat[0][0] - mat[1][0] * mat[0][1]) / det;
        
        return inv;
    }

    /**
     * Print the current matrix.
     */
    public final void print() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TransformationMatrix2D mat = TransformationMatrix2D.newInstance();
        mat.loadIdentity();
        mat.scale(1, -1);
        mat.translate(1, 1);
        mat.scale(50, 50);
        mat.translate(30, 30);
        mat.print();
        
        TransformationMatrix2D invert = mat.inverse();
        invert.print();
        
        mat.mult(invert).print();

//        float[] mult = mat.apply(1, 1);
//        System.out.println("(" + mult[0] + "," + mult[1] + ")");
    }

    private float[][] mat; //2D homogeneous transformation matrix
}
