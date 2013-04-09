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
public class GaussianKernelDistance extends KernelDistance {

    public GaussianKernelDistance setParameters(float sigma) {
        this.sigma = sigma;
        return this;
    }

    public float kernel(AbstractVector v1, AbstractVector v2) {
        Euclidean diss = new Euclidean();
        float dist = diss.calculate(v1, v2);
        return (float) Math.exp(-(dist * dist) / (sigma * sigma));
    }

    private float sigma = 1.0f;
}
