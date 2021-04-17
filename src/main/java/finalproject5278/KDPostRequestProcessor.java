package finalproject5278;

public class KdPostRequestProcessor implements IRequestProcessor {
	@Override
	public HttpResponse ProcessRequest(HttpRequest request) {		
		String content = "<h1>Hello from KDPostRequestProcessor!</h1>";
		HttpResponse response = new HttpResponse("200 OK", "text/html", content.getBytes());
		
		KdTreeStore kdTreeStore = KdStoreConfigManager.get_kdTreeStore();
		return response;
	}
}
