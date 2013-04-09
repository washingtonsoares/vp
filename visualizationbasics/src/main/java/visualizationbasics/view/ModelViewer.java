/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualizationbasics.view;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import visualizationbasics.coordination.AbstractCoordinator;
import visualizationbasics.model.AbstractModel;

/**
 *
 * @author paulovich
 */
public interface ModelViewer extends Observer {

    public void setModel(AbstractModel model) ;

    public AbstractModel getModel();

    public void addCoordinator(AbstractCoordinator coordinator) ;

    public ArrayList<AbstractCoordinator> getCoordinators();

    public Container getContainer();

    /**
     * This method is called when the model is changed.
     * It can (must) be used to re-create the visual
     * representation of the model.
     */
    public void update(Observable o, Object arg);
}
