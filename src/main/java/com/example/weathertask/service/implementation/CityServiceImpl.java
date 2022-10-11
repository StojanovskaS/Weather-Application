package com.example.weathertask.service.implementation;

import com.example.weathertask.model.City;
import com.example.weathertask.model.exeptions.CityNotFoundException;
import com.example.weathertask.model.exeptions.InvalidParametarsException;
import com.example.weathertask.repository.CityRepository;
import com.example.weathertask.service.CityService;
import org.springframework.stereotype.Service;


@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City saveCity(String cityName) {
        if (cityName == null || cityName.isEmpty()){
            throw  new InvalidParametarsException();

        } else if (this.cityRepository.findById(cityName).isPresent()){
            return this.findCityByName(cityName);

        }else{
            return this.cityRepository.save(new City(cityName));
        }
    }

    @Override
    public void deleteCityByCityName(String cityName) {
        this.cityRepository.deleteById(cityName);
    }

    @Override
    public City findCityByName(String cityName) {
        return this.cityRepository.findById(cityName).orElseThrow(CityNotFoundException::new);
    }
}
