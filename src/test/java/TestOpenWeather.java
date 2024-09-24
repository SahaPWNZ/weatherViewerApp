import com.example.weatherviewerapp.dto.WeatherCardDTO;
import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.OpenWeatherService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;


public class TestOpenWeather {
    private final  OpenWeatherService openWeatherService = new OpenWeatherService();
private final CookieService cookieService = new CookieService();
@Test
    public void test1() throws URISyntaxException, IOException, InterruptedException {
    var a = openWeatherService.getLocationsHttpMethod("Минск");
    for(LocationResponseDTO location: a){
        System.out.println(location);
    }
    var cards = openWeatherService.findAllWeatherCards(3L);
    for(WeatherCardDTO card: cards){
        System.out.println(card);
    }
}
}
