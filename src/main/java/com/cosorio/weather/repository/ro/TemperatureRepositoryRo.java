package com.cosorio.weather.repository.ro;

import com.cosorio.weather.entity.Temperature;
import org.springframework.data.repository.CrudRepository;

public interface TemperatureRepositoryRo extends CrudRepository<Temperature, Long> {
}
