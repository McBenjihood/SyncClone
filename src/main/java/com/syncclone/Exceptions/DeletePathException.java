package com.syncclone.Exceptions;

public class DeletePathException extends RuntimeException {
    public DeletePathException(String message) {
        super(message);
    }
    public DeletePathException(String message, Throwable cause) {
        super(message, cause);
    }
}
