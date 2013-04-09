/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualizationbasics.model;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public abstract class AbstractInstance {

    public AbstractInstance(int id) {
        this.id = id;
        this.selected = false;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        model.setChanged();
    }

    public AbstractModel getModel() {
        return model;
    }

    public void setModel(AbstractModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    protected AbstractModel model;
    protected int id;
    protected boolean selected;
}
