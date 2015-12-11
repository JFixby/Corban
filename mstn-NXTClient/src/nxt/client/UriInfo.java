package nxt.client;

/**
 * contiene le info sull'uri di una risorsa
 * 
 * esempio
 * 	usb://nxtName/motors/A
 * 
 * @author marco
 *
 */
public class UriInfo {

	private final static String PROTOCOL_SEPARATOR = "://";
	private final static String RESOURCE_SEPARATOR = "/";
	
	private String protocol;
	private String nxtId;
	private String resource;
	
	public UriInfo(String uri){
		// separo il protocollo dal resto dell'uri
		protocol = uri.substring(0, uri.indexOf(PROTOCOL_SEPARATOR));
		uri = uri.substring(uri.indexOf(PROTOCOL_SEPARATOR)+PROTOCOL_SEPARATOR.length());
		nxtId = uri.substring(0, uri.indexOf(RESOURCE_SEPARATOR));
		resource = uri.substring(uri.indexOf(RESOURCE_SEPARATOR)+RESOURCE_SEPARATOR.length()-1);
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setNxtId(String nxtId) {
		this.nxtId = nxtId;
	}

	public String getNxtId() {
		return nxtId;
	}
	

	public String getDeviceURL() {
		// TODO potrebbe cambiare a seconda di usb o bt
		StringBuffer sb = new StringBuffer();
		sb.append(protocol).append(PROTOCOL_SEPARATOR);
		return sb.toString();
	}
	
	public static final void main(String[] args){
		String uri = "usb://nxtName/motors/A";
		UriInfo uriInfo = new UriInfo(uri);
		System.out.println(uriInfo.getProtocol());
		System.out.println(uriInfo.getNxtId());
		System.out.println(uriInfo.getResource());
	}
	
	
}
