/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distance.dissimilarity;

import matrix.AbstractVector;

/**
 *
 * @author PC
 */
public class PolynomialKernelDistance extends KernelDistance {

    public PolynomialKernelDistance setParameters(float sigma) {
        this.sigma = sigma;
        return this;
    }

    public float kernel(AbstractVector v1, AbstractVector v2) {
        return (float) Math.pow(v1.dot(v2) + 1, sigma);
    }

    private float sigma = 1.0f;
}
