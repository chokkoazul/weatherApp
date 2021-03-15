package com.cosorio.weather.service;

import com.cosorio.weather.aspect.annotation.Monitor;
import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.repository.WeatherRepository;
import com.cosorio.weather.service.domain.Location;
import com.cosorio.weather.service.domain.ReportWeather;
import com.cosorio.weather.service.domain.WeatherDomain;
import com.cosorio.weather.repository.TemperatureRepository;
import com.cosorio.weather.service.domain.DataWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
        for(Float temp:weatherDomain.getTemperature()){
            Temperature temperature = new Temperature();
            temperature.setValue(temp);
            temperatures.add(temperatureRepository.save(temperature));
        }

        weather.setTemperatures(temperatures);
        weatherRepository.save(weather);
        weatherDomain.setId(weather.getId());
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
        for(Float temp:weatherDomain.getTemperature()){
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

    @Override
    public ReportWeather getWeatherReport(String startDate, String endDate) {

        validateDate(startDate, endDate);

        List<Weather> weathers = weatherRepository.findByDateLessThanEqualAndDateGreaterThanEqual(Date.valueOf(endDate), Date.valueOf(startDate));

        ReportWeather reportWeather = new ReportWeather();
        List<DataWeather> dataWeathers = new ArrayList<>();
        for (Weather weather: weathers){
            DataWeather dataWeather = new DataWeather();
            dataWeather.setCity(weather.getCity());
            dataWeather.setHighest(getHighest(weather.getTemperatures()));
            dataWeather.setLowest(getLowest(weather.getTemperatures()));
            dataWeathers.add(dataWeather);
        }
        reportWeather.setReport(dataWeathers);
        return reportWeather;
    }

    private Float getLowest(List<Temperature> temperatures) {
        Collections.sort(temperatures);
        return temperatures.get(0).getValue();
    }

    private Float getHighest(List<Temperature> temperatures) {
        Collections.reverse(temperatures);
        return temperatures.get(0).getValue();
    }

    private void validateDate(String startDate, String endDate) {
        if(startDate == null){

        }

        if(endDate == null) {

        }

    }
}
