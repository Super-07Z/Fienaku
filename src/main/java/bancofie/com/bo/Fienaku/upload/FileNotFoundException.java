package bancofie.com.bo.Fienaku.upload;

public class FileNotFoundException extends StorageException {

	private static final long serialVersionUID = 8482217129851689197L;

	public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}