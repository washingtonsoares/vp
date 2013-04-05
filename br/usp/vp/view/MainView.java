package br.usp.vp.view;

import java.awt.Color;

import br.usp.vp.view.misc.ToolBar;
import br.usp.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.vp.view.tree.InteractionsTreePanel;

public class MainView extends MainViewTemplate {	

	private static final long serialVersionUID = 6782262222544845423L;
	
	private ToolBar toolBar;
	private DualProjectionsPanel dualPanel;
	private InteractionsTreePanel treePanel;
	
	public MainView() {
		
		super();
		
		createComponents();
		attachComponents();
	}
	
	public ToolBar getToolBar() {
		return toolBar;
	}

	public DualProjectionsPanel getDualPanel() {
		return dualPanel;
	}

	public InteractionsTreePanel getTreePanel() {
		return treePanel;
	}

	private void createComponents() {
		
		toolBar = new ToolBar();
		
		dualPanel = new DualProjectionsPanel();
		dualPanel.setBackground(Color.WHITE);
		
		treePanel = new InteractionsTreePanel();
		treePanel.setBackground(Color.WHITE);
	}
	
	private void attachComponents() {
		
		topPanel.add(toolBar);
		middlePanel.add(dualPanel);
		bottomPanel.add(treePanel);
	}
}
