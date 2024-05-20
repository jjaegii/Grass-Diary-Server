package chzzk.grassdiary.global.common.error.exception;

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
