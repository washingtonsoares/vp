package br.usp.vp.app;

import javax.swing.JFrame;

import matrix.AbstractMatrix;

import br.usp.vp.controller.ControllerHandle;
import br.usp.vp.view.MainView;

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
		
		AbstractMatrix dataMatrix = ControllerHandle.getInstance().
				loadData(DataSets.iris);
		
		ControllerHandle.getInstance().initModels(dataMatrix);
	}
}
