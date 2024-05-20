package chzzk.grassdiary.exception.file;

import chzzk.grassdiary.exception.response.ErrorCode;
import chzzk.grassdiary.exception.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> converterExceptionHandler(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.error(ErrorCode.REQUEST_PARAM_NOT_MATCH));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> converterSecondExceptionHandler(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.error(ErrorCode.REQUEST_PARAM_NOT_MATCH));
    }
}
