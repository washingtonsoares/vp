/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationbasics.color;

import java.awt.Color;

/**
 *
 * @author Fatore
 */
public class FromXToY extends ColorScale {
    
    public FromXToY(int[] x, int[] y) {
        
        colors = new java.awt.Color[256];
        
        float rInc = (y[0] - x[0]) / (float) colors.length;
        float gInc = (y[1] - x[1]) / (float) colors.length;
        float bInc = (y[2] - x[2]) / (float) colors.length;
        
        for (int i = 0; i < colors.length; i++) {
            
            int r = (int) Math.floor(x[0] + rInc * i);
            int g = (int) Math.floor(x[1] + gInc * i);
            int b = (int) Math.floor(x[2] + bInc * i);
            
            colors[i] = new Color(r,g,b);
        }
    }
}
