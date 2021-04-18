package finalproject5278;

public class KdPostRequestProcessor implements IRequestProcessor {
	@Override
	public HttpResponse ProcessRequest(HttpRequest request) {
		try {
			KdTreeStore kdTreeStore = KdStoreConfigManager.get_kdTreeStore();
			String[] inCoordinates = request.getRequestBody().split(",");
			int[] coordinate = new int[inCoordinates.length];
			int ci = 0;
			while (ci < inCoordinates.length) {
				coordinate[ci] = Integer.parseInt(inCoordinates[ci]);
				++ci;
			}

			kdTreeStore.insert(coordinate);
			String content = String.format("The coordinate (%s) has been successfully insertted in K-d tree store",
					request.getRequestBody());
			HttpResponse response = new HttpResponse("200 OK", "text/html", content.getBytes());
			return response;

		} catch (Exception e) {
			HttpResponse response = new HttpResponse("500 Internal Server Error", "text/html",
					e.getMessage().getBytes());
			return response;
		}
	}
}
