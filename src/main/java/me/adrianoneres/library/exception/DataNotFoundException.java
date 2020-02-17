package me.adrianoneres.library.exception;

public class DataNotFoundException extends RuntimeException {

    private static final String NOT_FOUND = "error.not-found";

    public DataNotFoundException() {
        super(NOT_FOUND);
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
