package br.usp.vp.app;

import br.usp.vp.controller.Controller;
import br.usp.vp.view.MainView;

public class Main {
	
	public static void main(String[] args) throws Exception {

		MainView view = new MainView();
		
		new Controller();
		Controller.loadData(DataSets.iris);
		Controller.init(view.getTopPanel(),view.getMiddlePanel(), view.getBottomPanel());
		
//		view.setExtendedState(view.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		view.setVisible(true);
	}
}
