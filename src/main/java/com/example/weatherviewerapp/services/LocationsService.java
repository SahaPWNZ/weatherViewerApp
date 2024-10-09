package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.LocationsModelDAO;
import com.example.weatherviewerapp.dto.WeatherCardDTO;
import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationsService {
    private final OpenWeatherService weatherService;
    private final LocationsModelDAO locationsDAO;

    public LocationsService(LocationsModelDAO locationsDAO) {
        this.weatherService = new OpenWeatherService();
        this.locationsDAO = locationsDAO;
    }

    public void addLocationToUser(User user, Location locationResponseDTO) {
        locationResponseDTO.setUser(user);
        locationsDAO.save(locationResponseDTO);
    }

    public WeatherCardDTO getWeatherForLocation(double lat, double lon, Long id) throws IOException {
        var weatherResponseDTO = weatherService.getWeatherForCoordinatesHttpMethod(lat, lon);

        return WeatherCardDTO.builder()
                .locationId(id)
                .temp(weatherResponseDTO.getMain().getTemp())
                .feelsLike(weatherResponseDTO.getMain().getFeelsLike())
                .description(weatherResponseDTO.getWeather().get(0).getDescription())
                .nameLocation(weatherResponseDTO.getName())
                .country(weatherResponseDTO.getSys().getCountry())
                .build();
    }

    public List<WeatherCardDTO> findAllWeatherCards(Long id) throws IOException {
        var ListLocations = locationsDAO.findAllByUserId(id);
        List<WeatherCardDTO> allWeatherCards = new ArrayList<>();
        for (Location location : ListLocations) {
            allWeatherCards.add(getWeatherForLocation(location.getLat(), location.getLon(), location.getId()));
        }
        return allWeatherCards;
    }
}
