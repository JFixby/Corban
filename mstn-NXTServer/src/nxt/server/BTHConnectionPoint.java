package nxt.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;

/**
 * questo thread rimane in ascolto di una connessione usb quanto un client si
 * connette la classe gestisce request e response delegando la parte di business
 * ad altre classi
 * 
 * @author marco
 * 
 */
public class BTHConnectionPoint extends Thread {
 
	public BTHConnectionPoint() {

	}

	@Override
	public void run() {

		while (true) {
			// rimani in attesa di una connessione USB
			System.out.println("wait for client");
			// FNXTConnection conn = USB.waitForConnection();
			BTConnection conn = Bluetooth.waitForConnection();
			// un client si e' connesso
			System.out.println("client connected");
			Sleep.sleep(200);
			// preparo gli stream sui quali rimango in ascolto
			DataInputStream input = conn.openDataInputStream();
			DataOutputStream output = conn.openDataOutputStream();
			Sleep.sleep(200);
			// preparo la connessione
			Connection connection = new Connection(input, output);

			try {
				// ottengo la stringa che rappresenta la richiesta come stringa
				String requestStr = connection.getStringRequest();
				Print.out(requestStr);
				
				// costruisco la richiesta
				Request request = Utils.getRequestFromJSON(requestStr);
				
				Response response = new Response();
				
				// delego al dispatcher
				Dispatcher dispatcher = new Dispatcher();
				Print.out("S0");
				dispatcher.process(request, response);
				Print.out("S1");
				// costruisco la risposta in JSON
				String jsonResponse = Utils.getJSONFromResponse(response);
				// spedisco la risposta
				System.out.println(jsonResponse);
				System.out.println("X");
				Sleep.sleep(5000);
				connection.send(jsonResponse);
				Sleep.sleep(3000);
				// chiudo la connessione
				connection.close();
			} catch (EOFException e) {
				System.out.println("conn closed by client");
			} catch (IOException e) {
				System.out.println(e.getMessage());
				String jsonError = Utils
						.createJSONErrorResponse(e.getMessage());
				try {
					connection.send(jsonError);
					connection.close();
				} catch (IOException e1) {
					System.out.println("cannot send res");
				}
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
				String jsonError = Utils
						.createJSONErrorResponse(e.getMessage());
				try {
					connection.send(jsonError);
					connection.close();
				} catch (IOException e1) {
					System.out.println("cannot send res");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				try {
					connection.send(e.getMessage());
					connection.close();
				} catch (IOException e1) {
					System.out.println("cannot send res");
				}
			}

			LCD.clear();
		}
	}

}
