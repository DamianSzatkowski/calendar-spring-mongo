package pl.umk.mat.damianszat.calendar.exception;

import java.time.LocalDateTime;

public class InvalidOperationException extends RuntimeException {

    private LocalDateTime exceptionTime;

    public InvalidOperationException(String message){
        super(message);
        exceptionTime = LocalDateTime.now();
    }

    public LocalDateTime getExceptionTime() {
        return exceptionTime;
    }
}
