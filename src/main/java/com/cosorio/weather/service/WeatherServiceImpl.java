package com.cosorio.weather.service;

import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.repository.WeatherRepository;
import com.cosorio.weather.service.domain.Location;
import com.cosorio.weather.service.domain.WeatherDomain;
import com.cosorio.weather.repository.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository weatherRepository;
    private TemperatureRepository temperatureRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository, TemperatureRepository temperatureRepository) {
        this.weatherRepository = weatherRepository;
        this.temperatureRepository = temperatureRepository;
    }

    @Override
    public List<WeatherDomain> getAllWeather() {
        List<WeatherDomain> weatherDomains = new ArrayList<>();
        for(Weather weather : weatherRepository.findAll()){
            weatherDomains.add(weather.transformToWeather());
        }
        weatherDomains.sort(Collections.reverseOrder());
        return weatherDomains;

    }

    @Override
    public WeatherDomain getWeather(Long id) {
        Weather weather = weatherRepository.findById(id).get();
        return weather.transformToWeather();
    }

    @Override
    public List<WeatherDomain> getWeatherByDate(String date) throws NotFoundWeatherException {

        List<WeatherDomain> weatherDomains = new ArrayList<>();
        List<Weather> weatherList = weatherRepository.findByDate(Date.valueOf(date));

        if (weatherList.isEmpty()) throw new NotFoundWeatherException();

        for(Weather weather : weatherList){
            weatherDomains.add(weather.transformToWeather());
        }
        weatherDomains.sort(Collections.reverseOrder());
        return weatherDomains;
    }

    @Override
    public WeatherDomain createWeather(WeatherDomain weatherDomain) {

        Weather weather = new Weather();
        weather.setDate(java.sql.Date.valueOf(weatherDomain.getDate()));
        Location location = weatherDomain.getLocation();
        weather.setLatitud(location.getLat());
        weather.setLongitud(location.getLon());
        weather.setCity(location.getCity());
        weather.setState(location.getState());
        List<Temperature> temperatures = new ArrayList<>();
        for(int i=0;i<weatherDomain.getTemperature().length;i++){
            Float temp = weatherDomain.getTemperature()[i];
            Temperature temperature = new Temperature();
            temperature.setValue(temp);
            temperatures.add(temperatureRepository.save(temperature));
        }
        weather.setTemperatures(temperatures);
        weatherRepository.save(weather);
        return weatherDomain;
    }

    @Override
    public WeatherDomain updateWeather(WeatherDomain weatherDomain) {
        Weather weather = new Weather();
        weather.setId(weatherDomain.getId());
        weather.setDate(java.sql.Date.valueOf(weatherDomain.getDate()));
        Location location = weatherDomain.getLocation();
        weather.setLatitud(location.getLat());
        weather.setLongitud(location.getLon());
        weather.setCity(location.getCity());
        weather.setState(location.getState());
        List<Temperature> temperatures = new ArrayList<>();
        for(int i=0;i<weatherDomain.getTemperature().length;i++){
            Float temp = weatherDomain.getTemperature()[i];
            Temperature temperature = new Temperature();
            temperature.setValue(temp);
            temperatures.add(temperatureRepository.save(temperature));
        }
        weather.setTemperatures(temperatures);
        return weatherRepository.save(weather).transformToWeather();
    }

    @Override
    public void deleteAllWeathers() {
        weatherRepository.deleteAll();
    }

    @Override
    public void deleteWeatherById(Long id) {
        weatherRepository.deleteById(id);
    }
}
