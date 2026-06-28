import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Acesso a API publica TVmaze (https://www.tvmaze.com/api).
 * Responsavel por buscar series por nome e converter a resposta em objetos Serie.
 * Toda falha previsivel (rede, HTTP, JSON) e convertida em AppException com
 * mensagem amigavel, de modo que o programa nunca feche inesperadamente.
 */
public class TvMazeService {

    private final String baseUrl;
    private final HttpClient client;

    public TvMazeService() {
        // A URL base vem do .env; se ausente, usa o padrao oficial da TVmaze.
        this.baseUrl = EnvLoader.get("TVMAZE_API_URL", "https://api.tvmaze.com");
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(12))
                .build();
    }

    /**
     * Busca series cujo nome contenha o termo informado.
     * @throws AppException em caso de entrada invalida, falha de rede ou da API.
     */
    public List<Serie> buscarPorNome(String termo) throws AppException {
        if (termo == null || termo.trim().isEmpty()) {
            throw new AppException("Digite o nome de uma serie para pesquisar.");
        }

        String url = baseUrl + "/search/shows?q="
                + URLEncoder.encode(termo.trim(), StandardCharsets.UTF_8);

        String corpo = requisitar(url);

        try {
            Object raiz = Json.parse(corpo);
            if (!(raiz instanceof List)) {
                throw new AppException("Resposta inesperada da API ao buscar series.");
            }
            List<Serie> resultado = new ArrayList<>();
            for (Object item : (List<?>) raiz) {
                if (item instanceof Map) {
                    Object show = ((Map<?, ?>) item).get("show");
                    if (show instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> showMap = (Map<String, Object>) show;
                        resultado.add(Serie.fromShow(showMap));
                    }
                }
            }
            return resultado;
        } catch (JsonException e) {
            throw new AppException("Nao foi possivel interpretar a resposta da API.", e);
        }
    }

    private String requisitar(String url) throws AppException {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .header("Accept", "application/json")
                    .header("User-Agent", "SeriesTracker/1.0 (projeto educacional)")
                    .GET()
                    .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            int status = resp.statusCode();

            if (status == 200) return resp.body();
            if (status == 404) throw new AppException("Nenhuma serie encontrada (HTTP 404).");
            if (status == 429) throw new AppException("Muitas requisicoes em pouco tempo. Aguarde alguns segundos e tente novamente.");
            if (status >= 500) throw new AppException("O servidor da TVmaze esta indisponivel no momento (HTTP " + status + ").");
            throw new AppException("Falha na requisicao a API (HTTP " + status + ").");

        } catch (java.net.http.HttpConnectTimeoutException e) {
            throw new AppException("Tempo de conexao esgotado. Verifique sua internet e tente novamente.", e);
        } catch (java.net.UnknownHostException e) {
            throw new AppException("Sem conexao com a internet. Verifique sua rede e tente novamente.", e);
        } catch (IOException e) {
            throw new AppException("Falha de conexao ao acessar a API: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AppException("A busca foi interrompida.", e);
        }
    }
}
