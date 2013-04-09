/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualizationbasics.util.filter;

import vispipelinecommon.filefilter.AbstractFilter;

/**
 *
 * @author Fernando V. Paulovic
 */
public class SCALARFilter extends AbstractFilter {

    public String getDescription() {
        return "Salar file (*.scalar)";
    }

    @Override
    public String getProperty() {
        return "SCALAR.DIR";
    }

    @Override
    public String getFileExtension() {
        return "scalar";
    }

}
