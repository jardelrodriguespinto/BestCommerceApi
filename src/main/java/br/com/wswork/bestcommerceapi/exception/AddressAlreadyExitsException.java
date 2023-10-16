package br.com.wswork.bestcommerceapi.exception;

public class AddressAlreadyExitsException extends RuntimeException {
    public AddressAlreadyExitsException(String message) {
        super(message);
    }
}
