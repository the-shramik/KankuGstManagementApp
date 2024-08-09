package com.kanku.excption;

public class BillNumberAlreadyExistsException extends Exception{
    public BillNumberAlreadyExistsException(String message) {
        super(message);
    }
}
