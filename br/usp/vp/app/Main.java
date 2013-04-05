package br.usp.vp.app;

import br.usp.vp.controller.Controller;
import br.usp.vp.view.MainView;

public class Main {
	
	public static void main(String[] args) throws Exception {

		MainView view = new MainView();
		
		new Controller(view.getToolBar(),view.getDualPanel(), view.getTreePanel());
		Controller.loadData(DataSets.iris);
		Controller.init();
		
		view.setVisible(true);
	}
}
