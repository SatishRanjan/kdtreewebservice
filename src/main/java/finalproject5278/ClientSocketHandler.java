package finalproject5278;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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

			// Build HttpRequest
			/*
			 * BufferedReader br; StringBuilder requestBuilder = new StringBuilder(); String
			 * line;
			 * 
			 * br = new BufferedReader(new
			 * InputStreamReader(_clientSocket.getInputStream())); /* line = br.readLine();
			 * while(line != null && !line.isBlank()) { requestBuilder.append(line +
			 * "\r\n"); line = br.readLine(); }
			 */

			/*
			 * while ((line = br.readLine()) != null) { if (line.isBlank()) { break; }
			 * requestBuilder.append(line + "\r\n"); }
			 * 
			 * String request = requestBuilder.toString(); if (request.isBlank()) { return;
			 * }
			 * 
			 * System.out.println("Request line are: " + request);
			 * 
			 * String[] requestsLines = request.split("\r\n"); String[] requestLine =
			 * requestsLines[0].split(" "); String method = requestLine[0]; String path =
			 * requestLine[1]; String version = requestLine[2]; String requestContentType =
			 * requestsLines[1].split(" ")[1];
			 * 
			 * List<String> headers = new ArrayList<>(); Hashtable<String, String> headersHt
			 * = new Hashtable<String, String>(); for (int h = 2; h < requestsLines.length;
			 * h++) { String header = requestsLines[h]; headers.add(header); String[]
			 * headerParts = header.split(":"); if (headerParts.length > 1) {
			 * headersHt.put(headerParts[0], headerParts[1].trim()); } }
			 * System.out.println("Headers are: " + headersHt);
			 * 
			 * String accessLog = String.
			 * format("Client %s, method %s, path %s, version %s, requestcontenttype %s, headers %s"
			 * , _clientSocket.toString(), method, path, version, requestContentType,
			 * headers.toString()); System.out.println(accessLog);
			 * 
			 * int contentLength = 0; if (headersHt.containsKey("Content-Length")) {
			 * contentLength = Integer.parseInt(headersHt.get("Content-Length"));
			 * System.out.println("Content length is:" + contentLength); // code to read the
			 * post payload data StringBuilder payload = new StringBuilder(); while
			 * (br.ready()) { payload.append((char) br.read()); }
			 * System.out.println("Payload data is: " + payload.toString()); }
			 */

			HttpRequest request = HttpRequestBuilder.Build(_clientSocket.getInputStream());
			if (request == null) {
				return;
			}

			HttpResponse httpResponse = HttpRequestProcessorFactory.ProcessRequest(request);
			sendClientResponse(_clientSocket, httpResponse);

			/*Path filePath = getFilePath(request.getPath());
			if (Files.exists(filePath)) {
				// file exist
				String contentType;
				contentType = guessContentType(filePath);
				sendResponse(_clientSocket, "200 OK", contentType, Files.readAllBytes(filePath));

			} else {
				byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
				sendResponse(_clientSocket, "404 Not Found", "text/html", notFoundContent);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			try {
				_clientSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static String guessContentType(Path filePath) throws IOException {
		return Files.probeContentType(filePath);
	}

	private static Path getFilePath(String path) {
		if ("/".equals(path)) {
			path = "\\index.html";
		}

		String cannonicalPath = "";
		try {
			cannonicalPath = new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Paths.get(cannonicalPath, path);
	}

	private static void sendResponse(Socket client, String status, String contentType, byte[] content)
			throws IOException {
		OutputStream clientOutput = client.getOutputStream();
		clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
		clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
		clientOutput.write("\r\n".getBytes());
		clientOutput.write(content);
		clientOutput.write("\r\n\r\n".getBytes());
		clientOutput.flush();
		client.close();
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
