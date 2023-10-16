package br.com.wswork.bestcommerceapi.exception;

public class CategoryAlreadyExitsException extends RuntimeException {
    public CategoryAlreadyExitsException(String message) {
        super(message);
    }
}
