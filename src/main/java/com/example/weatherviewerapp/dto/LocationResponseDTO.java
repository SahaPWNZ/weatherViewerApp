package com.example.weatherviewerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@JsonIgnoreProperties({"local_names", "state"})
public class LocationResponseDTO {
    private String name;
    private double lat;
    private double lon;
    private String country;
//    @JsonProperty("local_names")
//    private Map<String, String> localNames;
//    private String state;


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
