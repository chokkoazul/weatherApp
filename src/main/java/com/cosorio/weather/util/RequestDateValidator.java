package com.cosorio.weather.util;

import java.time.LocalDate;

public interface RequestDateValidator {

    void validate(LocalDate dateFrom, LocalDate dateTo);
}
