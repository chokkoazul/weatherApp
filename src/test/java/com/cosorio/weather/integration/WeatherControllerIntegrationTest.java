package com.cosorio.weather.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {

    //Mock your clock bean
    @MockBean
    private Clock clock;

    //field that will contain the fixed clock
    private Clock fixedClock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetAllWeather_thenReturns200() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/weathers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        Assert.assertEquals("mockHttpServletResponse.getContentAsString() should be [{\"id\":6,\"date\":\"1799-12-18\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Arica\",\"state\":\"Chile\"},\"temperature\":[89.7,74.3,11.2,125.4]},{\"id\":5,\"date\":\"1799-12-15\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Antofagasta\",\"state\":\"Chile\"},\"temperature\":[89.7,74.3,11.2,125.4]},{\"id\":4,\"date\":\"1999-01-25\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"London\",\"state\":\"England\"},\"temperature\":[15.7,24.3,51.2,15.4]},{\"id\":3,\"date\":\"1980-01-28\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Santiago\",\"state\":\"Chile\"},\"temperature\":[74.7,24.3,91.2,45.4]},{\"id\":2,\"date\":\"1915-06-03\",\"location\":{\"lat\":45.2546,\"lon\":12.8089,\"city\":\"Los Angeles\",\"state\":\"Florida\"},\"temperature\":[89.7,54.3,11.2,95.4]},{\"id\":1,\"date\":\"1900-01-01\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Dallas\",\"state\":\"Texas\"},\"temperature\":[89.7,74.3,11.2,125.4]}]", mockHttpServletResponse.getContentAsString(), "[{\"id\":6,\"date\":\"1799-12-18\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Arica\",\"state\":\"Chile\"},\"temperature\":[89.7,74.3,11.2,125.4]},{\"id\":5,\"date\":\"1799-12-15\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Antofagasta\",\"state\":\"Chile\"},\"temperature\":[89.7,74.3,11.2,125.4]},{\"id\":4,\"date\":\"1999-01-25\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"London\",\"state\":\"England\"},\"temperature\":[15.7,24.3,51.2,15.4]},{\"id\":3,\"date\":\"1980-01-28\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Santiago\",\"state\":\"Chile\"},\"temperature\":[74.7,24.3,91.2,45.4]},{\"id\":2,\"date\":\"1915-06-03\",\"location\":{\"lat\":45.2546,\"lon\":12.8089,\"city\":\"Los Angeles\",\"state\":\"Florida\"},\"temperature\":[89.7,54.3,11.2,95.4]},{\"id\":1,\"date\":\"1900-01-01\",\"location\":{\"lat\":32.2345,\"lon\":96.797,\"city\":\"Dallas\",\"state\":\"Texas\"},\"temperature\":[89.7,74.3,11.2,125.4]}]");

    }

    @Test
    public void whenGetReportWeatherWithDateNull_thenReturnsOneWeather() throws Exception {

        fixedClock = Clock.fixed(LocalDate.of(1900, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        MvcResult mvcResult = this.mockMvc.perform(get("/weather/report"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        Assert.assertEquals("mockHttpServletResponse.getContentAsString() should be {\"report\":[{\"city\":\"Dallas\",\"lowest\":11.2,\"highest\":125.4}]}", mockHttpServletResponse.getContentAsString(), "{\"report\":[{\"city\":\"Dallas\",\"lowest\":11.2,\"highest\":125.4}]}");

    }

    @Test
    public void whenGetReportWeatherWithoutStartDate_thenReturnsTwoWeathers() throws Exception {

        fixedClock = Clock.fixed(LocalDate.of(1800, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        MvcResult mvcResult = this.mockMvc.perform(get("/weather/report?endDate=1800-01-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        Assert.assertEquals("mockHttpServletResponse.getContentAsString() should be {\"report\":[{\"city\":\"Antofagasta\",\"lowest\":11.2,\"highest\":125.4},{\"city\":\"Arica\",\"lowest\":11.2,\"highest\":125.4}]}", mockHttpServletResponse.getContentAsString(), "{\"report\":[{\"city\":\"Antofagasta\",\"lowest\":11.2,\"highest\":125.4},{\"city\":\"Arica\",\"lowest\":11.2,\"highest\":125.4}]}");

    }

    @Test
    public void whenGetReportWeatherWithoutEndDate_thenReturnsNotFound() throws Exception {

        fixedClock = Clock.fixed(LocalDate.of(1800, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        this.mockMvc.perform(get("/weather/report?startDate=1799-12-30"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void whenGetReportWeatherWithDateIncorrect_thenReturns400() throws Exception {

        this.mockMvc.perform(get("/weather/report?startDate=2021-03-01&endDate=2021-02-01"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

    }

    @Test
    public void whenGetReportWeather_thenReturns200() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/weather/report?startDate=1980-01-01&endDate=2000-01-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();

        Assert.assertEquals("mockHttpServletResponse.getContentAsString() should be {\"report\":[{\"city\":\"Santiago\",\"lowest\":24.3,\"highest\":91.2},{\"city\":\"London\",\"lowest\":15.4,\"highest\":51.2}]}", mockHttpServletResponse.getContentAsString(), "{\"report\":[{\"city\":\"Santiago\",\"lowest\":24.3,\"highest\":91.2},{\"city\":\"London\",\"lowest\":15.4,\"highest\":51.2}]}");

    }

    @Test
    public void whenGetReportWeatherNotFound_thenReturns404() throws Exception {

        this.mockMvc.perform(get("/weather/report?startDate=2021-01-01&endDate=2021-05-02"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
