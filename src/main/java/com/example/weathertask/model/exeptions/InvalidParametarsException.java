package com.example.weathertask.model.exeptions;

public class InvalidParametarsException extends RuntimeException{

    public InvalidParametarsException() {
        super("The parameter is not valid!");
    }
}
