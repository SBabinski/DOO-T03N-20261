import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Classe responsavel por conversar com a API da TVmaze.
public class TVMazeClient {
    
    private static final String BASE_URL = "https://api.tvmaze.com/search/shows?q=";

    public List<Serie> buscarSeries(String nomeDigitado) throws IOException {
        // Valida o texto digitado antes de tentar acessar a internet.
        if (nomeDigitado == null || nomeDigitado.trim().isEmpty()) {
            throw new IOException("Digite o nome de uma serie para buscar.");
        }

        // Codifica espacos e caracteres especiais para a URL.
        // Exemplo: "breaking bad" vira "breaking+bad".
        String nomeNaUrl = URLEncoder.encode(nomeDigitado.trim(), StandardCharsets.UTF_8.name());
        URL url = new URL(BASE_URL + nomeNaUrl);

        // HttpURLConnection faz a requisicao HTTP sem biblioteca externa.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "SistemaSeriesDOO/1.0");

        // Se a API responder algo diferente de 200 OK, tratamos como erro.
        int statusCode = connection.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("A API retornou o codigo " + statusCode + ".");
        }

        // Le a resposta JSON retornada pela API.
        String json = readAll(connection.getInputStream());

        // Converte o JSON em List/Map para facilitar a leitura em Java.
        Object parsed = JsonParser.parse(json);
        if (!(parsed instanceof List)) {
            throw new IOException("Resposta inesperada da API.");
        }

        List<Serie> series = new ArrayList<Serie>();
        for (Object item : (List<?>) parsed) {
            if (item instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> result = (Map<String, Object>) item;
                Object show = result.get("show");
                if (show instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> showMap = (Map<String, Object>) show;

                    // Transforma os dados da API em objeto Serie.
                    series.add(Serie.criarAPartirDaTVMaze(showMap));
                }
            }
        }

        return series;
    }

    private String readAll(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        try {
            String line;
            // Junta todas as linhas da resposta em uma unica String.
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } finally {
            // Fecha o leitor mesmo se acontecer algum erro durante a leitura.
            reader.close();
        }
        return builder.toString();
    }
}
