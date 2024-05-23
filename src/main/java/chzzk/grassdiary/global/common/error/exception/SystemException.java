package chzzk.grassdiary.global.common.error.exception;

import chzzk.grassdiary.global.common.response.CodeModel;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private final CodeModel errorCode;

    public SystemException(CodeModel errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public SystemException(CodeModel errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause);
        this.errorCode = errorCode;
    }
}
