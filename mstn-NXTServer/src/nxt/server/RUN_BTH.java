package nxt.server;

import lejos.nxt.Button;

/**
 * questa classe e' il main del NXT
 * 
 * TODO prendi in considerazione anche bluetooth 
 * 
 * @author marco
 * 
 */
public class RUN_BTH {
	
	private static BTHConnectionPoint connector;

	public static final void main(String[] args) {
		
		connector = new BTHConnectionPoint();
		connector.start();
		
		// TODO
		// inizializzazone del NXT
		// warming up delle risorse

		while (!Button.ESCAPE.isPressed()){
			// do nothing
		}
		// esci dal sistema
		System.exit(0);
		

	}

}
