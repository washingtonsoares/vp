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
public abstract class KernelDistance implements AbstractDissimilarity {

    public float calculate(AbstractVector v1, AbstractVector v2) {
        assert (v1.size() == v2.size()) : "ERROR: vectors of different sizes!";
        
        return (float) Math.sqrt(kernel(v1, v1) - 2 * kernel(v1, v2) + kernel(v2, v2));
    }

    public abstract float kernel(AbstractVector v1, AbstractVector v2);

}
