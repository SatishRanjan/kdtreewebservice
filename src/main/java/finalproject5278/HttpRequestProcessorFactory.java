package finalproject5278;

public class HttpRequestProcessorFactory {
	public static HttpResponse ProcessRequest(HttpRequest httpRequest) throws Exception {
		if (httpRequest == null) {
			return null;
		}

		if (httpRequest.getHttpMethod() != null && httpRequest.getHttpMethod().equals("GET")) {

			IRequestProcessor processor = new KDGetRequestProcessor();
			return processor.ProcessRequest(httpRequest);
		} else if (httpRequest.getHttpMethod() != null && httpRequest.getHttpMethod().equals("POST")) {
			IRequestProcessor processor = new KDPostRequestProcessor();
			return processor.ProcessRequest(httpRequest);
		}

		return null;
	}
}
