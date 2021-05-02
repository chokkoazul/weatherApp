package com.cosorio.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class Now {

    @Autowired
    private Clock clock;

    public LocalDate getNow(){
        return LocalDate.now(clock);
    }
}
