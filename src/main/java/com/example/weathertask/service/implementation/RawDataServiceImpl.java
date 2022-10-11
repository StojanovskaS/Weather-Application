package com.example.weathertask.service.implementation;

import com.example.weathertask.model.City;
import com.example.weathertask.service.CityService;
import com.example.weathertask.service.DailyDataService;
import com.example.weathertask.service.RawDataService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class RawDataServiceImpl implements RawDataService {

    private final CityService cityService;
    private final DailyDataService dailyDataService;

    public RawDataServiceImpl(CityService cityService, DailyDataService dailyDataService) {
        this.cityService = cityService;
        this.dailyDataService = dailyDataService;
    }

    @Override
    public void parseResponse(String data) {
        Object obj = JSONValue.parse(data);
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject cityInfo = (JSONObject) jsonObject.get("city");
        String cityName = (String) cityInfo.get("name");
        City city = this.cityService.saveCity(cityName);
        ArrayList<JSONObject> listWithDailyDataInfo = (ArrayList<JSONObject>) jsonObject.get("list");
        for (JSONObject element :
                listWithDailyDataInfo) {

            Long unParsedDate = (Long) element.get("dt");
            Date parsedDate = new Date((long) unParsedDate * 1000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String dateToSave = dateFormat.format(parsedDate);
            JSONObject temp = (JSONObject) element.get("temp");
            ArrayList<JSONObject> listWithDailyDataWeatherInfo = (ArrayList<JSONObject>) element.get("weather");

            double minTemperature = (Double) temp.get("min");
            Object maxTemp = temp.get("max");
            double maxTemperature = 0.0;
            if (maxTemp instanceof Double) {
                maxTemperature = (Double) temp.get("max");

            } else if (maxTemp instanceof Long) {
                Long maxTemp1 = (Long) temp.get("max");
                maxTemperature = Double.parseDouble(maxTemp1 + "");

            }
            double temperature = (Double) temp.get("day");
            String weatherMain = (String) listWithDailyDataWeatherInfo.get(0).get("main");
            if (!this.dailyDataService.dailyDataForTheDateAndCityExist(dateToSave, cityName)) {
                this.dailyDataService.save(cityName, dateToSave, temperature, minTemperature, maxTemperature, weatherMain);

            }

        }
    }
}
