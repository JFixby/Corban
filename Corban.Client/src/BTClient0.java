import java.io.IOException;

import nxt.client.Connection;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

public class BTClient0 {

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
		c.send("{\"resource\":\"/motor/A\",\"action\":\"PUT\",\"payload\":{\"forward\":true,\"speed\":2}}\n");
		System.out.println((c.waitForResponse()));
		Thread.sleep(4000);
		c.send("{\"resource\":\"/motor/A\",\"action\":\"PUT\",\"payload\":{\"forward\":false,\"speed\":-2}}\n");
		System.out.println((c.waitForResponse()));
	}

	

}
