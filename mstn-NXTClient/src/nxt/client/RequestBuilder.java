package nxt.client;

import java.util.logging.Logger;

import nxt.shared.json.JSONObject;
import nxt.shared.json.JSONParseException;
import nxt.shared.json.JSONParser;

/**
 * costruisce una richiesta
 * @author marco
 *
 */
public class RequestBuilder {

	private final static String ACTION_KEY = "action";
	private final static String RESOURCE_KEY = "resource";
	private final static String PAYLOAD_KEY = "payload";
	
	private final static Logger logger = Logger.getLogger(RequestBuilder.class
			.getName());
	
	/**
	 * crea una richiesta di tipo PUT 
	 * @param resource
	 * @param representation rappresentazione JSON 
	 * @return richiesta JSON
	 */
	public static String createPutRequest(String resource, String representation) {
		String response = null;
		JSONObject request = new JSONObject();
		request.put(ACTION_KEY, "PUT");
		request.put(RESOURCE_KEY,  resource );
		try {
			JSONObject payload = JSONParser.parse(representation);
			request.put(PAYLOAD_KEY, payload);
			response = request.toString() + "\n";
		} catch (JSONParseException e) {
			e.printStackTrace();
			logger.severe("incorrect representation: " + e.getMessage());
		}
		
		return response;
	}
	
	/**
	 * 
	 * @param resource
	 * @return richiesta JSON
	 */
	public static String createGetRequest(String resource){
		JSONObject request = new JSONObject();
		request.put(ACTION_KEY, "GET");
		request.put(RESOURCE_KEY,  resource );
		return request.toString()+"\n";
	}

	public static String createPostRequest(String resource,
			String representation) {
		String response = null;
		JSONObject request = new JSONObject();
		request.put(ACTION_KEY, "POST");
		request.put(RESOURCE_KEY,  resource );
		try {
			JSONObject payload = JSONParser.parse(representation);
			request.put(PAYLOAD_KEY, payload);
			response = request.toString() + "\n";
		} catch (JSONParseException e) {
			e.printStackTrace();
			logger.severe("incorrect representation: " + e.getMessage());
		}
		
		return response;
	}
	
}
