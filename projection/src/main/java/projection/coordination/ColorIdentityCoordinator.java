/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.coordination;

import java.awt.Color;
import visualizationbasics.model.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import projection.model.ProjectionInstance;
import projection.model.ProjectionModel;
import visualizationbasics.coordination.AbstractCoordinator;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class ColorIdentityCoordinator extends AbstractCoordinator {

    @Override
    public void coordinate(List<AbstractInstance> selectedInstances, Object parameter) {
        if (selectedInstances.isEmpty()) {
            for (AbstractModel am : models) {
                am.cleanSelectedInstances();
            }
        } else {
            // an index to speed-up the coordination process
            HashSet<Integer> selectedInstancesIds = new HashSet<Integer>();
            for (AbstractInstance sel : selectedInstances) {
                selectedInstancesIds.add(sel.getId());
            }

            for (AbstractModel am : models) {
                if (am != selectedInstances.get(0).getModel()) {
                    if (am instanceof ProjectionModel) {
                        ArrayList<AbstractInstance> selcoord = new ArrayList<AbstractInstance>();

                        ArrayList ins = am.getInstances();
                        for (int i = 0; i < ins.size(); i++) {
                            AbstractInstance ai = (AbstractInstance) ins.get(i);

                            if (selectedInstancesIds.contains(ai.getId())) {
                                if (ai instanceof ProjectionInstance) {
                                    ((ProjectionInstance) ai).setColor((Color) parameter);
                                }
                                selcoord.add(ai);
                            }
                        }

                        am.setSelectedInstances(selcoord);
                        am.notifyObservers();
                    }
                }
            }
        }
    }
}
