package com.cosorio.weather.controller;

import com.cosorio.weather.aspect.annotation.Monitor;
import com.cosorio.weather.business.service.WeatherService;
import com.cosorio.weather.business.service.domain.ReportWeather;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Monitor
@RestController
public class WeatherController {

    private WeatherService weatherService;
    private static final String PATH = "/weathers";
    private static final String PATH_BY_ID = "/weathers/{weatherId}";
    private static final String PATH_REPORT = "/weather/report";


    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value = PATH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<WeatherDomain> getAllWeathers(@RequestParam(required = false) String date) {
        if (date != null){
            return weatherService.getWeatherByDate(date);
        }
        return weatherService.getAllWeather();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value = PATH_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WeatherDomain getWeather(@PathVariable("weatherId") Long weatherId){
        return weatherService.getWeather(weatherId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            value = PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WeatherDomain createWeather(@RequestBody WeatherDomain weatherDomain) {
        return weatherService.createWeather(weatherDomain);
    }

    @PutMapping(
            value = PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WeatherDomain> updateWeather(@RequestBody WeatherDomain weatherDomain) {
        return ResponseEntity.ok(weatherService.updateWeather(weatherDomain));
    }

    @DeleteMapping(
            value = PATH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WeatherDomain> deleteWeather(){
        weatherService.deleteAllWeathers();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = PATH_BY_ID
    )
    public void deleteWeatherById(@PathVariable("weatherId") Long weatherId){
        weatherService.deleteWeatherById(weatherId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value = PATH_REPORT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReportWeather getWeatherReport(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        return weatherService.getWeatherReport(startDate, endDate);
    }


}
