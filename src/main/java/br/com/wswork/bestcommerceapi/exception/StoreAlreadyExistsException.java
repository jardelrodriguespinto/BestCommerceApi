package br.com.wswork.bestcommerceapi.exception;

public class StoreAlreadyExistsException  extends RuntimeException {
    public StoreAlreadyExistsException (String message) {
        super(message);
    }
}