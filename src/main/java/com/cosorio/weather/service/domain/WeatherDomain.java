package com.cosorio.weather.unit.controller.service.domain;


import java.time.LocalDate;
import java.util.Arrays;

public class WeatherDomain implements Comparable<WeatherDomain> {
    private Long id;
    private LocalDate date;
    private Location location;
    private Float[] temperature;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Float[] getTemperature() {
        return temperature;
    }

    public void setTemperature(Float[] temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "MeteorologicalDomain{" +
                "id=" + id +
                ", date=" + date +
                ", location=" + location +
                ", temperature=" + Arrays.toString(temperature) +
                '}';
    }

    @Override
    public int compareTo(WeatherDomain o) {
        return getId().compareTo(o.getId());
    }

}
