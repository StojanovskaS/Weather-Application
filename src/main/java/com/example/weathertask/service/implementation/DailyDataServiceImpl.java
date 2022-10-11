package com.example.weathertask.service.implementation;

import com.example.weathertask.model.City;
import com.example.weathertask.model.DailyData;
import com.example.weathertask.model.exeptions.DailyDataForCityDoNotExistException;
import com.example.weathertask.model.exeptions.InvalidParametarsException;
import com.example.weathertask.model.exeptions.ParserFailedException;
import com.example.weathertask.repository.DailyDataRepository;
import com.example.weathertask.service.CityService;
import com.example.weathertask.service.DailyDataService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DailyDataServiceImpl implements DailyDataService {

    private final CityService cityService;
    private final DailyDataRepository dailyDataRepository;

    public DailyDataServiceImpl(CityService cityService, DailyDataRepository dailyDataRepository) {
        this.cityService = cityService;
        this.dailyDataRepository = dailyDataRepository;
    }

    @Override
    public DailyData save(String cityName, String date, Double temperature, Double min_temperature, Double max_temperature, String weather) {
        City city = this.cityService.findCityByName(cityName);
        if (cityName == null || cityName.isEmpty() || temperature == null || min_temperature == null || max_temperature == null) {
            throw new InvalidParametarsException();
        } else {
            return this.dailyDataRepository.save(new DailyData(city, date, temperature, min_temperature, max_temperature, weather));
        }
    }

    @Override
    public void deleteDailyDataByDateAndCity(String dailyDataDate, String cityName) {
        City city = this.cityService.findCityByName(cityName);
        this.dailyDataRepository.deleteByDateAndCity(dailyDataDate, city);

    }

    @Override
    public List<DailyData> listAll() {
        return this.dailyDataRepository.findAll();
    }

    @Override
    public List<DailyData> findByCity(String cityName) {
        City city = this.cityService.findCityByName(cityName);
        List<DailyData> allDailyDataForCity = this.dailyDataRepository.findAllByCity(city);
        if (allDailyDataForCity.isEmpty()) {
            throw new DailyDataForCityDoNotExistException(cityName);
        } else return allDailyDataForCity;
    }

    @Override
    public List<DailyData> listMaxTempOver25DegreesForNext16Days() {
        ArrayList<DailyData> dailyDataForNext16Days = new ArrayList<>();
        Date currentDate = new Date();
        Date dateToReview = null;
        for (DailyData dailyData :
                this.dailyDataRepository.findAll()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                dateToReview = sdf.parse(dailyData.getDate());
            } catch (Exception ex) {
                throw new ParserFailedException();
            }
            if (dateToReview.after(currentDate) || dateToReview.equals(currentDate)) {
                if (dailyData.getMax_temperature() >= 25.0) {
                    dailyDataForNext16Days.add(dailyData);
                }
            }

        }
        return dailyDataForNext16Days;

    }

    @Override
    public List<DailyData> listMinTempBellow10DegreesForNext16Days() {
        ArrayList<DailyData> dailyDataForNext16Days = new ArrayList<>();
        Date currentDate = new Date();
        Date dateToReview = null;
        for (DailyData dailyData :
                this.dailyDataRepository.findAll()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                dateToReview = sdf.parse(dailyData.getDate());
            } catch (Exception ex) {
                throw new ParserFailedException();
            }
            if (dateToReview.after(currentDate) || dateToReview.equals(currentDate)) {
                if (dailyData.getMin_temperature() <= 10.0) {
                    dailyDataForNext16Days.add(dailyData);
                }
            }

        }
        return dailyDataForNext16Days;
    }

    @Override
    public List<DailyData> listRainyDays() {
        return this.dailyDataRepository.findAllByWeather("Rain");
    }

    @Override
    public boolean dailyDataForTheDateAndCityExist(String date, String cityName) {
        City city = this.cityService.findCityByName(cityName);
        if (this.dailyDataRepository.findByDateAndAndCity(date, city) != null) {
            return true;
        }
        return false;
    }
}
