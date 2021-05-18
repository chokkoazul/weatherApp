package com.cosorio.weather.integration;

import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.business.service.WeatherService;
import com.cosorio.weather.business.service.domain.Location;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//@AutoConfigureTestDatabase
public class WeatherServiceIntegrationTest {

    @Autowired
    WeatherService weatherService;

    @Test
    public void testGetAllWeather() {
        assertEquals("getAllWeather().size() should be 6", weatherService.getAllWeather().size(), 6);
    }

    @Test
    public void testGetWeather() {
        assertEquals("getWeather(1L).toString() should be MeteorologicalDomain{id=1, date=1900-01-01, location=Location{lat=32.2345, lon=96.797, city='Dallas', state='Texas'}, temperature=[89.7, 74.3, 11.2, 125.4]}", weatherService.getWeather(1L).toString(), "MeteorologicalDomain{id=1, date=1900-01-01, location=Location{lat=32.2345, lon=96.797, city='Dallas', state='Texas'}, temperature=[89.7, 74.3, 11.2, 125.4]}");
    }

    @Test
    public void testGetWeatherByDate() throws NotFoundWeatherException {
        assertEquals("getWeatherByDate(\"1915-06-03\").toString() should be [MeteorologicalDomain{id=2, date=1915-06-03, location=Location{lat=45.2546, lon=12.8089, city='Los Angeles', state='Florida'}, temperature=[89.7, 54.3, 11.2, 95.4]}]", weatherService.getWeatherByDate(LocalDate.of(1915, 6, 3)).toString(), "[MeteorologicalDomain{id=2, date=1915-06-03, location=Location{lat=45.2546, lon=12.8089, city='Los Angeles', state='Florida'}, temperature=[89.7, 54.3, 11.2, 95.4]}]");
    }

    @Test(expected = NotFoundWeatherException.class)
    public void testGetWeatherByDateNotFound() throws NotFoundWeatherException {
        weatherService.getWeatherByDate(LocalDate.of(2021, 1, 10));
    }

    @Test
    public void testCreateWeather() {

        WeatherDomain weatherDomain = weatherService.createWeather(getWeatherDomainTest());
        assertNotNull("weatherDomain.getId() should not be null", weatherDomain.getId());
        assertEquals("weatherDomain.getId() should be 7", weatherDomain.getId(), Long.valueOf(7));
    }

    private WeatherDomain getWeatherDomainTest() {
        Location location = Location.builder().city("Lima").state("Chile").lon(3.5F).lat(23.5F).build();

        Float[] temperature = new Float[4];
        temperature[0] = 23.5F;
        temperature[1] = 24.5F;
        temperature[2] = 25.5F;
        temperature[3] = 26.5F;

        return WeatherDomain.builder().date(LocalDate.of(2021,8,1)).location(location).temperature(temperature).build();
    }


}
