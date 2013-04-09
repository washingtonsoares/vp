/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NormalizationParamView.java
 *
 * Created on 20/06/2009, 11:22:01
 */
package datamining.normalization;

import java.io.IOException;
import vispipelinebasics.interfaces.AbstractParametersView;
import datamining.normalization.NormalizationFactory.NormalizationType;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class NormalizationParamView extends AbstractParametersView {

    /** Creates new form NormalizationParamView */
    public NormalizationParamView(NormalizationComp comp) {
        initComponents();

        this.comp = comp;

        for (NormalizationType normtype : NormalizationType.values()) {
            this.normalizationComboBox.addItem(normtype);
        }

        reset();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        normalizationPanel = new javax.swing.JPanel();
        normalizationComboBox = new javax.swing.JComboBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Normalization Parameters"));
        setLayout(new java.awt.GridBagLayout());

        normalizationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Normalization Technique"));
        normalizationPanel.setLayout(new java.awt.GridBagLayout());
        normalizationPanel.add(normalizationComboBox, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(normalizationPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void reset() {
        normalizationComboBox.setSelectedItem(comp.getNormalizationType());
    }

    @Override
    public void finished() throws IOException {
        comp.setNormalizationType((NormalizationType) normalizationComboBox.getSelectedItem());
    }

    private NormalizationComp comp;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox normalizationComboBox;
    private javax.swing.JPanel normalizationPanel;
    // End of variables declaration//GEN-END:variables
}