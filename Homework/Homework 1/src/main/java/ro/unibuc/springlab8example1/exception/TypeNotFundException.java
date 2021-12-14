package ro.unibuc.springlab8example1.exception;

public class TypeNotFundException extends RuntimeException {
    public TypeNotFundException(String message) {
        super(message);
    }
}
