package com.cosorio.weather.integration;

import com.cosorio.weather.controller.WeatherController;
import com.cosorio.weather.entity.Temperature;
import com.cosorio.weather.entity.Weather;
import com.cosorio.weather.repository.TemperatureRepository;
import com.cosorio.weather.repository.WeatherRepository;
import com.cosorio.weather.service.WeatherService;
import com.cosorio.weather.service.domain.WeatherDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @MockBean
    private WeatherRepository weatherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        List<WeatherDomain> weatherDomains = new ArrayList<>();
        WeatherDomain weatherDomain = new WeatherDomain();
        weatherDomain.setId(666L);
        weatherDomain.setDate(LocalDate.of(2021, 1 , 3));
        weatherDomains.add(weatherDomain);

        weatherRepository.findAll().forEach(e -> System.out.println(e.getId()));

        int i = 0;

        //when(weatherService.getAllWeather()).thenReturn(weatherDomains);

    }

    @Test
    public void whenGetAllWeather_thenReturns200() throws Exception {

        List<WeatherDomain> weatherDomains = new ArrayList<>();
        WeatherDomain weatherDomain = new WeatherDomain();
        weatherDomain.setId(666L);
        weatherDomain.setDate(LocalDate.of(2021, 1 , 3));
        weatherDomains.add(weatherDomain);

        when(weatherService.getAllWeather()).thenReturn(weatherDomains);

        MvcResult mvcResult = this.mockMvc.perform(get("/weathers"))
                                    .andDo(print())
                                    .andExpect(status().isOk())
                                    .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        //Assert.assertNotNull("El cuerpo de la respuesta del servicio no deberia ser nula", mockHttpServletResponse.getContentAsString());
        //Assert.assertEquals("application/json", mockHttpServletResponse.getContentType());
        //Assert.assertEquals("La lista deberia tener datos", "[{\"id\":666,\"date\":null,\"location\":null,\"temperature\":null}]", mvcResult.getResponse().getContentAsString());

    }

    @Test
    public void whenGetByIdWeather_thenReturns200() throws Exception {

        WeatherDomain weatherDomain = new WeatherDomain();
        weatherDomain.setId(666L);
        weatherDomain.setDate(LocalDate.of(2021, 1 , 3));

        when(weatherService.getWeather(anyLong())).thenReturn(weatherDomain);

        mockMvc.perform(get("/weathers/666"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(weatherDomain.getId()));

    }


    @Test
    public void whenValidWeather_thenReturns500() throws Exception {

        /*MockHttpServletResponse response = mockMvc.perform(get("/weathers/3")).andReturn().getResponse();*/


        mockMvc.perform(get("/weatherss")
                .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void whenValidWeather_thenOutOk() throws Exception {

        /*MockHttpServletResponse response = mockMvc.perform(get("/weathers/3")).andReturn().getResponse();*/


        MvcResult mvcResult = mockMvc.perform(get("/weathers")
                .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();

        String actual = mvcResult.getResponse().getContentAsString();

        System.out.println("hola");

    }

}
