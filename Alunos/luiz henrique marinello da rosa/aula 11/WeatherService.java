

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Servico responsavel por buscar dados climaticos na Visual Crossing API.
 * A chave da API e lida de um arquivo .env externo ao codigo.
 */
public class WeatherService {

    private static final String BASE_URL =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    /**
     * Le a chave da API do arquivo .env.
     * O arquivo deve estar na raiz do projeto com o formato: API_KEY=suachave
     */
    private String carregarApiKey() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(".env"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();
                if (linha.startsWith("API_KEY=")) {
                    return linha.substring("API_KEY=".length()).trim();
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo .env: " + e.getMessage());
        }
        return null;
    }

    public WeatherData buscarClima(String cidade) {
        String apiKey = carregarApiKey();

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("API Key nao encontrada. Verifique o arquivo .env");
            return null;
        }

        try {
            String cidadeCodificada = URLEncoder.encode(cidade, "UTF-8");
            String urlStr = BASE_URL + cidadeCodificada
                    + "/today?unitGroup=metric&include=current,days&key=" + apiKey
                    + "&contentType=json";

            URL url = new URL(urlStr);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(5000);
            conexao.setReadTimeout(5000);

            int codigoResposta = conexao.getResponseCode();
            if (codigoResposta != 200) {
                System.err.println("Erro na API. Codigo HTTP: " + codigoResposta);
                return null;
            }

            BufferedReader leitor = new BufferedReader(
                new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
            leitor.close();

            return parsearResposta(cidade, resposta.toString());

        } catch (Exception e) {
            System.err.println("Erro ao buscar clima: " + e.getMessage());
            return null;
        }
    }

    private WeatherData parsearResposta(String cidade, String json) {
        double tempAtual    = extrairDouble(json, "\"temp\":");
        double tempMax      = extrairDouble(json, "\"tempmax\":");
        double tempMin      = extrairDouble(json, "\"tempmin\":");
        double umidade      = extrairDouble(json, "\"humidity\":");
        double precipitacao = extrairDouble(json, "\"precip\":");
        double vento        = extrairDouble(json, "\"windspeed\":");
        double direcaoGrau  = extrairDouble(json, "\"winddir\":");
        String condicao     = extrairString(json, "\"conditions\":\"");

        return new WeatherData(cidade, tempAtual, tempMax, tempMin,
                               umidade, condicao, precipitacao, vento,
                               grausParaDirecao(direcaoGrau));
    }

    private double extrairDouble(String json, String chave) {
        int inicio = json.indexOf(chave);
        if (inicio == -1) return 0.0;
        inicio += chave.length();
        int fim = json.indexOf(",", inicio);
        if (fim == -1) fim = json.indexOf("}", inicio);
        try {
            return Double.parseDouble(json.substring(inicio, fim).trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String extrairString(String json, String chave) {
        int inicio = json.indexOf(chave);
        if (inicio == -1) return "N/A";
        inicio += chave.length();
        int fim = json.indexOf("\"", inicio);
        return json.substring(inicio, fim);
    }

    private String grausParaDirecao(double graus) {
        String[] direcoes = {"Norte", "Nordeste", "Leste", "Sudeste",
                             "Sul", "Sudoeste", "Oeste", "Noroeste"};
        int indice = (int) ((graus + 22.5) / 45) % 8;
        return direcoes[indice];
    }
}