package exception;

/**
 * @author shas7002
 */
public class HttpResponseException extends RuntimeException {
    public HttpResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpResponseException(String message) {
        super(message);
    }
}
