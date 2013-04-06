package br.usp.vp.controller;

public class ControllerHandle {

	private static Controller controller;
	
	static {
		
		controller = new Controller();
	}
	
	public static Controller getInstance() {
		
		return controller;
	}
}
