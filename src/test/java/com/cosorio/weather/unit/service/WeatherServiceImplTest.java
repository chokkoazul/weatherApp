package com.cosorio.weather.unit.service;


import com.cosorio.weather.business.service.WeatherServiceImpl;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import com.cosorio.weather.repository.ro.WeatherRepositoryRo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceImplTest {

    @Mock
    private WeatherRepositoryRo weatherRepositoryRo;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Before
    public void setUp() {
        // STUB FOR getAllWeatherResponseOk
        List<Weather> weathers = Collections.singletonList(getWeather(1L, LocalDate.of(2021,2,1),
                23.2345f,
                45.4567f,
                "Santiago",
                "Chile",
                Arrays.asList(new Temperature(1L, 89.3f),
                        new Temperature(2L, 45.7f))));
        when(weatherRepositoryRo.findAll()).thenReturn(weathers);

        // STUB FOR getWeatherResponseOk
        Optional<Weather> optionalWeather = Optional.of(getWeather(1L, LocalDate.of(2021,2,1),
                23.2345f,
                45.4567f,
                "Santiago",
                "Chile",
                Arrays.asList(new Temperature(1L, 89.3f),
                        new Temperature(2L, 45.7f))));
        when(weatherRepositoryRo.findById(anyLong())).thenReturn(optionalWeather);
    }

    @Test
    public void getAllWeatherResponseOk() {

        List<WeatherDomain> weatherDomains = weatherService.getAllWeather();
        assertEquals("weatherDomains.size() should be 1",weatherDomains.size(), 1);
        assertEquals("object weather should be equals", weatherDomains.get(0).toString(),  "MeteorologicalDomain{id=1, date=2021-02-01, location=Location{lat=23.2345, lon=45.4567, city='Santiago', state='Chile'}, temperature=[89.3, 45.7, null, null]}");

    }

    @Test
    public void getWeatherResponseOk() {

        WeatherDomain weatherDomain = weatherService.getWeather(1L);
        assertNotNull("weatherDomain should not be null",weatherDomain);
        assertEquals("object weather should be equals", weatherDomain.toString(), "MeteorologicalDomain{id=1, date=2021-02-01, location=Location{lat=23.2345, lon=45.4567, city='Santiago', state='Chile'}, temperature=[89.3, 45.7, null, null]}");
    }


    private Weather getWeather(Long id, LocalDate date, Float latitud, Float longitud, String city, String state, List<Temperature> temperatures) {
        Weather weather = new Weather();
        weather.setId(id);
        weather.setDate(date);
        weather.setLatitud(latitud);
        weather.setLongitud(longitud);
        weather.setCity(city);
        weather.setState(state);
        weather.setTemperatures(temperatures);
        return weather;
    }
}