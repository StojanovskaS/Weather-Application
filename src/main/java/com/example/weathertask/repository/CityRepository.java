package com.example.weathertask.repository;

import com.example.weathertask.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,String> {
}
