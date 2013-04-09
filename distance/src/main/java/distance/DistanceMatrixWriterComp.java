/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distance;

import java.io.IOException;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class DistanceMatrixWriterComp implements AbstractComponent<DistanceMatrixWriterComp.Input, OutputInterface> {

    private static final long serialVersionUID = 1L;
    private String filename;

    @Override
    public OutputInterface execute(Input in) throws VisPipelineException {
        try {
            in.distanceMatrix.save(filename);
        } catch (IOException e) {
            throw new VisPipelineException(e);
        }

        return null;
    }

    public static class Input implements InputInterface {

        public DistanceMatrix distanceMatrix;

        public Input(@Param(name = "distance matrix") DistanceMatrix distanceMatrix) {
            if (distanceMatrix == null) {
                throw new IllegalArgumentException("A distance matrix must be provided.");
            }
            this.distanceMatrix = distanceMatrix;
        }
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}