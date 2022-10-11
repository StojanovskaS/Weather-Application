package com.example.weathertask.service;

import com.example.weathertask.model.City;

public interface CityService {

    City saveCity(String cityName);

    void deleteCityByCityName(String cityName);

    City findCityByName(String cityName);
}
