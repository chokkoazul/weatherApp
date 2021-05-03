package com.cosorio.weather.repository.ro;

import com.cosorio.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface WeatherRepositoryRo extends JpaRepository<Weather, Long> {

    List<Weather> findByDate(LocalDate date);

    List<Weather> findByDateLessThanEqual(Date date);

    List<Weather> findByDateGreaterThanEqual(Date date);

    List<Weather> findByDateLessThanEqualAndDateGreaterThanEqual(Date date1, Date date2);

    List<Weather> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
