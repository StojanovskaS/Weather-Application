package com.example.weathertask.model.exeptions;

public class ParserFailedException extends RuntimeException{

    public ParserFailedException() {
        super("Can't parse the date!");
    }

}
