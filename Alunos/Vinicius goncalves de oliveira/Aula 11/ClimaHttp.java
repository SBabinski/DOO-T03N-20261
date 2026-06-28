import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ClimaHttp {

    private static final String URL_BASE =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final String apiKey;

    public ClimaHttp(String apiKey) {
        this.apiKey = apiKey;
    }

    public DadosCLima buscar(String cidade) throws Excecoes {
        String json = get(cidade);
        return parse(json, cidade);
    }

    private String get(String cidade) throws Excecoes {
        try {
            String url = URL_BASE
                + URLEncoder.encode(cidade, StandardCharsets.UTF_8)
                + "/today?unitGroup=metric&include=current,days&key=" + apiKey + "&contentType=json";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(10_000);
            conn.setReadTimeout(10_000);

            Excecoes.verificarStatus(conn.getResponseCode(), cidade);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String linha;
                while ((linha = br.readLine()) != null) {
                    sb.append(linha);
                }
                return sb.toString();
            }
        } catch (Excecoes e) {
            throw e;
        } catch (Exception e) {
            throw new Excecoes("Falha de conexao: " + e.getMessage());
        }
    }

    private DadosCLima parse(String json, String cidadeFallback) throws Excecoes {
        try {
            String cidade = texto(json, "resolvedAddress", cidadeFallback);

            int ci = json.indexOf("\"currentConditions\"");
            if (ci == -1) {
                throw new Excecoes("Dados atuais nao encontrados.");
            }
            String cur = bloco(json, ci);

            double tempAtual = numero(cur, "temp");
            double humidade = numero(cur, "humidity");
            double precip = numero(cur, "precip");
            double vento = numero(cur, "windspeed");
            double dirVento = numero(cur, "winddir");
            String condicao = texto(cur, "conditions", "");

            int di = json.indexOf("\"days\"");
            String day = di != -1 ? bloco(json, di) : "{}";
            double tempMax = numero(day, "tempmax");
            double tempMin = numero(day, "tempmin");

            return new DadosCLima(cidade, tempAtual, tempMax, tempMin,
                humidade, condicao, precip, vento, dirVento);
        } catch (Excecoes e) {
            throw e;
        } catch (Exception e) {
            throw new Excecoes("Erro ao processar resposta da API.");
        }
    }

    private String bloco(String json, int from) {
        int start = json.indexOf('{', from);
        if (start == -1) {
            return "{}";
        }
        int depth = 0;
        int i = start;
        while (i < json.length()) {
            char c = json.charAt(i++);
            if (c == '{') {
                depth++;
            } else if (c == '}' && --depth == 0) {
                return json.substring(start, i);
            }
        }
        return "{}";
    }

    private double numero(String json, String chave) {
        int inicio = valorInicio(json, chave);
        if (inicio == -1) {
            return 0.0;
        }

        int fim = inicio;
        while (fim < json.length()) {
            char c = json.charAt(fim);
            if ((c < '0' || c > '9') && c != '-' && c != '.') {
                break;
            }
            fim++;
        }

        if (fim == inicio) {
            return 0.0;
        }
        return Double.parseDouble(json.substring(inicio, fim));
    }

    private String texto(String json, String chave, String def) {
        int inicio = valorInicio(json, chave);
        if (inicio == -1 || inicio >= json.length() || json.charAt(inicio) != '"') {
            return def;
        }

        inicio++;
        int fim = json.indexOf('"', inicio);
        return fim == -1 ? def : json.substring(inicio, fim);
    }

    private int valorInicio(String json, String chave) {
        int posChave = json.indexOf("\"" + chave + "\"");
        if (posChave == -1) {
            return -1;
        }

        int doisPontos = json.indexOf(':', posChave);
        if (doisPontos == -1) {
            return -1;
        }

        int inicio = doisPontos + 1;
        while (inicio < json.length() && Character.isWhitespace(json.charAt(inicio))) {
            inicio++;
        }
        return inicio;
    }
}
