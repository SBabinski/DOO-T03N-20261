import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TvMazeService {
    private static final String URL_BASE = "https://api.tvmaze.com/search/shows?q=";
    private String ultimaMensagemErro;

    public List<Serie> buscarSeriesPorNome(String nomeSerie) {
        List<Serie> series = new ArrayList<>();
        ultimaMensagemErro = null;

        if (nomeSerie == null || nomeSerie.trim().isEmpty()) {
            return series;
        }

        try {
            String nomeCodificado = URLEncoder.encode(nomeSerie.trim(), StandardCharsets.UTF_8.toString());
            String urlCompleta = URL_BASE + nomeCodificado;

            String jsonResposta = executarRequisicaoGet(urlCompleta);

            return converterJsonParaSeries(jsonResposta);
        } catch (Exception erro) {
            ultimaMensagemErro = "Não foi possível consultar a API TVMaze. Verifique sua conexão e tente novamente";
            System.out.println("Erro ao buscar séries na API: " + erro.getMessage());
            return series;
        }
    }

    public boolean teveErroNaUltimaBusca() {
        return ultimaMensagemErro != null;
    }

    public String getUltimaMensagemErro() {
        return ultimaMensagemErro;
    }

    private String executarRequisicaoGet(String urlCompleta) throws Exception {
        URL url = new URL(urlCompleta);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(8000);
        conexao.setReadTimeout(8000);
        conexao.setRequestProperty("Accept", "application/json");

        int codigoResposta = conexao.getResponseCode();

        if (codigoResposta != HttpURLConnection.HTTP_OK) {
            throw new Exception("A API retornou o código: " + codigoResposta);
        }

        StringBuilder resposta = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8)
        )) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                resposta.append(linha);
            }
        } finally {
            conexao.disconnect();
        }

        return resposta.toString();
    }

    private List<Serie> converterJsonParaSeries(String json) {
        List<Serie> series = new ArrayList<>();

        if (json == null || json.trim().isEmpty() || json.equals("[]")) {
            return series;
        }

        List<String> objetosShow = extrairObjetosShow(json);

        for (String objetoShow : objetosShow) {
            Serie serie = converterObjetoShowParaSerie(objetoShow);

            if (serie != null) {
                series.add(serie);
            }
        }

        return series;
    }

    private List<String> extrairObjetosShow(String json) {
        List<String> objetosShow = new ArrayList<>();

        String chaveShow = "\"show\":";
        int posicaoAtual = 0;

        while (true) {
            int inicioShow = json.indexOf(chaveShow, posicaoAtual);

            if (inicioShow == -1) {
                break;
            }

            int inicioObjeto = json.indexOf("{", inicioShow);

            if (inicioObjeto == -1) {
                break;
            }

            int fimObjeto = encontrarFimObjeto(json, inicioObjeto);

            if (fimObjeto == -1) {
                break;
            }

            objetosShow.add(json.substring(inicioObjeto, fimObjeto + 1));
            posicaoAtual = fimObjeto + 1;
        }

        return objetosShow;
    }

    private int encontrarFimObjeto(String json, int inicioObjeto) {
        int contadorChaves = 0;
        boolean dentroString = false;
        boolean caractereEscapado = false;

        for (int i = inicioObjeto; i < json.length(); i++) {
            char caractere = json.charAt(i);

            if (caractereEscapado) {
                caractereEscapado = false;
                continue;
            }

            if (caractere == '\\') {
                caractereEscapado = true;
                continue;
            }

            if (caractere == '"') {
                dentroString = !dentroString;
                continue;
            }

            if (!dentroString) {
                if (caractere == '{') {
                    contadorChaves++;
                } else if (caractere == '}') {
                    contadorChaves--;

                    if (contadorChaves == 0) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }

    private Serie converterObjetoShowParaSerie(String objetoShow) {
        try {
            int id = extrairInteiro(objetoShow, "id");
            String nome = extrairTexto(objetoShow, "name");
            String idioma = extrairTexto(objetoShow, "language");
            List<String> generos = extrairListaTexto(objetoShow, "genres");
            double notaGeral = extrairNotaGeral(objetoShow);
            String estado = extrairTexto(objetoShow, "status");
            String dataEstreia = extrairTexto(objetoShow, "premiered");
            String dataTermino = extrairTexto(objetoShow, "ended");
            String emissora = extrairEmissora(objetoShow);
            String imagemUrl = extrairImagemUrl(objetoShow);

            return new Serie(
                    id,
                    nome,
                    idioma,
                    generos,
                    notaGeral,
                    estado,
                    dataEstreia,
                    dataTermino,
                    emissora,
                    imagemUrl
            );
        } catch (Exception erro) {
            System.out.println("Erro ao converter série: " + erro.getMessage());
            return null;
        }
    }

    private int extrairInteiro(String json, String chave) {
        String valor = extrairValorSimples(json, chave);

        if (valor.isEmpty() || valor.equals("null")) {
            return 0;
        }

        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException erro) {
            return 0;
        }
    }

    private String extrairTexto(String json, String chave) {
        String chaveCompleta = "\"" + chave + "\"";
        int inicioChave = json.indexOf(chaveCompleta);

        if (inicioChave == -1) {
            return "Não informado";
        }

        int doisPontos = json.indexOf(":", inicioChave);

        if (doisPontos == -1) {
            return "Não informado";
        }

        int inicioValor = doisPontos + 1;

        while (inicioValor < json.length() && Character.isWhitespace(json.charAt(inicioValor))) {
            inicioValor++;
        }

        if (inicioValor >= json.length()) {
            return "Não informado";
        }

        if (json.startsWith("null", inicioValor)) {
            return "Não informado";
        }

        if (json.charAt(inicioValor) != '"') {
            return "Não informado";
        }

        StringBuilder valor = new StringBuilder();
        boolean caractereEscapado = false;

        for (int i = inicioValor + 1; i < json.length(); i++) {
            char caractere = json.charAt(i);

            if (caractereEscapado) {
                valor.append(converterCaractereEscapado(caractere));
                caractereEscapado = false;
                continue;
            }

            if (caractere == '\\') {
                caractereEscapado = true;
                continue;
            }

            if (caractere == '"') {
                break;
            }

            valor.append(caractere);
        }

        String texto = valor.toString().trim();

        if (texto.isEmpty()) {
            return "Não informado";
        }

        return texto;
    }

    private char converterCaractereEscapado(char caractere) {
        switch (caractere) {
            case 'n':
                return '\n';

            case 't':
                return '\t';

            case 'r':
                return '\r';

            case '"':
                return '"';

            case '\\':
                return '\\';

            default:
                return caractere;
        }
    }

    private String extrairValorSimples(String json, String chave) {
        String chaveCompleta = "\"" + chave + "\"";
        int inicioChave = json.indexOf(chaveCompleta);

        if (inicioChave == -1) {
            return "";
        }

        int doisPontos = json.indexOf(":", inicioChave);

        if (doisPontos == -1) {
            return "";
        }

        int inicioValor = doisPontos + 1;

        while (inicioValor < json.length() && Character.isWhitespace(json.charAt(inicioValor))) {
            inicioValor++;
        }

        StringBuilder valor = new StringBuilder();

        for (int i = inicioValor; i < json.length(); i++) {
            char caractere = json.charAt(i);

            if (caractere == ',' || caractere == '}' || caractere == ']') {
                break;
            }

            valor.append(caractere);
        }

        return valor.toString().trim();
    }

    private List<String> extrairListaTexto(String json, String chave) {
        List<String> valores = new ArrayList<>();

        String chaveCompleta = "\"" + chave + "\"";
        int inicioChave = json.indexOf(chaveCompleta);

        if (inicioChave == -1) {
            return valores;
        }

        int inicioLista = json.indexOf("[", inicioChave);
        int fimLista = json.indexOf("]", inicioLista);

        if (inicioLista == -1 || fimLista == -1) {
            return valores;
        }

        String conteudoLista = json.substring(inicioLista + 1, fimLista);

        int posicaoAtual = 0;

        while (posicaoAtual < conteudoLista.length()) {
            int inicioTexto = conteudoLista.indexOf("\"", posicaoAtual);

            if (inicioTexto == -1) {
                break;
            }

            int fimTexto = conteudoLista.indexOf("\"", inicioTexto + 1);

            if (fimTexto == -1) {
                break;
            }

            String valor = conteudoLista.substring(inicioTexto + 1, fimTexto).trim();

            if (!valor.isEmpty()) {
                valores.add(valor);
            }

            posicaoAtual = fimTexto + 1;
        }

        return valores;
    }

    private double extrairNotaGeral(String json) {
        String chaveRating = "\"rating\"";
        int inicioRating = json.indexOf(chaveRating);

        if (inicioRating == -1) {
            return 0;
        }

        int inicioObjetoRating = json.indexOf("{", inicioRating);

        if (inicioObjetoRating == -1) {
            return 0;
        }

        int fimObjetoRating = encontrarFimObjeto(json, inicioObjetoRating);

        if (fimObjetoRating == -1) {
            return 0;
        }

        String objetoRating = json.substring(inicioObjetoRating, fimObjetoRating + 1);
        String valorAverage = extrairValorSimples(objetoRating, "average");

        if (valorAverage.isEmpty() || valorAverage.equals("null")) {
            return 0;
        }

        try {
            return Double.parseDouble(valorAverage);
        } catch (NumberFormatException erro) {
            return 0;
        }
    }

    private String extrairEmissora(String json) {
        String emissoraNetwork = extrairNomeObjetoInterno(json, "network");

        if (!emissoraNetwork.equals("Não informado")) {
            return emissoraNetwork;
        }

        String emissoraWebChannel = extrairNomeObjetoInterno(json, "webChannel");

        if (!emissoraWebChannel.equals("Não informado")) {
            return emissoraWebChannel;
        }

        return "Não informado";
    }

    private String extrairNomeObjetoInterno(String json, String chaveObjeto) {
        String chave = "\"" + chaveObjeto + "\"";
        int inicioChave = json.indexOf(chave);

        if (inicioChave == -1) {
            return "Não informado";
        }

        int doisPontos = json.indexOf(":", inicioChave);

        if (doisPontos == -1) {
            return "Não informado";
        }

        int inicioValor = doisPontos + 1;

        while (inicioValor < json.length() && Character.isWhitespace(json.charAt(inicioValor))) {
            inicioValor++;
        }

        if (json.startsWith("null", inicioValor)) {
            return "Não informado";
        }

        int inicioObjeto = json.indexOf("{", inicioValor);

        if (inicioObjeto == -1) {
            return "Não informado";
        }

        int fimObjeto = encontrarFimObjeto(json, inicioObjeto);

        if (fimObjeto == -1) {
            return "Não informado";
        }

        String objetoInterno = json.substring(inicioObjeto, fimObjeto + 1);

        return extrairTexto(objetoInterno, "name");
    }

    private String extrairImagemUrl(String json) {
        String chaveImagem = "\"image\"";
        int inicioImagem = json.indexOf(chaveImagem);

        if (inicioImagem == -1) {
            return "";
        }

        int doisPontos = json.indexOf(":", inicioImagem);

        if (doisPontos == -1) {
            return "";
        }

        int inicioValor = doisPontos + 1;

        while (inicioValor < json.length() && Character.isWhitespace(json.charAt(inicioValor))) {
            inicioValor++;
        }

        if (json.startsWith("null", inicioValor)) {
            return "";
        }

        int inicioObjetoImagem = json.indexOf("{", inicioValor);

        if (inicioObjetoImagem == -1) {
            return "";
        }

        int fimObjetoImagem = encontrarFimObjeto(json, inicioObjetoImagem);

        if (fimObjetoImagem == -1) {
            return "";
        }

        String objetoImagem = json.substring(inicioObjetoImagem, fimObjetoImagem + 1);

        String imagemMedium = extrairTexto(objetoImagem, "medium");

        if (!"Não informado".equals(imagemMedium)) {
            return imagemMedium;
        }

        String imagemOriginal = extrairTexto(objetoImagem, "original");

        if (!"Não informado".equals(imagemOriginal)) {
            return imagemOriginal;
        }

        return "";
    }
}
