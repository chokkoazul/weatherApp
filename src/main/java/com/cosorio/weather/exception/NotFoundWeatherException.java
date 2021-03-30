package com.cosorio.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundWeatherException extends RuntimeException {

    public NotFoundWeatherException(String message) {
        super(message);
    }


}
