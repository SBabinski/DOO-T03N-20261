import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherService {
    private static final String BASE_URL =
            "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final String apiKey;

    public WeatherService(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("A chave da API não pode estar vazia.");
        }

        this.apiKey = apiKey.trim();
    }

    public WeatherData buscarClima(String cidade) throws IOException {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("A cidade não pode estar vazia.");
        }

        String url = montarUrl(cidade);
        String json = fazerRequisicao(url);

        String cidadeResolvida = extrairString(json, "resolvedAddress", cidade);

        String condicoesAtuais = extrairObjeto(json, "currentConditions");
        String diaAtual = extrairPrimeiroObjetoDoArray(json, "days");

        double temperaturaAtual = extrairDouble(condicoesAtuais, "temp", Double.NaN);
        double temperaturaMaxima = extrairDouble(diaAtual, "tempmax", Double.NaN);
        double temperaturaMinima = extrairDouble(diaAtual, "tempmin", Double.NaN);
        double umidade = extrairDouble(condicoesAtuais, "humidity", Double.NaN);
        String condicao = extrairString(condicoesAtuais, "conditions", "Não informada");
        double precipitacao = extrairDouble(diaAtual, "precip", 0.0);

        double velocidadeVento = extrairDouble(
                condicoesAtuais,
                "windspeed",
                extrairDouble(diaAtual, "windspeed", Double.NaN)
        );

        double direcaoVento = extrairDouble(
                condicoesAtuais,
                "winddir",
                extrairDouble(diaAtual, "winddir", Double.NaN)
        );

        return new WeatherData(
                cidadeResolvida,
                temperaturaAtual,
                temperaturaMaxima,
                temperaturaMinima,
                umidade,
                condicao,
                precipitacao,
                velocidadeVento,
                direcaoVento
        );
    }

    private String montarUrl(String cidade) throws IOException {
        String cidadeCodificada = URLEncoder.encode(cidade.trim(), "UTF-8").replace("+", "%20");
        String chaveCodificada = URLEncoder.encode(apiKey, "UTF-8");

        return BASE_URL + cidadeCodificada
                + "?unitGroup=metric"
                + "&include=current,days"
                + "&lang=pt"
                + "&contentType=json"
                + "&key=" + chaveCodificada;
    }

    private String fazerRequisicao(String urlCompleta) throws IOException {
        HttpURLConnection conexao = null;

        try {
            URL url = URI.create(urlCompleta).toURL();

            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(10000);
            conexao.setReadTimeout(10000);

            int status = conexao.getResponseCode();

            InputStream resposta;

            if (status >= 200 && status < 300) {
                resposta = conexao.getInputStream();
            } else {
                resposta = conexao.getErrorStream();
            }

            String corpoResposta = lerTexto(resposta);

            if (status != 200) {
                throw new IOException("Erro na consulta. Código HTTP " + status + ". Resposta: " + corpoResposta);
            }

            return corpoResposta;
        } finally {
            if (conexao != null) {
                conexao.disconnect();
            }
        }
    }

    private String lerTexto(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }

        StringBuilder texto = new StringBuilder();
        BufferedReader leitor = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String linha;

        while ((linha = leitor.readLine()) != null) {
            texto.append(linha);
        }

        leitor.close();

        return texto.toString();
    }

    private String extrairObjeto(String json, String chave) throws IOException {
        String marcador = "\"" + chave + "\"";
        int posicaoChave = json.indexOf(marcador);

        if (posicaoChave == -1) {
            throw new IOException("Campo não encontrado no JSON: " + chave);
        }

        int inicioObjeto = json.indexOf('{', posicaoChave);

        if (inicioObjeto == -1) {
            throw new IOException("Objeto não encontrado para o campo: " + chave);
        }

        return recortarPorChaves(json, inicioObjeto);
    }

    private String extrairPrimeiroObjetoDoArray(String json, String chave) throws IOException {
        String marcador = "\"" + chave + "\"";
        int posicaoChave = json.indexOf(marcador);

        if (posicaoChave == -1) {
            throw new IOException("Array não encontrado no JSON: " + chave);
        }

        int inicioArray = json.indexOf('[', posicaoChave);
        int inicioObjeto = json.indexOf('{', inicioArray);

        if (inicioArray == -1 || inicioObjeto == -1) {
            throw new IOException("Primeiro objeto do array não encontrado: " + chave);
        }

        return recortarPorChaves(json, inicioObjeto);
    }

    private String recortarPorChaves(String json, int inicioObjeto) throws IOException {
        int nivel = 0;
        boolean dentroString = false;
        boolean escapado = false;

        for (int i = inicioObjeto; i < json.length(); i++) {
            char caractere = json.charAt(i);

            if (escapado) {
                escapado = false;
                continue;
            }

            if (caractere == '\\') {
                escapado = true;
                continue;
            }

            if (caractere == '"') {
                dentroString = !dentroString;
                continue;
            }

            if (!dentroString) {
                if (caractere == '{') {
                    nivel++;
                } else if (caractere == '}') {
                    nivel--;

                    if (nivel == 0) {
                        return json.substring(inicioObjeto, i + 1);
                    }
                }
            }
        }

        throw new IOException("JSON inválido: objeto não foi fechado corretamente.");
    }

    private double extrairDouble(String json, String chave, double valorPadrao) {
        Pattern pattern = Pattern.compile(
                "\\\"" + Pattern.quote(chave) + "\\\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?|null)"
        );

        Matcher matcher = pattern.matcher(json);

        if (!matcher.find() || "null".equals(matcher.group(1))) {
            return valorPadrao;
        }

        return Double.parseDouble(matcher.group(1));
    }

    private String extrairString(String json, String chave, String valorPadrao) {
        Pattern pattern = Pattern.compile(
                "\\\"" + Pattern.quote(chave) + "\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"])*)\\\""
        );

        Matcher matcher = pattern.matcher(json);

        if (!matcher.find()) {
            return valorPadrao;
        }

        return matcher.group(1)
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }
}