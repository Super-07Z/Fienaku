package bancofie.com.bo.fienaku.upload;

public class storageException extends RuntimeException {

	private static final long serialVersionUID = -5502351264978098291L;

	public storageException(String message) {
		super(message);
	}

	public storageException(String message, Throwable cause) {
		super(message, cause);
	}

}