package com.cosorio.weather.controller;

import com.cosorio.weather.aspect.annotation.Monitor;
import com.cosorio.weather.business.service.WeatherService;
import com.cosorio.weather.business.service.domain.ReportWeather;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import org.springframework.http.HttpStatus;
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

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/weathers")
    public List<WeatherDomain> getAllWeathers(@RequestParam(required = false) String date) {
        if (date != null){
            return weatherService.getWeatherByDate(date);
        }
        return weatherService.getAllWeather();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/weathers/{weatherId}")
    public WeatherDomain getWeather(@PathVariable("weatherId") Long weatherId){
        return weatherService.getWeather(weatherId);
    }

    @PostMapping(value = "/weathers", produces = "application/json;charset=UTF-8")
    ResponseEntity<WeatherDomain> createWeather(@RequestBody WeatherDomain weatherDomain) {
        return ResponseEntity.ok(weatherService.createWeather(weatherDomain));
    }

    @PutMapping(value = "/weathers", produces = "application/json;charset=UTF-8")
    ResponseEntity<WeatherDomain> updateWeather(@RequestBody WeatherDomain weatherDomain) {
        return ResponseEntity.ok(weatherService.updateWeather(weatherDomain));
    }

    @DeleteMapping(value = "/weathers", produces = "application/json;charset=UTF-8")
    ResponseEntity<WeatherDomain> deleteWeather(){
        weatherService.deleteAllWeathers();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/weathers/{weatherId}", produces = "application/json;charset=UTF-8")
    ResponseEntity<WeatherDomain> deleteWeatherById(@PathVariable("weatherId") Long weatherId){
        weatherService.deleteWeatherById(weatherId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/weather/report")
    ResponseEntity<ReportWeather> getWeatherReport(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(weatherService.getWeatherReport(startDate, endDate));
    }


}
