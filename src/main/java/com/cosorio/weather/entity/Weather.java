package com.cosorio.weather.entity;

import com.cosorio.weather.service.domain.Location;
import com.cosorio.weather.service.domain.WeatherDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private Date date;
    private Float latitud;
    private Float longitud;
    @Column(unique = true)
    private String city;
    private String state;

    @OneToMany
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
        WeatherDomain weatherDomain = new WeatherDomain();

        weatherDomain.setId(getId());
        weatherDomain.setDate(getDate().toLocalDate());
        Location location = new Location();
        location.setLat(getLatitud());
        location.setLon(getLongitud());
        location.setCity(getCity());
        location.setState(getState());

        weatherDomain.setLocation(location);

        Float[] temperature = new Float[4];
        for(int i = 0; i < getTemperatures().size() ; i++){
            Temperature temperature1 = getTemperatures().get(i);
            temperature[i] = temperature1.getValue();
        }

        weatherDomain.setTemperature(temperature);

        return weatherDomain;
    }
}
