package com.cosorio.weather.integration;

import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.service.WeatherService;
import com.cosorio.weather.service.domain.Location;
import com.cosorio.weather.service.domain.WeatherDomain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class WeatherServiceIntegrationTest {

    @Autowired
    WeatherService weatherService;

    @Test
    public void testGetAllWeather() {
        assertEquals("getAllWeather().size() should be 4", weatherService.getAllWeather().size(), 4);
    }

    @Test
    public void testGetWeather() {
        assertEquals("getWeather(1L).toString() should be MeteorologicalDomain{id=1, date=2021-01-15, location=Location{lat=32.2345, lon=96.797, city='Dallas', state='Texas'}, temperature=[89.7, 74.3, 11.2, 125.4]}", weatherService.getWeather(1L).toString(), "MeteorologicalDomain{id=1, date=2021-01-15, location=Location{lat=32.2345, lon=96.797, city='Dallas', state='Texas'}, temperature=[89.7, 74.3, 11.2, 125.4]}");
    }

    @Test
    public void testGetWeatherByDate() throws NotFoundWeatherException {
        assertEquals("getWeatherByDate(\"2021-01-15\").toString() should be [MeteorologicalDomain{id=1, date=2021-01-15, location=Location{lat=32.2345, lon=96.797, city='Dallas', state='Texas'}, temperature=[89.7, 74.3, 11.2, 125.4]}]", weatherService.getWeatherByDate("2021-01-15").toString(), "[MeteorologicalDomain{id=1, date=2021-01-15, location=Location{lat=32.2345, lon=96.797, city='Dallas', state='Texas'}, temperature=[89.7, 74.3, 11.2, 125.4]}]");
    }

    @Test(expected = NotFoundWeatherException.class)
    public void testGetWeatherByDateNotFound() throws NotFoundWeatherException {
        weatherService.getWeatherByDate("2021-01-10");
    }

    @Test
    public void testCreateWeather() {

        WeatherDomain weatherDomain = weatherService.createWeather(getWeatherDomainTest());
        assertNotNull("weatherDomain.getId() should not be null", weatherDomain.getId());
        assertEquals("weatherDomain.getId() should be 5", weatherDomain.getId(), Long.valueOf(5));
    }

    private WeatherDomain getWeatherDomainTest() {
        WeatherDomain weatherDomain = new WeatherDomain();
        weatherDomain.setDate(LocalDate.of(2021,8,1));
        Location location = new Location();
        location.setCity("Lima");
        location.setState("Chile");
        location.setLon(3.5F);
        location.setLat(23.5F);
        weatherDomain.setLocation(location);
        Float[] temperature = new Float[4];
        temperature[0] = 23.5F;
        temperature[1] = 24.5F;
        temperature[2] = 25.5F;
        temperature[3] = 26.5F;

        weatherDomain.setTemperature(temperature);
        return weatherDomain;
    }


}
