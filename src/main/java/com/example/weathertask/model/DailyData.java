package com.example.weathertask.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dailydata")
public class DailyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String date;
    Double temperature;
    Double min_temperature;
    Double max_temperature;
    String weather;
    @ManyToOne
    City city;

    public DailyData() { }

    public DailyData( City city,String date, Double temperature, Double min_temperature, Double max_temperature, String weather) {
        this.date = date;
        this.temperature = temperature;
        this.min_temperature = min_temperature;
        this.max_temperature = max_temperature;
        this.weather = weather;
        this.city = city;
    }
}
