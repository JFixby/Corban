package nxt.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connection {

	private InputStream inputStream;
	private OutputStream outputStream;

	public Connection(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	public void send(String request) throws IOException {
		DataOutputStream dos = new DataOutputStream(outputStream);
		// questo sembra l'unico metodo che non mi fa perdere caratteri nella trasmissione
		char[] chars = request.toCharArray();
		for (int i=0; i<chars.length; i++){
			dos.writeChar(chars[i]);
			
		}
		dos.flush();
	}

	public String waitForResponse() throws IOException {
		DataInputStream input = new DataInputStream(inputStream);
		StringBuffer buffer = new StringBuffer();
		char ch = input.readChar();
		while (ch != '\n' || ch != -1 ){
			buffer.append(ch);
			ch = input.readChar();
		}
		return buffer.toString();
	}
	
	public void close() throws IOException{
		inputStream.close();
		outputStream.close();
	}

}
