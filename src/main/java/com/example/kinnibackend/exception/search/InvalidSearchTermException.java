package com.example.kinnibackend.exception.search;

public class InvalidSearchTermException extends RuntimeException{
    public InvalidSearchTermException(String message){
        super(message);
    }
}
