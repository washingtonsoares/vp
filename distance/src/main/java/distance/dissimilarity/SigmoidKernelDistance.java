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
public class SigmoidKernelDistance extends KernelDistance {

    public SigmoidKernelDistance setParameters(float sigma, float delta) {
        this.sigma = sigma;
        this.delta = delta;
        return this;
    }

    public float kernel(AbstractVector v1, AbstractVector v2) {
        return (float) Math.tanh(sigma * v1.dot(v2) + delta);
    }

    private float sigma = 1.0f;
    private float delta = 1.0f;
}
