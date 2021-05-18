package com.cosorio.weather.repository.rw;

import com.cosorio.weather.entity.Temperature;
import org.springframework.data.repository.CrudRepository;

public interface TemperatureRepositoryRw extends CrudRepository<Temperature, Long> {
}
