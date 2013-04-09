/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.model;

import matrix.AbstractMatrix;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class ProjectionModelComp implements AbstractComponent<ProjectionModelComp.Input, ProjectionModelComp.Output> {

    private static final long serialVersionUID = 1L;
    private ProjectionModel.InstanceType instanceType;

    public ProjectionModelComp() {
        this.instanceType = ProjectionModel.InstanceType.POINT_INSTANCE;
    }

    @Override
    public Output execute(Input in) throws VisPipelineException {
        
        ProjectionModel model = new ProjectionModel();
        model.addProjection(in.projection, instanceType);

        return new Output(model);
        // throw new VisPipelineException("A 2D projection should be provided.");
    }

    public static class Input implements InputInterface {

        public AbstractMatrix projection;

        public Input(@Param(name = "2D projection") AbstractMatrix projection) {
            if (projection == null) {
                throw new IllegalArgumentException("A projection must be provided.");
            }
            this.projection = projection;
        }
    }

    public static class Output implements OutputInterface {

        public ProjectionModel model;

        public Output(ProjectionModel model) {
            this.model = model;
        }
    }

    public void setInstanceType(ProjectionModel.InstanceType instanceType) {
        this.instanceType = instanceType;
    }

    public ProjectionModel.InstanceType getInstanceType() {
        return instanceType;
    }
}
