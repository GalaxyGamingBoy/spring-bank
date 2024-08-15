package xyz.mariosm.bank.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.mariosm.bank.exceptions.AccountExistsException;
import xyz.mariosm.bank.exceptions.InternalServerException;

import java.util.Map;

@RestControllerAdvice
public class InternalServerAdvice {
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Map<String, Object> accountExistsException(InternalServerException exception) {
        return Map.of("ok", false, "msg", exception.getMessage());
    }
}
