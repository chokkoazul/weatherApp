package com.cosorio.weather.repository;

import com.cosorio.weather.entity.Temperature;
import org.springframework.data.repository.CrudRepository;

public interface TemperatureRepository extends CrudRepository<Temperature, Long> {
}
