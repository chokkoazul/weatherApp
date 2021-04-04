package com.cosorio.weather.advises;

import com.cosorio.weather.exception.DateInputNullException;
import com.cosorio.weather.exception.NotFoundWeatherException;
import com.cosorio.weather.exception.ResponseMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(NotFoundWeatherException.class)
    @ResponseBody
    public ResponseEntity<Object> handleNotFoundException(NotFoundWeatherException ex) {
        String weatherInformationNotFound = "Not found exception";
        ResponseMsg responseMsg = new ResponseMsg(new Date(), ex.getLocalizedMessage(), weatherInformationNotFound);

        return new ResponseEntity<>(responseMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateInputNullException.class)
    @ResponseBody
    public ResponseEntity<Object> handleDateInputNullException(DateInputNullException ex) {
        String weatherInformationNotFound = "Dates null";
        ResponseMsg responseMsg = new ResponseMsg(new Date(), ex.getLocalizedMessage(), weatherInformationNotFound);

        return new ResponseEntity<>(responseMsg, HttpStatus.BAD_REQUEST);
    }

}
