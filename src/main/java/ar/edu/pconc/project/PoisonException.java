package org.example;

public class PoisonException extends RuntimeException {
    public PoisonException(String msg) {
        super(msg);
    }
}
