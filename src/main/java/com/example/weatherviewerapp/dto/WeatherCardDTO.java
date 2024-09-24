package com.example.weatherviewerapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherCardDTO {
    private double temp;
    private double feelsLike;
    private String description;
    private String nameLocation;
    private String country;
}
