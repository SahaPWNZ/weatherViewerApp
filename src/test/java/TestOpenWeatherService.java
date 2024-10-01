import com.example.weatherviewerapp.services.OpenWeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
@ExtendWith(MockitoExtension.class)
class TestOpenWeatherService {
    @InjectMocks
    private OpenWeatherService WeatherService;
    @Mock
    private HttpClient mockHttpClient;

    @BeforeEach
    void setUp(){
        WeatherService = new OpenWeatherService(mockHttpClient, new ObjectMapper());
    }
}
