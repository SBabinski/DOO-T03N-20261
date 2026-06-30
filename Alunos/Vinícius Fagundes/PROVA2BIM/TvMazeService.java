
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Cliente HTTP responsável somente pela comunicação com o TVmaze. */
public final class TvMazeService {
    private static final String ENDPOINT = "https://api.tvmaze.com/search/shows?q=";
    private static final Pattern ENTIDADE_NUMERICA = Pattern.compile("&#(x?[0-9A-Fa-f]+);");
    private final HttpClient cliente;

    public TvMazeService() {
        this.cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public List<Serie> buscarPorNome(String nome) throws TvMazeException {
        if (nome == null || nome.isBlank()) {
            throw new TvMazeException("Digite o nome de uma série.");
        }

        String consulta = URLEncoder.encode(nome.trim(), StandardCharsets.UTF_8);
        HttpRequest requisicao = HttpRequest.newBuilder(URI.create(ENDPOINT + consulta))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .header("User-Agent", "SerieFinder/2.0 (projeto acadêmico; dados por TVmaze)")
                .GET()
                .build();

        try {
            HttpResponse<String> resposta = cliente.send(
                    requisicao, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (resposta.statusCode() == 429) {
                throw new TvMazeException("O TVmaze recebeu muitas consultas. Aguarde alguns segundos e tente novamente.");
            }
            if (resposta.statusCode() != 200) {
                throw new TvMazeException("O TVmaze respondeu com o código HTTP " + resposta.statusCode() + ".");
            }
            return interpretarResposta(resposta.body());
        } catch (InterruptedException erro) {
            Thread.currentThread().interrupt();
            throw new TvMazeException("A consulta foi interrompida.", erro);
        } catch (IOException erro) {
            throw new TvMazeException("Não foi possível acessar o TVmaze. Verifique sua conexão com a internet.", erro);
        }
    }

    private List<Serie> interpretarResposta(String json) throws TvMazeException {
        try {
            Object raiz = JsonUtil.parse(json);
            if (!(raiz instanceof List<?>)) {
                throw new IllegalArgumentException("A resposta não contém uma lista.");
            }
            List<?> resultados = (List<?>) raiz;
            List<Serie> series = new ArrayList<>();
            for (Object resultado : resultados) {
                Map<String, Object> item = comoMapa(resultado);
                Map<String, Object> show = comoMapa(item.get("show"));
                series.add(converterSerie(show));
            }
            return series;
        } catch (RuntimeException erro) {
            throw new TvMazeException("O TVmaze retornou dados em um formato inesperado.", erro);
        }
    }

    private Serie converterSerie(Map<String, Object> show) {
        long id = comoLong(show.get("id"), -1);
        String nome = comoTexto(show.get("name"));
        String idioma = comoTexto(show.get("language"));
        List<String> generos = comoListaDeTextos(show.get("genres"));
        double nota = -1;
        if (show.get("rating") instanceof Map<?, ?>) {
            Map<?, ?> rating = (Map<?, ?>) show.get("rating");
            nota = comoDouble(rating.get("average"), -1);
        }
        String status = comoTexto(show.get("status"));
        LocalDate estreia = comoData(show.get("premiered"));
        LocalDate termino = comoData(show.get("ended"));
        String emissora = nomeDaEmissora(show);
        String resumo = limparHtml(comoTexto(show.get("summary")));
        String imagem = urlDaImagem(show.get("image"));
        return new Serie(id, nome, idioma, generos, nota, status, estreia, termino,
                emissora, resumo, imagem);
    }

    private static String nomeDaEmissora(Map<String, Object> show) {
        for (String chave : List.of("network", "webChannel")) {
            if (show.get(chave) instanceof Map<?, ?>) {
                Map<?, ?> canal = (Map<?, ?>) show.get(chave);
                String nome = comoTexto(canal.get("name"));
                if (nome != null && !nome.isBlank()) {
                    return nome;
                }
            }
        }
        return "Não informada";
    }

    private static String urlDaImagem(Object valor) {
        if (!(valor instanceof Map<?, ?>)) {
            return "";
        }
        Map<?, ?> imagem = (Map<?, ?>) valor;
        String media = comoTexto(imagem.get("medium"));
        return media == null ? "" : media;
    }

    private static LocalDate comoData(Object valor) {
        String texto = comoTexto(valor);
        if (texto == null || texto.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(texto);
        } catch (DateTimeParseException ignorado) {
            return null;
        }
    }

    private static String limparHtml(String html) {
        if (html == null || html.isBlank()) {
            return "Resumo não disponível.";
        }
        String texto = html.replaceAll("(?i)<br\\s*/?>", "\n")
                .replaceAll("(?i)</p>", "\n")
                .replaceAll("<[^>]+>", "")
                .replace("&nbsp;", " ")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&#39;", "'")
                .replace("&lt;", "<")
                .replace("&gt;", ">");
        Matcher matcher = ENTIDADE_NUMERICA.matcher(texto);
        StringBuffer saida = new StringBuffer();
        while (matcher.find()) {
            String numero = matcher.group(1);
            int base = numero.startsWith("x") ? 16 : 10;
            if (base == 16) {
                numero = numero.substring(1);
            }
            try {
                matcher.appendReplacement(saida,
                        Matcher.quoteReplacement(String.valueOf((char) Integer.parseInt(numero, base))));
            } catch (NumberFormatException erro) {
                matcher.appendReplacement(saida, Matcher.quoteReplacement(matcher.group()));
            }
        }
        matcher.appendTail(saida);
        return saida.toString().replaceAll("[ \\t]+", " ").replaceAll("\\n{3,}", "\n\n").trim();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> comoMapa(Object valor) {
        if (!(valor instanceof Map<?, ?>)) {
            throw new IllegalArgumentException("Objeto JSON ausente.");
        }
        return (Map<String, Object>) valor;
    }

    private static List<String> comoListaDeTextos(Object valor) {
        if (!(valor instanceof List<?>)) {
            return List.of();
        }
        List<?> lista = (List<?>) valor;
        List<String> textos = new ArrayList<>();
        for (Object item : lista) {
            String texto = comoTexto(item);
            if (texto != null && !texto.isBlank()) {
                textos.add(texto);
            }
        }
        return textos;
    }

    private static String comoTexto(Object valor) {
        return valor == null ? null : String.valueOf(valor);
    }

    private static long comoLong(Object valor, long padrao) {
        return valor instanceof Number ? ((Number) valor).longValue() : padrao;
    }

    private static double comoDouble(Object valor, double padrao) {
        return valor instanceof Number ? ((Number) valor).doubleValue() : padrao;
    }
}
