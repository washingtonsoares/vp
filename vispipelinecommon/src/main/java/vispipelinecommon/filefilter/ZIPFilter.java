/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vispipelinecommon.filefilter;

/**
 *
 * @author paulovich
 */
public class ZIPFilter extends AbstractFilter {

    public String getDescription() {
        return "Zip file (*.zip)";
    }

    @Override
    public String getProperty() {
        return "ZIP.DIR";
    }

    @Override
    public String getFileExtension() {
        return "zip";
    }
}
