import com.example.weatherviewerapp.services.OpenWeatherService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;


public class TestOpenWeather {
    private static final  OpenWeatherService openWeatherService = new OpenWeatherService();

@Test
    public void test1() throws URISyntaxException, IOException, InterruptedException {
    var a = openWeatherService.getLocationsHttpMethod("Minsk");
    System.out.println(a.get(0).getLat() +" : "+ a.get(0).getLon());
    openWeatherService.getWeatherForCoordinatesHttpMethod(a.get(0));
}
}
