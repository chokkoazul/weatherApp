package com.cosorio.weather.controller;

import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.service.domain.ReportWeather;
import com.cosorio.weather.service.domain.WeatherDomain;
import com.cosorio.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weathers")
    public ResponseEntity<List<WeatherDomain>> getAllWeathers(@RequestParam(required = false) String date) {
        if (date != null){
            try {
                return ResponseEntity.ok(weatherService.getWeatherByDate(date));
            } catch (NotFoundWeatherException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(weatherService.getAllWeather());
    }

    @GetMapping("/weathers/{weatherId}")
    ResponseEntity<WeatherDomain> getWeather(@PathVariable("weatherId") Long weatherId){
        return ResponseEntity.ok(weatherService.getWeather(weatherId));
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
