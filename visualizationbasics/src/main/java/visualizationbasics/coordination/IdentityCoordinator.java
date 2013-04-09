/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationbasics.coordination;

import visualizationbasics.model.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class IdentityCoordinator extends AbstractCoordinator {

    @Override
    public void coordinate(List<AbstractInstance> selectedInstances, Object parameter) {
        if (selectedInstances.isEmpty()) {
            for (AbstractModel am : models) {
                am.cleanSelectedInstances();
            }
        } else {
            //creating an index to speed-up the coordination process
            HashSet<Integer> selids = new HashSet<Integer>();
            for (AbstractInstance sel : selectedInstances) {
                selids.add(sel.getId());
            }

            for (AbstractModel am : models) {
                if (am != selectedInstances.get(0).getModel()) {
                    ArrayList<AbstractInstance> selcoord = new ArrayList<AbstractInstance>();

                    ArrayList ins = am.getInstances();
                    for (int i = 0; i < ins.size(); i++) {
                        AbstractInstance ai = (AbstractInstance) ins.get(i);

                        if (selids.contains(ai.getId())) {
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
