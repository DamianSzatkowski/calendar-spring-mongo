package pl.umk.mat.damianszat.calendar.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            InvalidOperationException.class
    })
    public ResponseEntity<Object>handleCustomException(Exception ex, WebRequest request){

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if(ex instanceof InvalidOperationException){
            httpStatus = HttpStatus.CONFLICT;
        }

        return handleExceptionInternal(ex, httpHeaders, httpStatus, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status,
                                                             WebRequest request){
        ApiError apiError = new ApiError(status, ex.getMessage(), ex);

        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }
}
