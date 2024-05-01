package chzzk.grassdiary.web.exceptions;

import lombok.Getter;

@Getter
public class ErrorObject {
    private int status;

    private String message;

    public ErrorObject(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
