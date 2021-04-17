package finalproject5278;

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
			sendClientResponse(httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
			HttpResponse errorResponse = new HttpResponse("500 Internal Server Error", "text/html",
					e.getMessage().getBytes());
			sendClientResponse(errorResponse);
		}
	}

	private void sendClientResponse(HttpResponse response) {
		try {
			if (response.getContent() == null) {
				_clientSocket.close();
				return;
			}
			OutputStream clientOutput = _clientSocket.getOutputStream();
			clientOutput.write(("HTTP/1.1 \r\n" + response.getStatus()).getBytes());
			clientOutput.write(("ContentType: " + response.getContentType() + "\r\n").getBytes());
			clientOutput.write("\r\n".getBytes());
			clientOutput.write(response.getContent());
			clientOutput.write("\r\n\r\n".getBytes());
			clientOutput.flush();
			_clientSocket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
