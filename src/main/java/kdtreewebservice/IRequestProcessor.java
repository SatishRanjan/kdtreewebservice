package kdtreewebservice;

public interface IRequestProcessor {
	HttpResponse ProcessRequest(HttpRequest request) throws Exception;
}
