package com.example.weathertask.service;

import com.example.weathertask.model.DailyData;

import java.util.List;

public interface DailyDataService {
    DailyData save(String cityName, String date, Double temperature, Double min_temperature, Double max_temperature, String weather);

    void deleteDailyDataByDateAndCity(String dailyDataDate, String cityName);

    List<DailyData> listAll();

    List<DailyData> findByCity(String cityName);

    List<DailyData> listMaxTempOver25DegreesForNext16Days();

    List<DailyData> listMinTempBellow10DegreesForNext16Days();

    List<DailyData> listRainyDays();

    boolean dailyDataForTheDateAndCityExist(String date, String cityName);


}
