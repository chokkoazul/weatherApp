package com.cosorio.weather.unit.controller;

import com.cosorio.weather.controller.WeatherController;
import com.cosorio.weather.business.service.WeatherService;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
        weatherDomains.add(WeatherDomain.builder().id(666L).date(LocalDate.of(2021, 1, 2)).build());

        when(weatherService.getAllWeather()).thenReturn(weatherDomains);

        List<WeatherDomain> listResponseEntity = weatherController.getAllWeathers(null);


        assertNotNull("weatherDomains.size() should be 1",listResponseEntity);
        assertEquals("listResponseEntity.getBody() should be equals to [MeteorologicalDomain{id=666, date=2021-01-02, location=null, temperature=null}]", listResponseEntity.toString(), "[MeteorologicalDomain{id=666, date=2021-01-02, location=null, temperature=null}]");

    }

    @Test
    public void getAllWeathersByDateResponseOk() {
        when(weatherService.getWeatherByDate(anyString())).thenReturn(Arrays.asList(WeatherDomain.builder().id(666L).date(LocalDate.of(2021, 1, 2)).build()));

        List<WeatherDomain> listResponseEntity = weatherController.getAllWeathers("2021-1-2");

        assertNotNull("weatherDomains.size() not should be null",listResponseEntity);
        assertEquals("listResponseEntity.getBody() should be equals to [MeteorologicalDomain{id=666, date=2021-01-02, location=null, temperature=null}]", listResponseEntity.toString(), "[MeteorologicalDomain{id=666, date=2021-01-02, location=null, temperature=null}]");

    }

    @Test
    public void getWeatherByIdResponseOk() {
        when(weatherService.getWeather(anyLong())).thenReturn(WeatherDomain.builder().id(666L).build());

        WeatherDomain responseEntity = weatherController.getWeather(1L);

        assertNotNull("responseEntity not should be null",responseEntity);

    }




}
