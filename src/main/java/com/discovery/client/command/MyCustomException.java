package com.discovery.client.command;

public class MyCustomException extends RuntimeException{
    private static final long serialVersionUID = -4864350962123378098L;

    public MyCustomException(String message) {
        super(message);
    }
}
