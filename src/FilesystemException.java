public class FilesystemException extends Exception {
    public FilesystemException() {
        super();
    }
    public FilesystemException(String message) {
        super(message);
    }
    public FilesystemException(String message, Exception e) {
        super(message, e);
    }
}
