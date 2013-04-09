package br.usp.icmc.vicg.vp.view.misc;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import br.usp.icmc.vicg.vp.controller.ControllerHandle;

public class ToolBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private JToolBar toolBar;
	
	public ToolBar() {
		
		super();

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		// Holder for buttons
		this.toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		// Add buttons
		addreprojectItemsButton();
		
		// Add tool bar to pane
		this.add(toolBar);
	}
	
	private void addreprojectItemsButton() {

		ToolButton projectSubset = new ToolButton("Project Subset");
		
		projectSubset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				ControllerHandle.getInstance().reprojectItems();
			}
		});

		toolBar.add(projectSubset);
	}

	class ToolButton extends JButton {

		private static final long serialVersionUID = 1L;

		public ToolButton(String text) {

			this.setText(text);
		}
	}
	
	public void setBackground(Color color) {
		
		super.setBackground(color);
		
		if (toolBar != null) {

			for (Component c : toolBar.getComponents()) {
			
				c.setBackground(color);
			}
			toolBar.setBackground(color);
		}
	}
}
