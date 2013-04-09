/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.model;

import java.io.IOException;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando V. Paulovic
 */
public class XMLModelReaderComp implements AbstractComponent<InputInterface, XMLModelReaderComp.Output> {

    private static final long serialVersionUID = 1L;
    private String filename = "";

    @Override
    public Output execute(InputInterface in) throws VisPipelineException {
        if (filename.trim().length() > 0) {
            XMLModelReader xmr = new XMLModelReader();

            ProjectionModel model = new ProjectionModel();
            try {
                xmr.read(model, filename);
            } catch (IOException e) {
                throw new VisPipelineException(e);
            }
            return new Output(model);
        } else {
            throw new VisPipelineException("A projection model filename must be provided to read from.");
        }
    }

    public static class Output implements OutputInterface {

        public ProjectionModel model;

        public Output(ProjectionModel model) {
            this.model = model;
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
