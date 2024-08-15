package xyz.mariosm.bank.exceptions;

public class InternalServerException extends Exception {
    public InternalServerException(String message) {
        super("An internal error occured! " + message);
    }
}
