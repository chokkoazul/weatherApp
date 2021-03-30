package com.cosorio.weather.business.service.domain;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;

@Data
@Builder
public class WeatherDomain {
    private Long id;
    private LocalDate date;
    private Location location;
    private Float[] temperature;

    @Override
    public String toString() {
        return "MeteorologicalDomain{" +
                "id=" + id +
                ", date=" + date +
                ", location=" + location +
                ", temperature=" + Arrays.toString(temperature) +
                '}';
    }

}
