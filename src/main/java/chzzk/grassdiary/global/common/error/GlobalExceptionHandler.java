package chzzk.grassdiary.global.common.error;
import chzzk.grassdiary.global.common.error.exception.SystemException;
import chzzk.grassdiary.global.common.response.ErrorCodeModel;
import chzzk.grassdiary.global.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Response> handleCustomException(SystemException exception, WebRequest request) {
        ErrorCodeModel errorCode = exception.getErrorCode();
        Response response = Response.error(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGlobalException(Exception exception, WebRequest request) {
        Response response = new Response(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // status: 500
                "INTERNAL_SERVER_ERROR",
                "예기치 않은 오류가 발생했습니다."
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
