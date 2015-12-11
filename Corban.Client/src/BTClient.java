import java.io.IOException;

import nxt.client.Connection;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

public class BTClient {

	/**
	 * @param args
	 * @throws IOException
	 * @throws Throwable
	 */
	public static void main(String[] args) throws IOException, Throwable {
		// TODO Auto-generated method stub
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

		Connection c = new Connection(conn.getInputStream(),
				conn.getOutputStream());
		Thread.sleep(2000);
		c.send("{\"action\":\"GET\",\"resource\":\"/\"}\n");
		System.out.println(wrap(c.waitForResponse()));
	}

	private static String wrap(String w) {
		// TODO Auto-generated method stub
		int p = 0;
		String a = "";
		//w = w.replaceAll("{", "{\n");
		//w = w.replaceAll("}", "}\n");
		for (int i = 0; i < w.length(); i++) {
			if (w.charAt(i) == '{') {
				p++;
				a = a + "{\n";
			}else
			if (w.charAt(i) == '}') {
				p--;
				a = a + "}\n";
			}else{
				a=a+w.charAt(i);
			}
			

		}
		return w;
	}

}
