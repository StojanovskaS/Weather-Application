package com.example.weathertask.repository;

import com.example.weathertask.model.City;
import com.example.weathertask.model.DailyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyDataRepository extends JpaRepository<DailyData,Long> {
    List<DailyData> findAllByCity(City city);
    void deleteByDateAndCity(String date, City city);
    DailyData findByDateAndAndCity(String date, City city);
    List<DailyData> findAllByWeather(String weather);
}
