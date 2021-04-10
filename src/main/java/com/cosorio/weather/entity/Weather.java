package com.cosorio.weather.entity;

import com.cosorio.weather.business.service.domain.Location;
import com.cosorio.weather.business.service.domain.WeatherDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private LocalDate date;
    private Float latitud;
    private Float longitud;
    @Column(unique = true)
    private String city;
    private String state;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "WEATHER_ID", referencedColumnName = "ID")
    private List<Temperature> temperatures;

    public Weather() {
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

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

    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", date=" + date +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", temperatures=" + temperatures +
                '}';
    }

    public WeatherDomain transformToWeather() {
        Location location = Location.builder().lat(getLatitud()).lon(getLongitud()).city(getCity()).state(getState()).build();

        Float[] temperature = new Float[4];
        for(int i = 0; i < getTemperatures().size() ; i++){
            Temperature temperature1 = getTemperatures().get(i);
            temperature[i] = temperature1.getValue();
        }

        return WeatherDomain.builder().id(getId()).date(getDate()).location(location).temperature(temperature).build();
    }
}
