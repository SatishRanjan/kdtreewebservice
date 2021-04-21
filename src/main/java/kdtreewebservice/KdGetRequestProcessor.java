package kdtreewebservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KdGetRequestProcessor implements IRequestProcessor {
	@Override
	public HttpResponse ProcessRequest(HttpRequest request) throws Exception {
		String status = "";
		byte[] content = null;
		String contentType = "text/html";
		if (request != null && request.getPath() != null && !request.getPath().isBlank()) {
			if (request.getPath().equals("/")) {
				String path = "\\index.html";
				String cannonicalPath = new File(".").getCanonicalPath();
				Path filePath = Paths.get(cannonicalPath, path);
				if (Files.exists(filePath)) {
					contentType = getFileContentType(filePath);
					content = Files.readAllBytes(filePath);
					status = "200 OK";
				} else {
					content = "<h1>Index file not found </h1>".getBytes();
					status = "404 Not Found";
				}
			} else {				
				KdTreeStore kdTreeStore = KdStoreConfigManager.get_kdTreeStore();
				if (request.getQueryStrings() != null && !request.getQueryStrings().isEmpty()) {
					if (kdTreeStore.getDimension() == request.getQueryStrings().size()) {
						int[] coordinate = new int[kdTreeStore.getDimension()];
						int index = 0;
						Set<Entry<String, String>> entrySet = request.getQueryStrings().entrySet();
						for (Entry<String, String> entry : entrySet) {
							coordinate[index] = Integer.parseInt(entry.getValue());
							++index;
						}
						status = "200 OK";
						boolean doCoordinateExist = kdTreeStore.search(coordinate);
						String result = IntStream.of(coordinate).mapToObj(Integer::toString)
								.collect(Collectors.joining(", "));

						if (doCoordinateExist) {
							content = String.format("The coordinate (%s) exists in the k-d tree store", result)
									.getBytes();
						} else {
							content = String.format("The coordinate (%s) does not exists in the k-d tree store", result)
									.getBytes();
						}
					} else {
						content = String.format("The supported k-d tree dimension is %s", kdTreeStore.getDimension())
								.getBytes();
					}
				}
			}
		}

		HttpResponse response = new HttpResponse(status, contentType, content);
		return response;
	}

	private static String getFileContentType(Path filePath) throws IOException {
		return Files.probeContentType(filePath);
	}
}
