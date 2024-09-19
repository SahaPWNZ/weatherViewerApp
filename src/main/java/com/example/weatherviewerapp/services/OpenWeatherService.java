package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dto.LocationResponseDTO;
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
import java.util.List;

@Slf4j
public class OpenWeatherService {
   private static final String BASE_URL = "https://api.openweathermap.org/";
   private static final String GEO_URL = "geo/1.0/direct?";
   private static final HttpClient httpClient = HttpClient.newHttpClient();
private static final ObjectMapper objectMapper = new ObjectMapper();
   public void httpMethod(String sity) throws URISyntaxException, IOException, InterruptedException {

      HttpRequest request = HttpRequest.newBuilder()
              .uri(new URI(BASE_URL
                      + GEO_URL
              + "q=" + sity
              + "&limit=5"
              + "&appid=" + ConfigUtil.getApiKey()))
              .GET()
              .build();
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      log.info(String.valueOf(response.statusCode()));
      var locations =  objectMapper.readValue(response.body(), new TypeReference<List<LocationResponseDTO>>() {});
      for (var location : locations){

         log.info(location.toString());
         System.out.println();

      }
   }
}
