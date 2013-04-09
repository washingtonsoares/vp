/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationbasics.view;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JPanel;
import visualizationbasics.coordination.AbstractCoordinator;
import visualizationbasics.model.AbstractModel;

/**
 *
 * @author paulovich
 */
public abstract class JPanelModelViewer extends JPanel implements ModelViewer {

    public JPanelModelViewer() {
        this.coordinators = new ArrayList<AbstractCoordinator>();
    }

    public void setModel(AbstractModel model) {
        if (this.model != null) {
            this.model.deleteObserver(this);

            for (int i = 0; i < coordinators.size(); i++) {
                coordinators.get(i).deleteModel(this.model);
            }
        }

        this.model = model;

        if (model != null) {
            model.addObserver(this);
        }
    }

    public AbstractModel getModel() {
        return model;
    }

    public void addCoordinator(AbstractCoordinator coordinator) {
        if (coordinator != null) {
            if (!coordinators.contains(coordinator)) {
                coordinators.add(coordinator);
                coordinator.addModel(model);
            }
        }
    }

    public ArrayList<AbstractCoordinator> getCoordinators() {
        return coordinators;
    }

    public Container getContainer() {
        return this;
    }

    /**
     * This method is called when the model is changed.
     * It can (must) be used to re-create the visual
     * representation of the model.
     */
    public abstract void update(Observable o, Object arg);
    
    protected ArrayList<AbstractCoordinator> coordinators;
    protected AbstractModel model;
}
