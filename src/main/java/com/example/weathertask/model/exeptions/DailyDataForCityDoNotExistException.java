package com.example.weathertask.model.exeptions;

public class DailyDataForCityDoNotExistException extends RuntimeException{

    public DailyDataForCityDoNotExistException(String message) {
        super(String.format("Daily data for city: %s don't exists! ",message));
    }
}
