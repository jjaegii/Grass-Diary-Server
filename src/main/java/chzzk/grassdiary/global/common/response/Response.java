package chzzk.grassdiary.global.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class Response {
    private int status;
    private String code;
    private String description;

    public Response(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    public static Response error(ErrorCodeModel errorCode) {
        return new Response(
                errorCode.getStatusCode(),
                errorCode.getSystemErrorCode(),
                errorCode.getErrorMessage()
        );
    }

    public static Response success(ErrorCodeModel successCode) {
        return new Response(
                successCode.getStatusCode(),
                successCode.getSystemErrorCode(),
                successCode.getErrorMessage()
        );
    }
}
