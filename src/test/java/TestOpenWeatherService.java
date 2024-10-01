import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.exception.InvalidStatusCodeException;
import com.example.weatherviewerapp.exception.OpenWeatherApiException;
import com.example.weatherviewerapp.services.OpenWeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
@Slf4j
@ExtendWith(MockitoExtension.class)
class TestOpenWeatherService {
    @InjectMocks
    private OpenWeatherService weatherService;
    @Mock
    private HttpClient mockHttpClient;
    @Mock
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() {
        weatherService = new OpenWeatherService(mockHttpClient, new ObjectMapper());
    }

    private void setUpMockHttpClient() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).
                thenReturn(mockHttpResponse);
    }

    @Test
    void testGetLocationsHttpMethod() throws IOException, InterruptedException {
        String jsonResponse = "[{\"name\":\"Minsk\",\"local_names\":{\"tg\":\"Минск\",\"ascii\":\"Minsk\"," +
                "\"ta\":\"மின்ஸ்க்\",\"vo\":\"Minsk\",\"feature_name\":\"Minsk\",\"os\":\"" +
                "Минск\",\"cs\":\"Minsk\",\"it\":\"Minsk\"},\"lat\":53.9024716,\"lon\":27.5618225,\"" +
                "country\":\"BY\"},{\"name\":\"Минск\",\"lat\":57.099061,\"lon\":93.3343983,\"country\":\"" +
                "RU\",\"state\":\"Krasnoyarsk Krai\"}]";

        setUpMockHttpClient();

        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        List<LocationResponseDTO> resultList = weatherService.getLocationsHttpMethod("Минск");
        Assertions.assertNotNull(resultList);
        Assertions.assertFalse(resultList.isEmpty());

        LocationResponseDTO locationDto = resultList.get(0);
        Assertions.assertEquals("Minsk", locationDto.getName());
        Assertions.assertEquals(27.5618225,locationDto.getLon());
        Assertions.assertEquals(53.9024716,locationDto.getLat());
        Assertions.assertEquals("BY",locationDto.getCountry());
    }

    @Test
    void testGetLocationHttpMethodWithEmptyBody() throws IOException, InterruptedException {
        setUpMockHttpClient();
        when(mockHttpResponse.body()).thenReturn("[]");

        List<LocationResponseDTO> resultList = weatherService.getLocationsHttpMethod("qwerty");
        Assertions.assertTrue(resultList.isEmpty());
    }

    @Test
    void testGetGeoLocationInfoWithServerError() throws IOException, InterruptedException {
        setUpMockHttpClient();
        when(mockHttpResponse.statusCode()).thenReturn(500);

        Assertions.assertThrows(InvalidStatusCodeException.class,
                () -> weatherService.getLocationsHttpMethod("London"));
    }
    @Test
    void testGetGeoLocationInfoWithClientError() throws IOException, InterruptedException {
        setUpMockHttpClient();
        when(mockHttpResponse.statusCode()).thenReturn(404);

        Assertions.assertThrows(InvalidStatusCodeException.class,
                () -> weatherService.getLocationsHttpMethod("Minsk"));
    }


    @Test
    void testGetGeoLocationInfoWithInvalidResponseFormat() throws IOException, InterruptedException {
        String invalidResponse = "{\"invalid\":\"response\"}";

        setUpMockHttpClient();
        when(mockHttpResponse.body()).thenReturn(invalidResponse);

        Assertions.assertThrows(OpenWeatherApiException.class,
                () -> weatherService.getLocationsHttpMethod("Minsk"));
    }
}


