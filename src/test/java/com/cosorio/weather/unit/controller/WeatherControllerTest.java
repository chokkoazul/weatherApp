package com.cosorio.weather.unit.controller;

import com.cosorio.weather.controller.WeatherController;
import com.cosorio.weather.service.WeatherService;
import com.cosorio.weather.service.domain.WeatherDomain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;

    @Before
    public void setUp() {

    }

    @Test
    public void getAllWeathersResponseOk() {
        List<WeatherDomain> weatherDomains = new ArrayList<>();
        WeatherDomain weatherDomain = new WeatherDomain();
        weatherDomain.setId(666L);
        weatherDomain.setDate(LocalDate.of(2021, 1, 2));
        weatherDomains.add(weatherDomain);

        when(weatherService.getAllWeather()).thenReturn(weatherDomains);

        ResponseEntity<List<WeatherDomain>> listResponseEntity = weatherController.getAllWeathers(null);


        assertNotNull("weatherDomains.size() should be 1",listResponseEntity.getBody());
        assertEquals("listResponseEntity.getBody() should be equals to [MeteorologicalDomain{id=666, date=2021-01-02, location=null, temperature=null}]", listResponseEntity.getBody().toString(), "[MeteorologicalDomain{id=666, date=2021-01-02, location=null, temperature=null}]");

    }

}
