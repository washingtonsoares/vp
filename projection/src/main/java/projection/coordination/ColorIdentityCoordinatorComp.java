/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.coordination;

// import vispipelinebasics.annotations.VisComponent;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;
import visualizationbasics.coordination.AbstractCoordinator;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class ColorIdentityCoordinatorComp implements AbstractComponent<InputInterface, ColorIdentityCoordinatorComp.Output> {

    @Override
    public Output execute(InputInterface in) throws VisPipelineException {
        return new Output(new ColorIdentityCoordinator());
    }

    public static class Output implements OutputInterface {

        public AbstractCoordinator coordinator;

        public Output(AbstractCoordinator coordinator) {
            this.coordinator = coordinator;
        }
    }
}
