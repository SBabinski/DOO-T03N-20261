import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TVMazeAPI {

    public Serie buscarSerie(String nomeSerie) throws Exception {
        String nomeFormatado = nomeSerie.replace(" ", "%20");
        String urlApi = "https://api.tvmaze.com/singlesearch/shows?q=" + nomeFormatado;

        URL url = new URL(urlApi);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        if (conexao.getResponseCode() != 200) {
            throw new Exception("Série não encontrada.");
        }

        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder json = new StringBuilder();
        String linha;

        while ((linha = leitor.readLine()) != null) {
            json.append(linha);
        }

        leitor.close();
        return converterJsonParaSerie(json.toString());
    }

    private Serie converterJsonParaSerie(String json) {
        String nome = extrairTexto(json, "name");
        String idioma = extrairTexto(json, "language");
        String status = extrairTexto(json, "status");
        String estreia = extrairTexto(json, "premiered");
        String termino = extrairTexto(json, "ended");
        String generos = extrairGeneros(json);
        String emissora = extrairEmissora(json);

        double nota = extrairNota(json);

        return new Serie(nome, idioma, generos, nota, status, estreia, termino, emissora);
    }

    private String extrairTexto(String json, String campo) {
        try {
            Pattern pattern = Pattern.compile("\"" + campo + "\":(null|\"(.*?)\")");
            Matcher matcher = pattern.matcher(json);

            if (matcher.find()) {
                String valor = matcher.group(2);
                return valor == null ? "Não informado" : valor;
            }
        } catch (Exception e) {
            return "Não informado";
        }

        return "Não informado";
    }

    private double extrairNota(String json) {
        try {
            Pattern pattern = Pattern.compile("\"rating\":\\{\"average\":(null|[0-9.]+)");
            Matcher matcher = pattern.matcher(json);

            if (matcher.find()) {
                String valor = matcher.group(1);

                if (valor == null || valor.equals("null")) {
                    return 0;
                }

                return Double.parseDouble(valor);
            }
        } catch (Exception e) {
            return 0;
        }

        return 0;
    }

    private String extrairGeneros(String json) {
        try {
            int inicio = json.indexOf("\"genres\":[");
            int fim = json.indexOf("]", inicio);

            if (inicio == -1 || fim == -1) return "Não informado";

            String generos = json.substring(inicio + 10, fim);
            generos = generos.replace("\"", "");

            if (generos.isEmpty()) return "Não informado";

            return generos;
        } catch (Exception e) {
            return "Não informado";
        }
    }

    private String extrairEmissora(String json) {
        try {
            int inicioNetwork = json.indexOf("\"network\":");

            if (inicioNetwork == -1) return "Não informado";

            int inicioNome = json.indexOf("\"name\":\"", inicioNetwork);

            if (inicioNome == -1) return "Não informado";

            inicioNome += 8;
            int fimNome = json.indexOf("\"", inicioNome);

            return json.substring(inicioNome, fimNome);
        } catch (Exception e) {
            return "Não informado";
        }
    }
}