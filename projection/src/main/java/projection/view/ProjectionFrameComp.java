/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.view;

import java.awt.Dimension;
import projection.model.ProjectionModel;
import java.util.ArrayList;
import javax.swing.JFrame;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.exception.VisPipelineException;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.InputInterface;
import vispipelinebasics.interfaces.OutputInterface;
import visualizationbasics.coordination.AbstractCoordinator;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class ProjectionFrameComp implements AbstractComponent<ProjectionFrameComp.Input, OutputInterface> {

    private static final long serialVersionUID = 1L;
    private String title;
    private Dimension size;
    private transient ArrayList<AbstractCoordinator> coordinators;

    public ProjectionFrameComp() {
        this.title = "";
        this.size = new Dimension(600, 600);
    }

    @Override
    public OutputInterface execute(Input in) throws VisPipelineException {
        ProjectionFrame frame = new ProjectionFrame();
        frame.setSize(size);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle(title);
        frame.setModel(in.model);

        if (coordinators != null) {
            for (int i = 0; i < coordinators.size(); i++) {
                frame.addCoordinator(coordinators.get(i));
            }
        }

        return null;
    }

    public static class Input implements InputInterface {

        public ProjectionModel model;

        public Input(@Param(name = "projection model") ProjectionModel model) {
            if (model == null) {
                throw new IllegalArgumentException("A projection model must be provided.");
            }
            this.model = model;
        }
    }

    public void attach(@Param(name = "Coordinator") AbstractCoordinator coordinator) {
        if (coordinators == null) {
            coordinators = new ArrayList<AbstractCoordinator>();
        }

        if (coordinator != null) {
            coordinators.add(coordinator);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Dimension getSize() {
        return size;
    }
}
