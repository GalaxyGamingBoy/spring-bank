package xyz.mariosm.bank.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.mariosm.bank.exceptions.AccountExistsException;
import xyz.mariosm.bank.exceptions.InvalidDataException;

import java.util.Map;

@RestControllerAdvice
public class InvalidDataAdvice {
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, Object> accountExistsException(InvalidDataException exception) {
        return Map.of("ok", false, "msg", exception.getMessage());
    }
}
