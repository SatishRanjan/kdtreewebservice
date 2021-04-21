package kdtreewebservice;

public class HttpRequestProcessorFactory {
	public static HttpResponse ProcessRequest(HttpRequest httpRequest) throws Exception {
		if (httpRequest == null) {
			return null;
		}

		if (httpRequest.getHttpMethod() != null && httpRequest.getHttpMethod().equals("GET")) {
			IRequestProcessor processor = new KdGetRequestProcessor();
			return processor.ProcessRequest(httpRequest);
		} else if (httpRequest.getHttpMethod() != null && httpRequest.getHttpMethod().equals("POST")) {
			IRequestProcessor processor = new KdPostRequestProcessor();
			return processor.ProcessRequest(httpRequest);
		}

		return null;
	}
}
