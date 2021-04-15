package finalproject5278;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import finalproject5278.HttpRequest.HttpRequestBuilder;

public class ClientSocketHandler implements Runnable {

	private final Socket _clientSocket;

	public ClientSocketHandler(Socket client) {
		_clientSocket = client;
	}

	@Override
	public void run() {
		System.out.println("Processing client request " + _clientSocket.toString());
		try {
			HttpRequest request = HttpRequestBuilder.Build(_clientSocket.getInputStream());
			if (request == null) {
				return;
			}

			HttpResponse httpResponse = HttpRequestProcessorFactory.ProcessRequest(request);
			sendClientResponse(_clientSocket, httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				_clientSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}	

	private static void sendClientResponse(Socket client, HttpResponse response) throws IOException {
		OutputStream clientOutput = client.getOutputStream();
		clientOutput.write(("HTTP/1.1 \r\n" + response.getStatus()).getBytes());
		clientOutput.write(("ContentType: " + response.getContentType() + "\r\n").getBytes());
		clientOutput.write("\r\n".getBytes());
		clientOutput.write(response.getContent());
		clientOutput.write("\r\n\r\n".getBytes());
		clientOutput.flush();
		client.close();
	}
}
