package com.cosorio.weather.business.service.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Location {

    private Float lat;
    private Float lon;
    private String city;
    private String state;

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
