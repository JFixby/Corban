package nxt.server;

import nxt.server.resource.Resource;
import nxt.server.resource.ResourceLocator;
import nxt.shared.json.JSONObject;

/**
 * Il compito di questa classe e' invocare il metodo corretto a seconda
 * dell'azione specificata nella request
 * 
 * @author marco
 * 
 */
public class Dispatcher {

	public void process(Request request, Response response) {
		Print.out("Dispatch");
		// ottieni l'azione
		String action = request.getAction();
		// ottieni la risorsa
		String resourceName = request.getResource();
		Resource resource = ResourceLocator.lookupResource(resourceName);
		Print.out("resource found");
		Print.out("do " + action + " " + resourceName);
		Sleep.sleep(5000);
		// invoca il metodo opportuno del server
		if ("GET".equals(action)) {
			// Print.out("get");
			JSONObject obj = resource.read();
			String ret = obj.toString();
			response.setContent(ret);
			response.setStatus(Response.OK);
		} else if ("PUT".equals(action)) {
			// Print.out("put");
			Print.out("PUT");
			JSONObject representation = request.getPayload();
			Print.out("J "+representation+" "+resource);
			resource.update(representation);
			Print.out("U");
			response.setStatus(Response.OK);
			Print.out("F");
		} else if ("POST".equals(action)) {
			// Print.out("post");
			JSONObject representation = request.getPayload();
			resource.create(representation);
			response.setStatus(Response.OK);
		} else {
			String msg = "unknown action " + action;
			response.setMessage(msg);
			response.setStatus(Response.BAD_REQUEST);
		}
	}

}
