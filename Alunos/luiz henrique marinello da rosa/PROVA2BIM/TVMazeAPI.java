import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// Quem fala com a API do TVMaze (a internet de verdade).
//
// endpoint da busca: https://api.tvmaze.com/search/shows?q=NOME
// a resposta e uma lista de { "show": {dados} }. eu pego cada "show",
// passo pro ConversorSerie e devolvo uma lista de Serie pronta.
//
// erro de rede vira ApiException, pra tela avisar e o programa nao fechar.

public class TVMazeAPI {

    private static final String URL_BUSCA = "https://api.tvmaze.com/search/shows?q=";

    // busca series pelo nome. pode dar ApiException (rede) ou JsonException (resposta torta)
    public List<Serie> buscarPorNome(String termo) throws ApiException, JsonException {

        if (termo == null || termo.trim().isEmpty()) {
            throw new ApiException("Digite um nome para buscar.");
        }

        String resposta = fazerRequisicao(termo.trim());

        // a resposta e um array de { score, show }
        Object raiz = Json.parse(resposta);

        List<Serie> resultado = new ArrayList<Serie>();

        if (!(raiz instanceof List)) {
            // veio algo estranho: devolvo lista vazia em vez de quebrar
            return resultado;
        }

        List<?> itens = (List<?>) raiz;
        for (Object item : itens) {
            if (item instanceof Map) {
                Map<?, ?> objeto = (Map<?, ?>) item;
                Object show = objeto.get("show");
                if (show instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Serie s = ConversorSerie.deMapaApi((Map<String, Object>) show);
                    if (s != null) {
                        resultado.add(s);
                    }
                }
            }
        }

        return resultado;
    }

    // faz o GET de fato e devolve o texto que a API mandou
    private String fazerRequisicao(String termo) throws ApiException {
        HttpURLConnection conexao = null;
        BufferedReader leitor = null;

        try {
            // codifico o termo pra url (espaco/acento viram %XX)
            String termoCodificado = URLEncoder.encode(termo, StandardCharsets.UTF_8.name());
            URL url = new URL(URL_BUSCA + termoCodificado);

            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(10000); // 10s pra conectar
            conexao.setReadTimeout(10000);    // 10s pra ler
            conexao.setRequestProperty("User-Agent", "ProvaDOO-AcompanhadorSeries/1.0");

            int codigo = conexao.getResponseCode();
            if (codigo != 200) {
                throw new ApiException("A API respondeu com codigo HTTP " + codigo
                        + ". Tente novamente mais tarde.");
            }

            leitor = new BufferedReader(
                    new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder conteudo = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                conteudo.append(linha);
            }

            return conteudo.toString();

        } catch (java.net.UnknownHostException e) {
            throw new ApiException("Sem conexao com a internet. "
                    + "Verifique sua rede e tente de novo.", e);
        } catch (java.net.SocketTimeoutException e) {
            throw new ApiException("A API demorou demais para responder (timeout).", e);
        } catch (IOException e) {
            throw new ApiException("Erro de comunicacao com a API: " + e.getMessage(), e);
        } finally {
            // fecho tudo no final, deu erro ou nao
            if (leitor != null) {
                try {
                    leitor.close();
                } catch (IOException ignorado) {
                    // nada a fazer
                }
            }
            if (conexao != null) {
                conexao.disconnect();
            }
        }
    }
}
