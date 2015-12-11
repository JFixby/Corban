package nxt.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Uri {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(Uri.class.getName());

	private UriInfo uriInfo;

	public Uri(String uri) {
		uriInfo = new UriInfo(uri);
	}


	Connection connection = null;
	
	public Connection open() throws IOException{
		
		NXTComm nxtComm;
		if ("usb".equals(uriInfo.getProtocol())) {
			try {
				// se la connessione � gi�, l'attivo
					nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
					NXTInfo[] info = nxtComm.search(null, NXTCommFactory.USB);
					nxtComm.open(info[0]);
				
				// preparo una nuova connessione
				connection = new Connection(nxtComm.getInputStream(), nxtComm.getOutputStream());
			} catch (NXTCommException e) {
				throw new IOException("Cannot connect to device using usb");
			}
		} else if ("bts".equals(uriInfo.getProtocol())) {
			try {
				
//				
				NXTConnector conn = new NXTConnector();

				conn.addLogListener(new NXTCommLogListener() {

					public void logEvent(String message) {
						System.out.println("BTSend Log.listener: " + message);

					}

					public void logEvent(Throwable throwable) {
						System.out.println("BTSend Log.listener - stack trace: "
								+ throwable.getMessage());

					}

				});
				// Connect to any NXT over Bluetooth
				boolean connected = conn.connectTo("btspp://");

				if (!connected) {
					System.err.println("Failed to connect to any NXT");
					System.exit(1);
				}
//
				
				connection = new Connection(conn.getInputStream(), conn.getOutputStream());
			} catch (Exception e) {
				throw new IOException("Cannot connect to device using bluetooth");
			}
		} else {
			throw new IOException("Unknown protocol " + uriInfo.getProtocol());
		}
		return connection;
	}
	
	public void close() throws IOException{
		connection.close();
		connection = null;
	}
	
	public boolean isOpen(){
		return (connection != null);
	}

	public UriInfo getUriInfo() {
		return uriInfo;
	}

	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}

}
