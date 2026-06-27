
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Essa classe usada para series e Filmes na API 
public class Api {

    private static final String URL_BUSCA = "https://api.tvmaze.com/search/shows?q=";

    @SuppressWarnings("unchecked")
    public List<Serie> buscarPorNome(String nome) throws ErroApi {
        try {
            String nomeCodificado;
            try {
                nomeCodificado = URLEncoder.encode(nome, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                nomeCodificado = nome;
            }
            String urlCompleta = URL_BUSCA + nomeCodificado;

            String resposta = chamarApi(urlCompleta);

            List<Object> listaResultados = (List<Object>) Json.ler(resposta);

            List<Serie> series = new ArrayList<>();
            for (Object item : listaResultados) {
                Map<String, Object> itemMapa = (Map<String, Object>) item;
                Map<String, Object> showMapa = (Map<String, Object>) itemMapa.get("show");
                series.add(converterParaSerie(showMapa));
            }
            return series;

        } catch (ErroApi e) {
            throw e;
        } catch (Exception e) {
            // Imprime o erro completo no terminal, para facilitar descobrir a causa real.
            e.printStackTrace();
            throw new ErroApi("Erro ao interpretar a resposta da API: " + e.getMessage(), e);
        }
    }

    private String chamarApi(String url) throws ErroApi {
        HttpURLConnection conexao = null;
        try {
            conexao = (HttpURLConnection) URI.create(url).toURL().openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(8000);
            conexao.setReadTimeout(8000);
            // Alguns servidores recusam conexao sem um User-Agent "normal" de navegador.
            conexao.setRequestProperty("User-Agent", "Mozilla/5.0 (Java TVTracker App)");
            conexao.setRequestProperty("Accept", "application/json");

            int codigo = conexao.getResponseCode();
            if (codigo != HttpURLConnection.HTTP_OK) {
                throw new ErroApi("A API respondeu com codigo de erro: " + codigo);
            }

            InputStream entrada = conexao.getInputStream();
            BufferedReader leitor = new BufferedReader(new InputStreamReader(entrada, StandardCharsets.UTF_8));
            try {
                StringBuilder textoCompleto = new StringBuilder();
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    textoCompleto.append(linha);
                }
                return textoCompleto.toString();
            } finally {
                leitor.close();
            }

        } catch (IOException e) {
            // Mostra a mensagem tecnica real
            e.printStackTrace();
            throw new ErroApi("Nao foi possivel conectar a internet. Detalhe: " + e.getMessage(), e);
        } finally {
            if (conexao != null) conexao.disconnect();
        }
    }

    @SuppressWarnings("unchecked")
    private Serie converterParaSerie(Map<String, Object> showMapa) {
        Serie serie = new Serie();

        Object id = showMapa.get("id");
        serie.setId(id != null ? ((Number) id).intValue() : 0);

        serie.setNome((String) showMapa.get("name"));
        serie.setIdioma((String) showMapa.get("language"));
        serie.setEstado((String) showMapa.get("status"));
        serie.setDataEstreia((String) showMapa.get("premiered"));
        serie.setDataTermino((String) showMapa.get("ended"));

        Object generos = showMapa.get("genres");
        if (generos instanceof List) {
            List<String> listaGeneros = new ArrayList<>();
            for (Object g : (List<Object>) generos) {
                listaGeneros.add(String.valueOf(g));
            }
            serie.setGeneros(listaGeneros);
        }

        Object rating = showMapa.get("rating");
        if (rating instanceof Map) {
            Object media = ((Map<String, Object>) rating).get("average");
            serie.setNota(media != null ? ((Number) media).doubleValue() : null);
        }

        String nomeEmissora = "Nao informado";
        Object network = showMapa.get("network");
        Object webChannel = showMapa.get("webChannel");
        if (network instanceof Map) {
            Object nome = ((Map<String, Object>) network).get("name");
            if (nome != null) nomeEmissora = nome.toString();
        } else if (webChannel instanceof Map) {
            Object nome = ((Map<String, Object>) webChannel).get("name");
            if (nome != null) nomeEmissora = nome.toString();
        }
        serie.setEmissora(nomeEmissora);

        return serie;
    }
}