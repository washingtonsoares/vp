package br.usp.vp.view.projection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import projection.model.ProjectionModel;
import visualizationbasics.color.ColorScalePanel;
import visualizationbasics.view.selection.AbstractSelection;

public class RichProjectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int verticalGap = 15;
	private static final int fontSize = 12;
	
	private ProjectionPanel projectionPanel;
	private ColorScalePanel colorscale;
	private JLabel titleLabel;
	
	public RichProjectionPanel(ProjectionModel model, String text) {
		
		projectionPanel = new ProjectionPanel(model);
		
		colorscale = createColorScale(model);
		
		JPanel upperBar = new JPanel();
		upperBar.setLayout(new GridLayout(1,2));
		
		titleLabel = new JLabel();
		titleLabel.setOpaque(true);
		titleLabel.setText(text);
		titleLabel.setFont(new Font(null, Font.BOLD, fontSize));
		
		upperBar.add(titleLabel);
		upperBar.add(colorscale);
		
		this.setLayout(new BorderLayout(0, verticalGap));
		
		this.add(upperBar, BorderLayout.PAGE_START);
		this.add(projectionPanel, BorderLayout.CENTER);
	}
	
	public ProjectionPanel getProjectionPanel() {
		
		return projectionPanel;
	}
	
	public void setSelection(AbstractSelection selection) {
		
		this.projectionPanel.setSelection(selection);
	}
	
	private ColorScalePanel createColorScale(ProjectionModel model) {
		
		ColorScalePanel colorscale = new ColorScalePanel(null);
		colorscale.setColorTable(model.getColorTable());
		colorscale.setBackground(getBackground());
		
		return colorscale;
	}
	
	@Override
	public final void setBackground(Color bg) {

		super.setBackground(bg);

		if (this.colorscale != null) {
			
			this.colorscale.setBackground(bg);
		}
		if (this.projectionPanel != null) {
			
			this.projectionPanel.setBackground(bg);
		}
		if (this.titleLabel != null) {
			
			this.titleLabel.setBackground(bg);
		}
	}
}
