package com.cosorio.weather.business.service;

import com.cosorio.weather.business.builder.WeatherBuilder;
import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import com.cosorio.weather.exception.DateInputNullException;
import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.repository.WeatherRepository;
import com.cosorio.weather.business.service.domain.Location;
import com.cosorio.weather.business.service.domain.ReportWeather;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import com.cosorio.weather.repository.TemperatureRepository;
import com.cosorio.weather.business.service.domain.DataWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<WeatherDomain> weatherDomains = weatherRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Weather::getId).reversed())
                .map(WeatherBuilder::build)
                .collect(Collectors.toList());

        throwDataNotFoundExceptionWhenEmptyList(weatherDomains);

        return weatherDomains;

    }

    @Override
    public WeatherDomain getWeather(Long id) {
        return weatherRepository
                .findById(id)
                .map(WeatherBuilder::build)
                .orElseThrow(() -> new NotFoundWeatherException("No existe weather con ese Id"));
    }

    @Override
    public List<WeatherDomain> getWeatherByDate(String date) {

        List<WeatherDomain> weatherDomains = weatherRepository
                .findByDate(Date.valueOf(date))
                .stream()
                .sorted(Comparator.comparing(Weather::getId).reversed())
                .map(WeatherBuilder::build)
                .collect(Collectors.toList());

        throwDataNotFoundExceptionWhenEmptyList(weatherDomains);
        return weatherDomains;
    }

    private void throwDataNotFoundExceptionWhenEmptyList(List<WeatherDomain> weatherList) {

        if (weatherList.isEmpty()) {
            throw new NotFoundWeatherException("List is empty!!!");
        }

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

        List<DataWeather> dataWeathers = new ArrayList<>();
        for (Weather weather: weathers){
            dataWeathers.add(DataWeather.builder().city(weather.getCity()).highest(getHighest(weather.getTemperatures())).lowest(getLowest(weather.getTemperatures())).build());
        }
        return ReportWeather.builder().report(dataWeathers).build();
    }

    private Float getLowest(List<Temperature> temperatures) {
        return temperatures.stream()
                .sorted(Comparator.comparing(Temperature::getValue))
                .map(Temperature::getValue)
                .collect(Collectors.toList())
                .get(0);
    }

    private Float getHighest(List<Temperature> temperatures) {
        return temperatures.stream()
                .sorted(Comparator.comparing(Temperature::getValue).reversed())
                .map(Temperature::getValue)
                .collect(Collectors.toList())
                .get(0);
    }

    private void validateDate(String startDate, String endDate) {
        if(startDate == null && endDate == null){
            throw new DateInputNullException("Both dates can not be null!!!");
        }
    }
}
