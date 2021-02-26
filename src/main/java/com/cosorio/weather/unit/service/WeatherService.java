package com.cosorio.weather.unit.service;

import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.unit.service.domain.WeatherDomain;

import java.util.List;

public interface WeatherService {

    List<WeatherDomain> getAllWeather();

    WeatherDomain getWeather(Long id);

    List<WeatherDomain> getWeatherByDate(String date) throws NotFoundWeatherException;

    WeatherDomain createWeather(WeatherDomain weatherDomain);

    WeatherDomain updateWeather(WeatherDomain weatherDomain);

    void deleteAllWeathers();

    void deleteWeatherById(Long id);
}
