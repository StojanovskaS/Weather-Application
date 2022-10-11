package com.example.weathertask.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "city")
public class City {

    @Id
    String CityName;
    @OneToMany(mappedBy = "city",fetch = FetchType.EAGER)
    List<DailyData> dailyData;

    public City() { }

    public City(String cityName ) {
        CityName = cityName;
    }
}
