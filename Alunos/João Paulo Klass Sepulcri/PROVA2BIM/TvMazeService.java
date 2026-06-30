import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Classe responsável por buscar séries na API TVMaze
public class TvMazeService {

    // Busca séries pelo nome informado pelo usuário
    public List<Serie> buscarSeries(String nomeBusca) {
        List<Serie> series = new ArrayList<>();

        try {
            String nomeFormatado = URLEncoder.encode(nomeBusca, StandardCharsets.UTF_8);
            String endereco = "https://api.tvmaze.com/search/shows?q=" + nomeFormatado;

            URL url = new URL(endereco);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(10000);
            conexao.setReadTimeout(10000);

            int codigoResposta = conexao.getResponseCode();

            if (codigoResposta != 200) {
                throw new RuntimeException("Erro na API. Código: " + codigoResposta);
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conexao.getInputStream())
            );

            StringBuilder resposta = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                resposta.append(linha);
            }

            reader.close();

            series = transformarJsonEmSeries(resposta.toString());

        } catch (Exception e) {
            System.out.println("Erro ao buscar séries: " + e.getMessage());
        }

        return series;
    }

    // Transforma o JSON recebido da API em uma lista de objetos Serie
    private List<Serie> transformarJsonEmSeries(String json) {
        List<Serie> series = new ArrayList<>();

        String[] partes = json.split("\"show\":");

        for (int i = 1; i < partes.length; i++) {
            String objeto = partes[i];

            Serie serie = new Serie();

            serie.setNome(extrairTexto(objeto, "\"name\":\""));
            serie.setIdioma(extrairTexto(objeto, "\"language\":\""));
            serie.setGeneros(extrairGeneros(objeto));
            serie.setNota(extrairNota(objeto));
            serie.setEstado(extrairTexto(objeto, "\"status\":\""));
            serie.setDataEstreia(extrairTexto(objeto, "\"premiered\":\""));
            serie.setDataTermino(extrairTexto(objeto, "\"ended\":\""));
            serie.setEmissora(extrairEmissora(objeto));

            series.add(serie);
        }

        return series;
    }

    // Extrai textos simples do JSON
    private String extrairTexto(String json, String campo) {
        int inicio = json.indexOf(campo);

        if (inicio == -1) {
            return "Não informado";
        }

        inicio += campo.length();
        int fim = json.indexOf("\"", inicio);

        if (fim == -1) {
            return "Não informado";
        }

        String valor = json.substring(inicio, fim);

        if (valor.equals("null") || valor.isBlank()) {
            return "Não informado";
        }

        return valor;
    }

    // Extrai a nota da série
    private double extrairNota(String json) {
        String campo = "\"average\":";
        int inicio = json.indexOf(campo);

        if (inicio == -1) {
            return 0.0;
        }

        inicio += campo.length();
        int fim = json.indexOf("}", inicio);

        if (fim == -1) {
            return 0.0;
        }

        String valor = json.substring(inicio, fim).trim();

        try {
            if (valor.equals("null")) {
                return 0.0;
            }

            return Double.parseDouble(valor);
        } catch (Exception e) {
            return 0.0;
        }
    }

    // Extrai os gêneros da série
    private List<String> extrairGeneros(String json) {
        List<String> generos = new ArrayList<>();

        String campo = "\"genres\":[";
        int inicio = json.indexOf(campo);

        if (inicio == -1) {
            return generos;
        }

        inicio += campo.length();
        int fim = json.indexOf("]", inicio);

        if (fim == -1) {
            return generos;
        }

        String textoGeneros = json.substring(inicio, fim);
        textoGeneros = textoGeneros.replace("\"", "");

        if (textoGeneros.isBlank()) {
            return generos;
        }

        String[] partes = textoGeneros.split(",");

        for (String genero : partes) {
            generos.add(genero.trim());
        }

        return generos;
    }

    // Extrai a emissora.
    private String extrairEmissora(String json) {
        int inicioNetwork = json.indexOf("\"network\":");

        if (inicioNetwork != -1) {
            String parteNetwork = json.substring(inicioNetwork);
            String nome = extrairTexto(parteNetwork, "\"name\":\"");

            if (!nome.equals("Não informado")) {
                return nome;
            }
        }

        int inicioWebChannel = json.indexOf("\"webChannel\":");

        if (inicioWebChannel != -1) {
            String parteWebChannel = json.substring(inicioWebChannel);
            String nome = extrairTexto(parteWebChannel, "\"name\":\"");

            if (!nome.equals("Não informado")) {
                return nome;
            }
        }

        return "Não informado";
    }
}