package com.cosorio.weather.unit.service;


import com.cosorio.weather.repository.WeatherRepository;
import com.cosorio.weather.service.WeatherServiceImpl;
import com.cosorio.weather.service.domain.WeatherDomain;
import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
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
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Before
    public void setUp() {
        // STUB FOR getAllWeatherResponseOk
        Iterable<Weather> weathers = Collections.singletonList(getWeather(1L,
                23.2345f,
                45.4567f,
                "Santiago",
                "Chile",
                Arrays.asList(new Temperature(1L, 89.3f),
                        new Temperature(2L, 45.7f))));
        when(weatherRepository.findAll()).thenReturn(weathers);

        // STUB FOR getWeatherResponseOk
        Optional<Weather> optionalWeather = Optional.of(getWeather(1L,
                23.2345f,
                45.4567f,
                "Santiago",
                "Chile",
                Arrays.asList(new Temperature(1L, 89.3f),
                        new Temperature(2L, 45.7f))));
        when(weatherRepository.findById(anyLong())).thenReturn(optionalWeather);
    }

    @Test
    public void getAllWeatherResponseOk() {

        List<WeatherDomain> weatherDomains = weatherService.getAllWeather();
        assertEquals("weatherDomains.size() should be 1",weatherDomains.size(), 1);
        assertEquals("object weather should be equals", weatherDomains.get(0).toString(),  "MeteorologicalDomain{id=1, date=2021-03-06, location=Location{lat=23.2345, lon=45.4567, city='Santiago', state='Chile'}, temperature=[89.3, 45.7, null, null]}");

    }

    @Test
    public void getWeatherResponseOk() {

        WeatherDomain weatherDomain = weatherService.getWeather(1L);
        assertNotNull("weatherDomain should not be null",weatherDomain);
        assertEquals("object weather should be equals", weatherDomain.toString(), "MeteorologicalDomain{id=1, date=2021-03-06, location=Location{lat=23.2345, lon=45.4567, city='Santiago', state='Chile'}, temperature=[89.3, 45.7, null, null]}");
    }


    private Weather getWeather(Long id, Float latitud, Float longitud, String city, String state, List<Temperature> temperatures) {
        Weather weather = new Weather();
        weather.setId(id);
        long now = System.currentTimeMillis();
        Date sqlDate = new Date(now);
        weather.setDate(sqlDate);
        weather.setLatitud(latitud);
        weather.setLongitud(longitud);
        weather.setCity(city);
        weather.setState(state);
        weather.setTemperatures(temperatures);
        return weather;
    }
}