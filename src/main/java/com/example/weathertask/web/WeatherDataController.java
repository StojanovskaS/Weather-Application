package com.example.weathertask.web;

import com.example.weathertask.model.DailyData;
import com.example.weathertask.service.DailyDataService;
import com.example.weathertask.service.RawDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping({"/", "/home"})
public class WeatherDataController {

    private final RawDataService rawDataService;
    private final DailyDataService dailyDataService;
    public static String appId = "303b61375cf8faab46d19758068568bd";

    public WeatherDataController(RawDataService rawDataService, DailyDataService dailyDataService) {
        this.rawDataService = rawDataService;
        this.dailyDataService = dailyDataService;
    }


    @GetMapping()
    public ModelAndView getHomePage() {
        getData();
        List<DailyData> allDailyData = this.dailyDataService.listAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dailyDataList", allDailyData);
        modelAndView.addObject("bodyContent", "homepage");
        modelAndView.setViewName("main-templete.html");
        return modelAndView;
    }

    @GetMapping(value = "/warmdays")
    public ModelAndView getWarmDaysDataPage() {
        List<DailyData> dailyDataWithWarmTemp = this.dailyDataService.listMaxTempOver25DegreesForNext16Days();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dailyDataWithWarmTemp", dailyDataWithWarmTemp);
        modelAndView.addObject("bodyContent", "warmdaysdatapage");
        modelAndView.setViewName("main-templete.html");
        return modelAndView;
    }

    @GetMapping(value = "/colddays")
    public ModelAndView getColdDaysDataPage() {
        List<DailyData> dailyDataWithColdTemp = this.dailyDataService.listMinTempBellow10DegreesForNext16Days();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dailyDataWithColdTemp", dailyDataWithColdTemp);
        modelAndView.addObject("bodyContent", "colddaysdatapage");
        modelAndView.setViewName("main-templete.html");
        return modelAndView;
    }

    @GetMapping(value = "/rainydays")
    public ModelAndView getRainyDaysDataPage() {
        List<DailyData> rainyDaysData = this.dailyDataService.listRainyDays();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("rainyDaysData", rainyDaysData);
        modelAndView.addObject("bodyContent", "rainydaysdata");
        modelAndView.setViewName("main-templete.html");
        return modelAndView;
    }

    private void getData() {
        ArrayList<String> cityList = new ArrayList<>(Arrays.asList("Skopje,mk", "Bitola,mk", "Pehchevo,mk"));

        for (String city :
                cityList) {
            String uri = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&cnt=16&units=metric&APPID=" + appId;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String data = response.getBody();
                rawDataService.parseResponse(data);
            }

        }
    }

}
