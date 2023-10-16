package br.com.wswork.bestcommerceapi.exception;

public class CustomerTypeAlreadyExistsException extends RuntimeException {
    public CustomerTypeAlreadyExistsException(String message) {
        super(message);
    }
}