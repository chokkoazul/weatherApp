package com.cosorio.weather.business.service;

import com.cosorio.weather.business.service.domain.ReportWeather;
import com.cosorio.weather.business.service.domain.WeatherDomain;

import java.util.List;

public interface WeatherService {

    List<WeatherDomain> getAllWeather();

    WeatherDomain getWeather(Long id);

    List<WeatherDomain> getWeatherByDate(String date);

    WeatherDomain createWeather(WeatherDomain weatherDomain);

    WeatherDomain updateWeather(WeatherDomain weatherDomain);

    void deleteAllWeathers();

    void deleteWeatherById(Long id);

    ReportWeather getWeatherReport(String startDate, String endDate);
}
