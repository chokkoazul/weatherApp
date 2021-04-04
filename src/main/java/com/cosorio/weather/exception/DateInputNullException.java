package com.cosorio.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DateInputNullException extends RuntimeException{

    public DateInputNullException(String message) {
        super(message);
    }
}
