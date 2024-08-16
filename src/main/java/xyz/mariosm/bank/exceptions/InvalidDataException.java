package xyz.mariosm.bank.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super("Invalid data was found! " + message);
    }
}