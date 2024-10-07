package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.dto.api.WeatherResponseDTO;
import com.example.weatherviewerapp.exception.CustomException;
import com.example.weatherviewerapp.utils.ConfigUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
public class OpenWeatherService {
    private final String BASE_URL = "https://api.openweathermap.org/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenWeatherService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public OpenWeatherService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<LocationResponseDTO> getLocationsHttpMethod(String city) throws IOException {
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

            HttpResponse<String> response = getResponseIfValid(httpClient, request);
            log.info("Status code of response: " + response.statusCode());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (InterruptedException | MismatchedInputException | URISyntaxException e) {
            log.error("WeatherApiException", e);
            throw new CustomException("Problem with OpenWeatherApi");
        }
    }

    public WeatherResponseDTO getWeatherForCoordinatesHttpMethod(double lat, double lon) throws IOException {
        String DATA_URL = "data/2.5/weather?";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL
                            + DATA_URL
                            + "lat=" + lat
                            + "&lon=" + lon
                            + "&units=metric"
                            + "&appid=" + ConfigUtil.getApiKey()))
                    .GET()
                    .build();

            HttpResponse<String> response = getResponseIfValid(httpClient, request);
            log.info("Status code of response: " + response.statusCode());
            return objectMapper.readValue(response.body(), WeatherResponseDTO.class);
        } catch (InterruptedException | URISyntaxException | MismatchedInputException e) {
            log.error("WeatherApiException", e);
            throw new CustomException("Problem with OpenWeatherApi");
        }
    }

    private HttpResponse<String> getResponseIfValid(HttpClient client, HttpRequest req) throws IOException, InterruptedException {
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        int statusCode = res.statusCode();
        if ((statusCode >= 500 && statusCode < 600) || (statusCode >= 400 && statusCode < 500)) {
            log.error("Incorrect response status from Api");
            throw new CustomException("Incorrect response status from Api");
        }

        return res;
    }

}
