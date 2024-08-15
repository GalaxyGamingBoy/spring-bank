package xyz.mariosm.bank.exceptions;

public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String username) {
        super(String.format("An account with the username: %s, has already been created.", username));
    }
}
