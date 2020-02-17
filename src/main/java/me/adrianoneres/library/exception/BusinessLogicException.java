package me.adrianoneres.library.exception;

public class BusinessLogicException extends RuntimeException {

    private static final String BUSINESS_LOGIC = "error.business-logic";

    public BusinessLogicException() {
        super(BUSINESS_LOGIC);
    }

    public BusinessLogicException(String message) {
        super(message);
    }
}
