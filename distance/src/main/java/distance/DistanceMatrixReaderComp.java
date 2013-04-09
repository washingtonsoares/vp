/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distance;

import java.io.IOException;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class DistanceMatrixReaderComp implements AbstractComponent<InputInterface, DistanceMatrixReaderComp.Output> {

    private static final long serialVersionUID = 1L;
    private String filename;

    @Override
    public Output execute(InputInterface in) throws VisPipelineException {
        try {
            return new Output(DistanceMatrix.loadFromFile(filename));
        } catch (IOException e) {
            throw new VisPipelineException(e);
        }
    }

    public static class Output implements OutputInterface {

        public DistanceMatrix distanceMatrix;

        public Output(DistanceMatrix distanceMatrix) {
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