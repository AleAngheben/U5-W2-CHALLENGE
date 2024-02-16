package alessandro.angheben.u5w2d5.exceptions;

import alessandro.angheben.u5w2d5.exceptions.payloads.ErrorsPayload;
import alessandro.angheben.u5w2d5.exceptions.payloads.ErrorsPayloadWithList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayloadWithList handleBadRequest(BadRequestException e) {
        List<String> errorsMessage = new ArrayList<>();
        if(e.getErrorList() != null)
            errorsMessage =e.getErrorList().stream().map(err->err.getDefaultMessage()).toList();
        return new ErrorsPayloadWithList(e.getMessage(),new Date(),errorsMessage);

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleNotFound(NotFoundException e) {
        return new ErrorsPayload(e.getMessage(),LocalDateTime.now());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorsPayload("Errore generico", LocalDateTime.now());
    }

}
