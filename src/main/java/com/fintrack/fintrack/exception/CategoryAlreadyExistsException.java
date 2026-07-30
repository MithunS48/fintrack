package com.fintrack.fintrack.exception;

public class CategoryAlreadyExistsException extends  RuntimeException{

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
