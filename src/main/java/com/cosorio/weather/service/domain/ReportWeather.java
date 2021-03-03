package com.cosorio.weather.service.domain;

import java.util.List;

public class ReportWeather {

    private List<DataWeather> report;

    public List<DataWeather> getReport() {
        return report;
    }

    public void setReport(List<DataWeather> report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "ReportWeather{" +
                "report=" + report +
                '}';
    }
}
