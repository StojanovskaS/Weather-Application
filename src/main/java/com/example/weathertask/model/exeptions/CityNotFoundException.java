package com.example.weathertask.model.exeptions;

public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException() {
        super("City with this id doesn't exist!");
    }
}