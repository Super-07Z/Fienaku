package bancofie.com.bo.fienaku.upload;

public class fileNotFoundException extends storageException {

	private static final long serialVersionUID = 8482217129851689197L;

	public fileNotFoundException(String message) {
        super(message);
    }

    public fileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}