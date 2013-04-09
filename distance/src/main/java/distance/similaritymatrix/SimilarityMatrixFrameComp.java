/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distance.similaritymatrix;

import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;
import visualizationbasics.coordination.IdentityCoordinator;

/**
 *
 * @author Fernando V. Paulovic
 */
public class SimilarityMatrixFrameComp implements AbstractComponent<SimilarityMatrixFrameComp.Input, OutputInterface> {

    private static final long serialVersionUID = 1L;
    private transient IdentityCoordinator coordinator;

    @Override
    public OutputInterface execute(Input in) throws VisPipelineException {
        SimilarityMatrixFrame smf = new SimilarityMatrixFrame();
        smf.display(in.model);
        smf.setCoordinator(coordinator);

        return null;
    }

    public static class Input implements InputInterface {

        public SimilarityMatrixModel model;

        public Input(@Param(name = "similarity matrix model") SimilarityMatrixModel model) {
            if (model == null) {
                throw new IllegalArgumentException("A similarity matrix model must be provided.");
            }
            this.model = model;
        }
    }

    public void attach(@Param(name = "identity coordinator") IdentityCoordinator coordinator) {
        this.coordinator = coordinator;
    }
}
