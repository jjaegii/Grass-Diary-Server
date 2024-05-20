package chzzk.grassdiary.global.error.exceptions;

public class DiaryEditDateMismatchException extends RuntimeException {
    public DiaryEditDateMismatchException() {
    }

    public DiaryEditDateMismatchException(String message) {
        super(message);
    }

    public DiaryEditDateMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiaryEditDateMismatchException(Throwable cause) {
        super(cause);
    }
}


