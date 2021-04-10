package com.cosorio.weather.repository;

import com.cosorio.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByDate(LocalDate date);

    List<Weather> findByDateLessThanEqual(Date date);

    List<Weather> findByDateGreaterThanEqual(Date date);

    List<Weather> findByDateLessThanEqualAndDateGreaterThanEqual(Date date1, Date date2);

}
