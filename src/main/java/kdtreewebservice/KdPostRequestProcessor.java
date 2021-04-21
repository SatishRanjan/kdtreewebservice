package kdtreewebservice;

public class KdPostRequestProcessor implements IRequestProcessor {
	@Override
	public HttpResponse ProcessRequest(HttpRequest request) {
		try {
			KdTreeStore kdTreeStore = KdStoreConfigManager.get_kdTreeStore();

			if (request != null && request.getRequestBody() != null && !request.getRequestBody().isBlank()) {
				String[] inCoordinates = request.getRequestBody().split(",");
				int[] coordinate = new int[inCoordinates.length];
				int ci = 0;
				while (ci < inCoordinates.length) {
					if (!Utils.isInt(inCoordinates[ci])) {
						HttpResponse response = new HttpResponse("400 Bad Request", "text/html",
								"The k-d tree demiension values must be the integers".getBytes());
						return response;
					}
					coordinate[ci] = Integer.parseInt(inCoordinates[ci]);
					++ci;
				}

				if (kdTreeStore.getDimension() != inCoordinates.length) {
					HttpResponse response = new HttpResponse("400 Bad Request", "text/html", String.format(
							"The k-d tree demiension %s and the coordinates count to insert %s must be the same",
							kdTreeStore.getDimension(), inCoordinates.length).getBytes());
					return response;
				}

				kdTreeStore.insert(coordinate);
				String content = String.format("The coordinate (%s) has been successfully insertted in K-d tree store",
						request.getRequestBody());
				HttpResponse response = new HttpResponse("200 OK", "text/html", content.getBytes());
				return response;
			} else {
				HttpResponse response = new HttpResponse("400 Bad Request", "text/html",
						"Request or request body is empty".getBytes());
				return response;
			}

		} catch (Exception e) {
			HttpResponse response = new HttpResponse("500 Internal Server Error", "text/html",
					e.getMessage().getBytes());
			return response;
		}
	}
}
