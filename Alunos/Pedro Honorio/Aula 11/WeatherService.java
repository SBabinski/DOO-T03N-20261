import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.JOptionPane;

public class WeatherService {

    private static final String API_KEY = System.getenv("API_KEY");

    public Clima buscarClima(String cidade) {

        try {
            String cidadeFormatada = cidade.trim().replace(" ", "%20");

            String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                    + cidadeFormatada
                    + "?unitGroup=metric&key=" + API_KEY
                    + "&contentType=json";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());


            if (!response.body().startsWith("{")) {
                throw new RuntimeException("Resposta inválida da API");
            }

            ObjectMapper mapper = new ObjectMapper();

            WeatherResponse resp = mapper.readValue(response.body(), WeatherResponse.class);

            Day d = resp.days.get(0);

            Clima clima = new Clima();

            clima.setTemperaturaAtual(d.temp);
            clima.setTemperaturaMaxima(d.tempmax);
            clima.setTemperaturaMinima(d.tempmin);
            clima.setUmidade(d.humidity);
            clima.setCondicao(d.conditions);
            clima.setPrecipitacao(d.precip);
            clima.setVelocidadeVento(d.windspeed);
            clima.setDirecaoVento(d.winddir);

            return clima;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar clima: " + e.getMessage());
            return null;
        }
    }
}
