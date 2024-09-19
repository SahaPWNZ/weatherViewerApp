package com.example.weatherviewerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties({"local_names", "state"})
public class LocationResponseDTO {
    private String name;
    private double lat;
    private double lon;
//    @JsonProperty("local_names")
//    private Map<String, String> localNames;
    private String country;
//    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

//    public Map<String, String> getLocalNames() {
//        return localNames;
//    }
//
//    public void setLocalNames(Map<String, String> localNames) {
//        this.localNames = localNames;
//    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }

    @Override
    public String toString() {
        return "LocationResponseDTO{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
//                ", localNames=" + localNames +
                ", country='" + country + '\'' +
//                ", state='" + state + '\'' +
                '}';
    }

}
