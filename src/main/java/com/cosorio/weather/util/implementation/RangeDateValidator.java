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
    public void validate(String dateFrom, String dateTo) {
        if(dateFrom == null && dateTo == null){
            throw new InvalidDateRequestException("Date must not be null");
        }
        if (Period.between(LocalDate.parse(dateFrom), LocalDate.parse(dateTo)).isNegative()) {
            throw new InvalidDateRequestException("Invalid date request ");
        }
    }
}
