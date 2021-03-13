package com.cosorio.weather.aspect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LogMessageBuilder {
    private String className;
    private String method;
    private String start;
    private String durationMillis;
    private String exceptionMessage;
    private String stackTrace;
    private String description;

}
