package finalproject5278;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KDGetRequestProcessor implements IRequestProcessor {
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
				content = String.format("<h1>Hello from KDGetRequestProcessor!<p>The the request URI is: %s</p></h1>", request.getPath()).getBytes();
			}
		}		

		HttpResponse response = new HttpResponse(status, contentType, content);
		return response;
	}

	private static String getFileContentType(Path filePath) throws IOException {
		return Files.probeContentType(filePath);
	}	
}
