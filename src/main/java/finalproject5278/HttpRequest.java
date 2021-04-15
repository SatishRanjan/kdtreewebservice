package finalproject5278;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HttpRequest {
	String _httpMethod;
	String _path;
	String _httpVersion;
	String _requestContentType;
	Hashtable<String, String> _headers;
	Hashtable<String, String> _queryStrings;
	String _requestBody;

	private HttpRequest() {

	}

	public String getHttpMethod() {
		return _httpMethod;
	}

	public String getPath() {
		return _path;
	}

	public String getHttpVersion() {
		return _httpVersion;
	}

	public String getRequestContentType() {
		return _requestContentType;
	}

	public Hashtable<String, String> getHeaders() {
		return _headers;
	}

	public Hashtable<String, String> getQueryStrings() {
		return _queryStrings;
	}

	public String getRequestBody() {
		return _requestBody;
	}

	private HttpRequest setHttpMethod(String httpMethod) {
		this._httpMethod = httpMethod;
		return this;
	}

	private HttpRequest setHttpVersion(String httpVersion) {
		this._httpVersion = httpVersion;
		return this;
	}

	private HttpRequest setPath(String path) {
		this._path = path;
		return this;
	}

	private HttpRequest setRequestContentType(String requestContentType) {
		this._requestContentType = requestContentType;
		return this;
	}

	private HttpRequest setHeaders(Hashtable<String, String> headers) {
		this._headers = headers;
		return this;
	}

	private HttpRequest setQueryStrings(Hashtable<String, String> queryStrings) {
		this._queryStrings = queryStrings;
		return this;
	}

	private HttpRequest setRequestBody(String requestBody) {
		this._requestBody = requestBody;
		return this;
	}

	public static class HttpRequestBuilder {
		public static HttpRequest Build(InputStream inputStream) throws Exception {
			if (inputStream == null) {
				return null;
			}

			// Create BufferedReader from InputStream
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
			String request = getRequest(bufferReader);
			if (request == null || request.isBlank()) {
				return null;
			}

			System.out.println("Request value is : " + request);
			HttpRequest httpRequest = new HttpRequest();

			// Get the values from request
			String[] requestsLines = request.split("\r\n");
			System.out.println("Request lines are : " + requestsLines);

			String[] requestLine = requestsLines[0].split(" ");
			String method = requestLine[0];
			String version = requestLine[2];
			String path = requestLine[1];
			String requestContentType = requestsLines[1].split(" ")[1];

			httpRequest.setHttpMethod(method).setHttpVersion(version).setPath(path)
					.setRequestContentType(requestContentType);

			// Fill in the header values
			Hashtable<String, String> headersHt = new Hashtable<String, String>();
			for (int h = 2; h < requestsLines.length; h++) {
				String header = requestsLines[h];
				String[] headerParts = header.split(":");
				if (headerParts.length > 1) {
					headersHt.put(headerParts[0], headerParts[1].trim());
				}

				if (!headersHt.isEmpty()) {
					httpRequest.setHeaders(headersHt);
				}
			}

			System.out.println("Headers are: " + headersHt);

			// Get the request body content and set it to httpRequest object
			if (headersHt.containsKey("Content-Length")) {
				int contentLength = Integer.parseInt(headersHt.get("Content-Length"));
				if (contentLength > 0) {
					String content = getRequestContent(bufferReader);
					httpRequest.setRequestBody(content);
					System.out.println("Request content is: " + content);
				}
			}
			
			// Build the query string and set it to httpRequest object
			Hashtable<String, String> queryStringsKeyVal = getQueryStrings(path);
			if (!queryStringsKeyVal.isEmpty()) {
				httpRequest.setQueryStrings(queryStringsKeyVal);
			}			

			return httpRequest;
		}

		private static String getRequest(BufferedReader bufferReader) throws IOException {
			if (bufferReader == null) {
				return null;
			}

			StringBuilder requestBuilder = new StringBuilder();
			String line;

			while ((line = bufferReader.readLine()) != null) {
				if (line.isBlank()) {
					break;
				}
				requestBuilder.append(line + "\r\n");
			}

			String request = requestBuilder.toString();
			if (request.isBlank()) {
				return null;
			}

			return request;
		}

		private static String getRequestContent(BufferedReader bufferReader) throws IOException {
			StringBuilder payload = new StringBuilder();
			while (bufferReader.ready()) {
				payload.append((char) bufferReader.read());
			}

			return payload.toString();
		}

		private static Hashtable<String, String> getQueryStrings(String path) throws Exception {
			Hashtable<String, String> queryStringsKeyVal = new Hashtable<String, String>();
			if (path == null || path.isEmpty()) {
				return queryStringsKeyVal;
			}

			String queryString = null;
			String[] pathValues = path.split("\\?");
			if (pathValues.length > 1) {
				queryString = pathValues[1];
				String[] keyVals = queryString.split("&");
				for (String keyVal : keyVals) {
					if (!keyVal.isBlank()) {
						String[] individualKeyval = keyVal.split("=");
						if (individualKeyval.length > 1) {
							String key = individualKeyval[0];
							if (!queryStringsKeyVal.containsKey(key)) {
								queryStringsKeyVal.put(key, individualKeyval[1]);
							}
						}
					}
				}
			}

			return queryStringsKeyVal;
		}
	}
}
