package com.cosorio.weather.service.domain;

public class DataWeather {

    private String city;
    private Float lowest;
    private Float highest;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getLowest() {
        return lowest;
    }

    public void setLowest(Float lowest) {
        this.lowest = lowest;
    }

    public Float getHighest() {
        return highest;
    }

    public void setHighest(Float highest) {
        this.highest = highest;
    }

    @Override
    public String toString() {
        return "dataWeather{" +
                "city='" + city + '\'' +
                ", lowest=" + lowest +
                ", highest=" + highest +
                '}';
    }
}
