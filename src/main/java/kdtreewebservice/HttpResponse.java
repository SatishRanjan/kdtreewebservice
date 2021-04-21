package kdtreewebservice;

public class HttpResponse {
	private String _status;
	private String _contentType;
	private byte[] _content;

	public HttpResponse(String status, String contentType, byte[] content) {
		_status = status;
		_contentType = contentType;
		_content = content;
	}

	public String getStatus() {
		return _status;
	}

	public String getContentType() {
		return _contentType;
	}

	public byte[] getContent() {
		return _content;
	}
}
