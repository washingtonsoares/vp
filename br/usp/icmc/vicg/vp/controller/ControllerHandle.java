package br.usp.icmc.vicg.vp.controller;

public class ControllerHandle {

	private static Controller controller;
	
	static {
		
		controller = new Controller();
	}
	
	public static Controller getInstance() {
		
		return controller;
	}
}
