package xyz.mariosm.bank.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.mariosm.bank.exceptions.AccountNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class AccountNotFoundAdvice {
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, Object> accountNotFoundException(AccountNotFoundException exception) {
        return Map.of("ok", false, "msg", exception.getMessage());
    }
}
