package finalproject5278;

public class KDPostRequestProcessor implements IRequestProcessor {
	@Override
	public HttpResponse ProcessRequest(HttpRequest request) {
		// TODO Auto-generated method stub
		String content = "<h1>Hello from KDPostRequestProcessor!</h1>";
		HttpResponse response = new HttpResponse("200 OK", "text/html", content.getBytes());
		return response;
	}
}
