package xyz.mariosm.bank.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.mariosm.bank.exceptions.AccountExistsException;

import java.util.Map;

@RestControllerAdvice
public class AccountExistsAdvice {
    @ExceptionHandler(AccountExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Map<String, Object> accountExistsException(AccountExistsException exception) {
        return Map.of("ok", false, "msg", exception.getMessage());
    }
}
