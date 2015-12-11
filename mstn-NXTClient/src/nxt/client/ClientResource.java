package nxt.client;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * questa classe permette di accedere ad una risorsa di un nxt identificata da
 * uri
 * 
 * esempio di uri
 * 
 * usb://<nxt-name>/motor/A
 * 
 * TODO
 * esternalizza logger per misurare le performance
 * 
 * @author marco
 * 
 */
public class ClientResource {

	private final static Logger logger = Logger.getLogger(ClientResource.class
			.getName());
	private Uri uri;

	public ClientResource(String uri) {
		this.uri = new Uri(uri);
	}

	/**
	 * ritorna le info sulla risorsa
	 * 
	 * @return una rappresentazione della risorsa in json, null altrimenti
	 */
	public String get() {
		String response = null;
		try {
			Connection connection = uri.open();
			// ottengo la risorsa
			String resourceName = uri.getUriInfo().getResource();
			// preparo la richiesta
			String request = RequestBuilder.createGetRequest(resourceName);
			logger.info(request);
			// invio la richiesta
			connection.send(request);
			// attendo la risposta (bloccante)
			response = connection.waitForResponse();
			// chiudo la connessione
			uri.close();
		} catch (IOException e) {
			if (uri.isOpen()){
				try {
					uri.close();
				} catch (IOException e1) {
					logger.severe("cannot close connection");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		return response;
	}

	/**
	 * modifica la rappresentazione della risorsa
	 * @param representation in JSON della risorsa
	 * @return il risultato dell'azione (indica se azione andata a buon fine)
	 */
	public String put(String representation){
		String response = null;
		try {
			// apri una connessione con il nxt
			Connection connection = uri.open();
			// preparo la richiesta
			String request = RequestBuilder.createPutRequest(uri.getUriInfo().getResource(), representation);
			logger.info(request);
			// invio la richiesta
			connection.send(request);
			// attendo la risposta (bloccante)
			response = connection.waitForResponse();
			// chiudo la connessione
			uri.close();
		} catch (IOException e) {
			if (uri.isOpen()){
				try {
					uri.close();
				} catch (IOException e1) {
					logger.severe("cannot close connection");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			logger.severe(e.getMessage());
		}	
		return response;
	}
	
	/**
	 * crea una risorsa figlia
	 * @param la rappresentazione della risorsa e del suo comportamento
	 * @return
	 */
	public String post(String representation) {
		String response = null;
		try {
			// apri una connessione con il nxt
			Connection connection = uri.open();
			// preparo la richiesta
			String request = RequestBuilder.createPostRequest(uri.getUriInfo().getResource(), representation);
			logger.info(request);
			// invio la richiesta
			connection.send(request);
			// attendo la risposta (bloccante)
			response = connection.waitForResponse();
			// chiudo la connessione
			uri.close();
		} catch (IOException e) {
			if (uri.isOpen()){
				try {
					uri.close();
				} catch (IOException e1) {
					logger.severe("cannot close connection");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			logger.severe(e.getMessage());
		}	
		return response;
	}

	public Uri getUri() {
		return uri;
	}
	public void setUri(Uri uri){
		this.uri = uri;
	}


	
}
