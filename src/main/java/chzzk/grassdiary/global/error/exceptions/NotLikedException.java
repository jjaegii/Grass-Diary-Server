package chzzk.grassdiary.global.error.exceptions;

public class NotLikedException extends RuntimeException {
    public NotLikedException() {
    }

    public NotLikedException(String message) {
        super(message);
    }

    public NotLikedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLikedException(Throwable cause) {
        super(cause);
    }
}
