package api;

import config.Configuracao;
import exception.ExcecaoApi;
import exception.ExcecaoRede;
import model.Serie;

import java.net.ConnectException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por toda a comunicação com a API pública do TVMaze.
 * Utiliza java.net.http.HttpClient e realiza o parsing do JSON manualmente.
 */
public class ClienteTVMaze {

    private static final String URL_BASE = "https://api.tvmaze.com";
    private static final int TEMPO_LIMITE_SEGUNDOS = 10;

    private final HttpClient clienteHttp;

    /**
     * Cria o cliente HTTP com tempo limite configurado.
     */
    public ClienteTVMaze() {
        this.clienteHttp = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TEMPO_LIMITE_SEGUNDOS))
                .build();
    }

    /**
     * Pesquisa séries na API pelo nome fornecido.
     *
     * @param nomeSerie nome da série a pesquisar
     * @return lista de séries encontradas
     * @throws ExcecaoApi se ocorrer erro na comunicação com a API
     */
    public List<Serie> pesquisarSeries(String nomeSerie) throws ExcecaoApi {
        try {
            String nomeCodificado = URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
            String url = URL_BASE + "/search/shows?q=" + nomeCodificado;

            HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(TEMPO_LIMITE_SEGUNDOS))
                    .GET()
                    .build();

            HttpResponse<String> resposta = clienteHttp.send(
                    requisicao, HttpResponse.BodyHandlers.ofString()
            );

            if (resposta.statusCode() != 200) {
                throw new ExcecaoApi(
                        "A API retornou o código de status: " + resposta.statusCode()
                );
            }

            return parsearListaSeries(resposta.body());

        } catch (ExcecaoApi e) {
            throw e;
        } catch (HttpConnectTimeoutException e) {
            throw new ExcecaoRede(
                    "Não foi possível conectar à API: tempo limite de conexão excedido (" + TEMPO_LIMITE_SEGUNDOS + "s).\n"
                    + "Verifique sua conexão com a internet ou se um firewall está bloqueando o acesso.", e);
        } catch (HttpTimeoutException e) {
            throw new ExcecaoRede(
                    "A requisição excedeu o tempo limite (" + TEMPO_LIMITE_SEGUNDOS + "s).\n"
                    + "Verifique sua conexão com a internet ou se um firewall está bloqueando o acesso.", e);
        } catch (UnknownHostException e) {
            throw new ExcecaoRede(
                    "Não foi possível resolver o endereço da API (api.tvmaze.com).\n"
                    + "Verifique sua conexão com a internet ou as configurações de DNS.", e);
        } catch (ConnectException e) {
            throw new ExcecaoRede(
                    "Não foi possível estabelecer conexão com a API.\n"
                    + "Verifique sua conexão com a internet ou se um firewall está bloqueando o acesso.", e);
        } catch (java.io.IOException e) {
            throw new ExcecaoRede(
                    "Erro de comunicação com a API: " + e.getMessage() + "\n"
                    + "Verifique sua conexão com a internet.", e);
        } catch (Exception e) {
            throw new ExcecaoApi("Erro inesperado ao acessar a API do TVMaze: " + e.getMessage(), e);
        }
    }

    /**
     * Parseia o JSON de resposta da pesquisa e retorna uma lista de séries.
     * O JSON tem o formato: [{"score": 1.0, "show": {...}}, ...]
     *
     * @param json string JSON retornada pela API
     * @return lista de Series parseadas
     */
    private List<Serie> parsearListaSeries(String json) {
        List<Serie> series = new ArrayList<>();
        json = json.trim();
        if (json.equals("[]") || json.isEmpty()) return series;

        // Cada item do array começa com {"score":..., "show":{...}}
        // Precisamos extrair cada bloco "show"
        int posicao = 0;
        while (posicao < json.length()) {
            int inicioShow = json.indexOf("\"show\":", posicao);
            if (inicioShow == -1) break;

            int inicioObjeto = json.indexOf("{", inicioShow + 7);
            if (inicioObjeto == -1) break;

            int fimObjeto = encontrarFimObjeto(json, inicioObjeto);
            if (fimObjeto == -1) break;

            String objetoShow = json.substring(inicioObjeto, fimObjeto + 1);
            Serie serie = parsearSerie(objetoShow);
            if (serie != null) {
                series.add(serie);
            }
            posicao = fimObjeto + 1;
        }
        return series;
    }

    /**
     * Parseia um objeto JSON individual de série.
     *
     * @param json string JSON do objeto show
     * @return Serie parseada ou null se inválida
     */
    private Serie parsearSerie(String json) {
        try {
            int id = parsearInteiro(json, "\"id\":");
            String nome = parsearString(json, "\"name\":");
            String idioma = parsearString(json, "\"language\":");
            String estado = traduzirEstado(parsearString(json, "\"status\":"));
            String dataEstreia = parsearString(json, "\"premiered\":");
            String dataTermino = parsearString(json, "\"ended\":");
            double nota = parsearNota(json);
            String generos = parsearGeneros(json);
            String emissora = parsearEmissora(json);
            String resumo = limparHtml(parsearString(json, "\"summary\":"));
            String urlImagem = parsearUrlImagem(json);

            if (nome == null || nome.isEmpty()) return null;

            return new Serie(id, nome, idioma, generos, nota, estado,
                    dataEstreia, dataTermino, emissora, resumo, urlImagem);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Encontra o índice do fechamento correspondente do objeto JSON.
     */
    private int encontrarFimObjeto(String json, int inicio) {
        int profundidade = 0;
        boolean dentroDeString = false;
        for (int i = inicio; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                dentroDeString = !dentroDeString;
            }
            if (!dentroDeString) {
                if (c == '{') profundidade++;
                else if (c == '}') {
                    profundidade--;
                    if (profundidade == 0) return i;
                }
            }
        }
        return -1;
    }

    /**
     * Extrai um valor de string de um campo JSON.
     */
    private String parsearString(String json, String chave) {
        int pos = json.indexOf(chave);
        if (pos == -1) return "";
        int inicio = json.indexOf("\"", pos + chave.length());
        if (inicio == -1) {
            // Pode ser null
            String trecho = json.substring(pos + chave.length(), Math.min(pos + chave.length() + 10, json.length())).trim();
            if (trecho.startsWith("null")) return "";
            return "";
        }
        int fim = inicio + 1;
        while (fim < json.length()) {
            if (json.charAt(fim) == '"' && json.charAt(fim - 1) != '\\') break;
            fim++;
        }
        return json.substring(inicio + 1, fim);
    }

    /**
     * Extrai um valor inteiro de um campo JSON.
     */
    private int parsearInteiro(String json, String chave) {
        int pos = json.indexOf(chave);
        if (pos == -1) return 0;
        int inicio = pos + chave.length();
        while (inicio < json.length() && Character.isWhitespace(json.charAt(inicio))) inicio++;
        int fim = inicio;
        while (fim < json.length() && (Character.isDigit(json.charAt(fim)) || json.charAt(fim) == '-')) fim++;
        try {
            return Integer.parseInt(json.substring(inicio, fim));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Extrai a nota (rating.average) do JSON.
     */
    private double parsearNota(String json) {
        int posRating = json.indexOf("\"rating\":");
        if (posRating == -1) return 0.0;
        String trecho = json.substring(posRating);
        int posAverage = trecho.indexOf("\"average\":");
        if (posAverage == -1) return 0.0;
        int inicio = posAverage + 10;
        while (inicio < trecho.length() && Character.isWhitespace(trecho.charAt(inicio))) inicio++;
        if (trecho.startsWith("null", inicio)) return 0.0;
        int fim = inicio;
        while (fim < trecho.length() && (Character.isDigit(trecho.charAt(fim)) || trecho.charAt(fim) == '.')) fim++;
        try {
            return Double.parseDouble(trecho.substring(inicio, fim));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Extrai e formata os gêneros do array JSON.
     */
    private String parsearGeneros(String json) {
        int posGenres = json.indexOf("\"genres\":");
        if (posGenres == -1) return "";
        int inicioArray = json.indexOf("[", posGenres);
        if (inicioArray == -1) return "";
        int fimArray = json.indexOf("]", inicioArray);
        if (fimArray == -1) return "";
        String arrayStr = json.substring(inicioArray + 1, fimArray);
        StringBuilder generos = new StringBuilder();
        int pos = 0;
        while (pos < arrayStr.length()) {
            int ini = arrayStr.indexOf("\"", pos);
            if (ini == -1) break;
            int fim = arrayStr.indexOf("\"", ini + 1);
            if (fim == -1) break;
            if (generos.length() > 0) generos.append(", ");
            generos.append(arrayStr, ini + 1, fim);
            pos = fim + 1;
        }
        return generos.toString();
    }

    /**
     * Extrai o nome da emissora (network ou webChannel).
     */
    private String parsearEmissora(String json) {
        // Tenta network primeiro
        int posNetwork = json.indexOf("\"network\":");
        if (posNetwork != -1) {
            String trecho = json.substring(posNetwork);
            if (!trecho.startsWith("\"network\":null")) {
                String nome = parsearString(trecho, "\"name\":");
                if (nome != null && !nome.isEmpty()) return nome;
            }
        }
        // Tenta webChannel
        int posWeb = json.indexOf("\"webChannel\":");
        if (posWeb != -1) {
            String trecho = json.substring(posWeb);
            if (!trecho.startsWith("\"webChannel\":null")) {
                String nome = parsearString(trecho, "\"name\":");
                if (nome != null && !nome.isEmpty()) return nome;
            }
        }
        return "Desconhecida";
    }

    /**
     * Extrai a URL da imagem medium do JSON.
     */
    private String parsearUrlImagem(String json) {
        int posImage = json.indexOf("\"image\":");
        if (posImage == -1) return "";
        String trecho = json.substring(posImage);
        if (trecho.startsWith("\"image\":null")) return "";
        return parsearString(trecho, "\"medium\":");
    }

    /**
     * Remove tags HTML básicas de um texto.
     */
    private String limparHtml(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.replaceAll("<[^>]+>", "").trim();
    }

    /**
     * Traduz o estado da série do inglês para o português.
     */
    private String traduzirEstado(String estadoIngles) {
        if (estadoIngles == null) return "Desconhecido";
        switch (estadoIngles) {
            case "Running": return "Em exibição";
            case "Ended": return "Encerrada";
            case "To Be Determined": return "A definir";
            case "In Development": return "Em desenvolvimento";
            default: return estadoIngles;
        }
    }
}
