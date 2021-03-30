package com.cosorio.weather.business.builder;

import com.cosorio.weather.business.service.domain.Location;
import com.cosorio.weather.business.service.domain.WeatherDomain;
import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;

public class WeatherBuilder {

    public static WeatherDomain build (Weather weather){
        Location location = createLocation(weather);

        Float[] temperature = new Float[4];
        for(int i = 0; i < weather.getTemperatures().size() ; i++){
            Temperature temperature1 = weather.getTemperatures().get(i);
            temperature[i] = temperature1.getValue();
        }

        return WeatherDomain.builder()
                .id(weather.getId())
                .date(weather.getDate().toLocalDate())
                .location(location)
                .temperature(temperature).build();
    }

    private static Location createLocation(Weather weather) {
        return Location.builder()
                .lat(weather.getLatitud())
                .lon(weather.getLongitud())
                .city(weather.getCity())
                .state(weather.getState())
                .build();
    }
}
