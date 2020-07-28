package pl.umk.mat.damianszat.calendar.exception;

import java.time.LocalDateTime;

public class NotFoundException extends RuntimeException {

    private LocalDateTime exceptionTime;

    public NotFoundException(String message){
        super(message);
        exceptionTime = LocalDateTime.now();
    }

    public LocalDateTime getExceptionTime() {
        return exceptionTime;
    }
}
