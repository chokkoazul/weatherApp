package com.cosorio.weather.business.service;

import com.cosorio.weather.business.builder.WeatherBuilder;
import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.business.service.domain.Location;
import com.cosorio.weather.business.service.domain.ReportWeather;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import com.cosorio.weather.business.service.domain.DataWeather;
import com.cosorio.weather.repository.ro.TemperatureRepositoryRo;
import com.cosorio.weather.repository.ro.WeatherRepositoryRo;
import com.cosorio.weather.repository.rw.TemperatureRepositoryRw;
import com.cosorio.weather.repository.rw.WeatherRepositoryRw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepositoryRo weatherRepositoryRo;
    private WeatherRepositoryRw weatherRepositoryRw;

    private TemperatureRepositoryRo temperatureRepositoryRo;
    private TemperatureRepositoryRw temperatureRepositoryRw;

    @Autowired
    public WeatherServiceImpl(WeatherRepositoryRo weatherRepositoryRo, WeatherRepositoryRw weatherRepositoryRw, TemperatureRepositoryRo temperatureRepositoryRo, TemperatureRepositoryRw temperatureRepositoryRw) {
        this.weatherRepositoryRo = weatherRepositoryRo;
        this.weatherRepositoryRw = weatherRepositoryRw;
        this.temperatureRepositoryRo = temperatureRepositoryRo;
        this.temperatureRepositoryRw = temperatureRepositoryRw;
    }


    @Override
    public List<WeatherDomain> getAllWeather() {
        List<WeatherDomain> weatherDomains = weatherRepositoryRo.findAll()
                .stream()
                .sorted(Comparator.comparing(Weather::getId).reversed())
                .map(WeatherBuilder::build)
                .collect(Collectors.toList());

        throwDataNotFoundExceptionWhenEmptyList(weatherDomains);

        return weatherDomains;

    }

    @Override
    public WeatherDomain getWeather(Long id) {
        return weatherRepositoryRo
                .findById(id)
                .map(WeatherBuilder::build)
                .orElseThrow(() -> new NotFoundWeatherException("No existe weather con ese Id"));
    }

    @Override
    public List<WeatherDomain> getWeatherByDate(LocalDate date) {

        List<WeatherDomain> weatherDomains = weatherRepositoryRo
                .findByDate(date)
                .stream()
                .sorted(Comparator.comparing(Weather::getId).reversed())
                .map(WeatherBuilder::build)
                .collect(Collectors.toList());

        throwDataNotFoundExceptionWhenEmptyList(weatherDomains);
        return weatherDomains;
    }

    private void throwDataNotFoundExceptionWhenEmptyList(List<?> weatherList) {

        if (weatherList.isEmpty()) {
            throw new NotFoundWeatherException("List is empty!!!");
        }

    }

    @Override
    public WeatherDomain createWeather(WeatherDomain weatherDomain) {

        Weather weather = new Weather();
        weather.setDate(weatherDomain.getDate());
        Location location = weatherDomain.getLocation();
        weather.setLatitud(location.getLat());
        weather.setLongitud(location.getLon());
        weather.setCity(location.getCity());
        weather.setState(location.getState());
        List<Temperature> temperatures = new ArrayList<>();
        for(Float temp:weatherDomain.getTemperature()){
            Temperature temperature = new Temperature();
            temperature.setValue(temp);
            temperatures.add(temperatureRepositoryRw.save(temperature));
        }

        weather.setTemperatures(temperatures);
        weatherRepositoryRw.save(weather);
        weatherDomain.setId(weather.getId());
        return weatherDomain;
    }

    @Override
    public WeatherDomain updateWeather(WeatherDomain weatherDomain) {
        Weather weather = new Weather();
        weather.setId(weatherDomain.getId());
        weather.setDate(weatherDomain.getDate());
        Location location = weatherDomain.getLocation();
        weather.setLatitud(location.getLat());
        weather.setLongitud(location.getLon());
        weather.setCity(location.getCity());
        weather.setState(location.getState());
        List<Temperature> temperatures = new ArrayList<>();
        for(Float temp:weatherDomain.getTemperature()){
            Temperature temperature = new Temperature();
            temperature.setValue(temp);
            temperatures.add(temperatureRepositoryRw.save(temperature));
        }
        weather.setTemperatures(temperatures);
        return weatherRepositoryRw.save(weather).transformToWeather();
    }

    @Override
    public void deleteAllWeathers() {
        weatherRepositoryRw.deleteAll();
    }

    @Override
    public void deleteWeatherById(Long id) {
        weatherRepositoryRw.deleteById(id);
    }

    @Override
    public ReportWeather getWeatherReport(LocalDate startDate, LocalDate endDate) {

        List<Weather> weathers = weatherRepositoryRo.findByDateBetween(startDate, endDate);

        throwDataNotFoundExceptionWhenEmptyList(weathers);

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

}
