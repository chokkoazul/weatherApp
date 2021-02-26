package com.cosorio.weather.repository;

import com.cosorio.weather.entity.Weather;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather, Long> {

    List<Weather> findByDate(Date date);

}