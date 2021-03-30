package com.cosorio.weather.integration;

import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import com.cosorio.weather.repository.WeatherRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherRepository weatherRepository;

    @Test
    public void whenGetAllWeather_thenReturns200() throws Exception {

        Temperature temperature = new Temperature();
        temperature.setId(1L);
        temperature.setValue(23.4F);

        List<Weather> iterable = Collections.singletonList(getWeather(666L, Date.valueOf("2021-01-02"), "Santiago", "Chile", 12.4F, 34.4F, temperature));

        when(weatherRepository.findAll()).thenReturn(iterable);

        MvcResult mvcResult = this.mockMvc.perform(get("/weathers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        Assert.assertEquals("mockHttpServletResponse.getContentAsString() should be [{\"id\":666,\"date\":\"2021-01-02\",\"location\":{\"lat\":34.4,\"lon\":12.4,\"city\":\"Santiago\",\"state\":\"Chile\"},\"temperature\":[23.4,null,null,null]}]", mockHttpServletResponse.getContentAsString(), "[{\"id\":666,\"date\":\"2021-01-02\",\"location\":{\"lat\":34.4,\"lon\":12.4,\"city\":\"Santiago\",\"state\":\"Chile\"},\"temperature\":[23.4,null,null,null]}]");

    }

    private Weather getWeather(long id, Date date, String city, String state, float lon, float lat, Temperature temperature) {
        Weather weather = new Weather();
        weather.setId(id);
        weather.setDate(date);
        weather.setCity(city);
        weather.setState(state);
        weather.setLongitud(lon);
        weather.setLatitud(lat);
        weather.setTemperatures(Collections.singletonList(temperature));
        return weather;

    }


}
