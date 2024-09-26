package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.LocationsDAO;
import com.example.weatherviewerapp.dto.WeatherCardDTO;
import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.dto.api.WeatherResponseDTO;
import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.utils.ConfigUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OpenWeatherService {
    private final String BASE_URL = "https://api.openweathermap.org/";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LocationsDAO locationsDAO = new LocationsDAO();

    public List<LocationResponseDTO> getLocationsHttpMethod(String city) {
        if (city.contains(" ")) {
            city = city.replaceAll(" ", "%20");
        }
        try {
            String GEO_URL = "geo/1.0/direct?";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL
                            + GEO_URL
                            + "q=" + city
                            + "&limit=5"
                            + "&appid=" + ConfigUtil.getApiKey()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Status code of response: " + response.statusCode());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WeatherResponseDTO getWeatherForCoordinatesHttpMethod(double lat, double lon){
        String DATA_URL = "data/2.5/weather?";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(
                            BASE_URL
                                    + DATA_URL
                                    + "lat=" + lat
                                    + "&lon=" + lon
                                    + "&units=metric"
                                    + "&appid=" + ConfigUtil.getApiKey()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Status code of response: " + response.statusCode());
            return objectMapper.readValue(response.body(), WeatherResponseDTO.class);
        }
        catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLocationToUser(User user, Location locationResponseDTO) {
        locationResponseDTO.setUser(user);
        locationsDAO.save(locationResponseDTO);
    }

    public WeatherCardDTO getWeatherForLocation(double lat, double lon, Long id) {
        var weatherResponseDTO = getWeatherForCoordinatesHttpMethod(lat, lon);

        return WeatherCardDTO.builder()
                .locationId(id)
                .temp(weatherResponseDTO.getMain().getTemp())
                .feelsLike(weatherResponseDTO.getMain().getFeelsLike())
                .description(weatherResponseDTO.getWeather().get(0).getDescription())
                .nameLocation(weatherResponseDTO.getName())
                .country(weatherResponseDTO.getSys().getCountry())
                .build();
    }

    public List<WeatherCardDTO> findAllWeatherCards(Long id) {
        var ListLocations = locationsDAO.findAllbyUserId(id);
        List<WeatherCardDTO> allWeatherCards = new ArrayList<>();
        for (Location location : ListLocations) {
            allWeatherCards.add(getWeatherForLocation(location.getLat(), location.getLon(), location.getId()));
        }
        return allWeatherCards;
    }
}
