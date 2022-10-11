package com.example.weathertask;

import com.example.weathertask.model.City;
import com.example.weathertask.model.DailyData;
import com.example.weathertask.model.exeptions.InvalidParametarsException;
import com.example.weathertask.repository.DailyDataRepository;
import com.example.weathertask.service.CityService;
import com.example.weathertask.service.DailyDataService;
import com.example.weathertask.service.implementation.DailyDataServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DailyDataServiceTest {
    @Mock
    private CityService cityService;

    @Mock
    DailyDataRepository dailyDataRepository;

    private DailyDataService dailyDataService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        City city = new City("Skopje");
        DailyData dailyData = new DailyData(city, "2022/10/10", 25.5, 24.4, 26.6, "Rain");
        Mockito.when(this.cityService.findCityByName(Mockito.anyString())).thenReturn(city);
        Mockito.when(this.dailyDataRepository.save(Mockito.any(DailyData.class))).thenReturn(dailyData);

        this.dailyDataService = Mockito.spy(new DailyDataServiceImpl(this.cityService, this.dailyDataRepository));
    }

    @Test
    public void testSuccessSaveMethod() {
        DailyData dailyData = this.dailyDataService.save("Skopje", "2022/10/10", 25.5, 24.4, 26.6, "Rain");

        Mockito.verify(this.dailyDataService).save("Skopje", "2022/10/10", 25.5, 24.4, 26.6, "Rain");
        Assert.assertEquals("Skopje", dailyData.getCity().getCityName());
        Assert.assertEquals("2022/10/10", dailyData.getDate());


    }

    @Test
    public void testInvalidParametars() {
        Assert.assertThrows("InvalidParametarsException expected",
                InvalidParametarsException.class,
                () -> this.dailyDataService.save(null, "2022/10/10", 25.5, 24.4, 26.6, "Rain"));
        Mockito.verify(this.dailyDataService).save(null, "2022/10/10", 25.5, 24.4, 26.6, "Rain");
    }

}
