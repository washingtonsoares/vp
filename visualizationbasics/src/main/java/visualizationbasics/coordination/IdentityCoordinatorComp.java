/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationbasics.coordination;

// import vispipelinebasics.annotations.VisComponent;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
/* @VisComponent(hierarchy = "Coordination",
name = "Identity Coordination",
description = "Create an identity coordination object.") */
public class IdentityCoordinatorComp implements AbstractComponent<InputInterface, IdentityCoordinatorComp.Output> {

    private static final long serialVersionUID = 1L;

    @Override
    public Output execute(InputInterface in) throws VisPipelineException {
        return new Output(new IdentityCoordinator());
    }

    public static class Output implements OutputInterface {

        public IdentityCoordinator coordinator;

        public Output(IdentityCoordinator coordinator) {
            this.coordinator = coordinator;
        }
    }
}
