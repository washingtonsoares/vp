/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.model;

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
public class XMLModelWriterComp implements AbstractComponent<XMLModelWriterComp.Input, OutputInterface> {

    private static final long serialVersionUID = 1L;
    private String filename;

    @Override
    public OutputInterface execute(Input in) throws VisPipelineException {
        if (filename != null && filename.trim().length() > 0) {
            XMLModelWriter xmw = new XMLModelWriter();
            try {
                xmw.write(in.model, filename);
            } catch (IOException e) {
                throw new VisPipelineException(e);
            }
        } else {
            throw new VisPipelineException("A projection model filename must be provided to write to.");
        }

        return null;
    }

    public static class Input implements InputInterface {

        public ProjectionModel model;

        public Input(@Param(name = "model") ProjectionModel model) {
            if (model == null) {
                throw new IllegalArgumentException("A projection model must be provided.");
            }
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
