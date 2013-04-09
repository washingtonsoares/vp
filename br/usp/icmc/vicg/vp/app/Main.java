package br.usp.icmc.vicg.vp.app;

import javax.swing.JFrame;

import matrix.AbstractMatrix;
import br.usp.icmc.vicg.vp.controller.ControllerHandle;
import br.usp.icmc.vicg.vp.model.util.DataLoader;
import br.usp.icmc.vicg.vp.view.MainView;

public class Main {
	
	public static void main(String[] args) throws Exception {

		MainView view = new MainView();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.pack();
		view.setVisible(true);
		
		ControllerHandle.getInstance().initView(
				view.getTopPanel(),
				view.getMiddlePanel(), 
				view.getBottomPanel());
		
		AbstractMatrix dataMatrix = DataLoader.loadData(DataSets.iris);
		
		ControllerHandle.getInstance().attachData(dataMatrix, false);
	}
}
