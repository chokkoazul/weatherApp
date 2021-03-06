package com.cosorio.weather.util.implementation;

import com.cosorio.weather.aspect.annotation.Monitor;
import com.cosorio.weather.exception.InvalidDateRequestException;
import com.cosorio.weather.util.RequestDateValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@Monitor
public class RangeDateValidator implements RequestDateValidator {
    @Override
    public void validate(LocalDate dateFrom, LocalDate dateTo) {
        if (Period.between(dateFrom, dateTo).isNegative()) {
            throw new InvalidDateRequestException("Invalid date request ");
        }
    }
}
