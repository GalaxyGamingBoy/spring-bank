package xyz.mariosm.bank.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super("An internal error occurred! " + message);
    }
}
