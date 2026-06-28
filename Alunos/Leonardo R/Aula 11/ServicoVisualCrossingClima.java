import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServicoVisualCrossingClima implements ServicoClima {

    private static final String CHAVE_API = "VUPXCVN46HXAHW58UDUVFJ969";
    private static final String URL_BASE = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final int TEMPO_CONEXAO_MS = 10000;
    private static final int TEMPO_LEITURA_MS = 10000;

    @Override
    public ClimaAtual consultarPorCidade(String nomeCidade) throws Exception {
        String cidadeNormalizada = normalizarCidade(nomeCidade);
        String enderecoConsulta = montarEnderecoConsulta(cidadeNormalizada);

        String respostaApi = baixarJson(enderecoConsulta);
        String condicoesAtuais = extrairObjeto(respostaApi, "currentConditions");
        String primeiroDia = extrairPrimeiroObjetoDoArray(respostaApi, "days");

        String cidadeRetornada = lerTexto(respostaApi, "address");
        if (cidadeRetornada.isEmpty()) {
            cidadeRetornada = cidadeNormalizada;
        }

        double temperaturaAtual = lerNumero(condicoesAtuais, "temp");
        double umidade = lerNumero(condicoesAtuais, "humidity");
        String descricaoCondicao = lerTexto(condicoesAtuais, "conditions");
        double chuva = lerNumeroOpcional(condicoesAtuais, "precip", 0.0);
        double velocidadeVento = lerNumero(condicoesAtuais, "windspeed");
        String direcaoVento = formatarNumero(lerNumero(condicoesAtuais, "winddir"));

        double temperaturaMaxima = lerNumero(primeiroDia, "tempmax");
        double temperaturaMinima = lerNumero(primeiroDia, "tempmin");

        return new ClimaAtual(
                cidadeRetornada,
                temperaturaAtual,
                temperaturaMinima,
                temperaturaMaxima,
                umidade,
                descricaoCondicao,
                chuva,
                velocidadeVento,
                direcaoVento
        );
    }

    private static String normalizarCidade(String nomeCidade) {
        if (nomeCidade == null) {
            throw new IllegalArgumentException("Informe uma cidade.");
        }

        String cidadeTratada = nomeCidade.trim().replaceAll("\\s+", " ");

        if (cidadeTratada.isEmpty()) {
            throw new IllegalArgumentException("Informe uma cidade.");
        }

        return cidadeTratada;
    }

    private static String montarEnderecoConsulta(String cidadeNormalizada) throws IOException {
        String cidadeParaUrl = URLEncoder.encode(cidadeNormalizada, StandardCharsets.UTF_8.name())
                .replace("+", "%20");

        return URL_BASE + cidadeParaUrl
                + "?unitGroup=metric&key=" + CHAVE_API + "&contentType=json";
    }

    private static String baixarJson(String enderecoConsulta) throws IOException {
        HttpURLConnection conexao = (HttpURLConnection) new URL(enderecoConsulta).openConnection();
        conexao.setRequestMethod("GET");
        conexao.setRequestProperty("Accept", "application/json");
        conexao.setConnectTimeout(TEMPO_CONEXAO_MS);
        conexao.setReadTimeout(TEMPO_LEITURA_MS);

        try {
            int codigoHttp = conexao.getResponseCode();
            InputStream fluxoResposta = codigoHttp >= 200 && codigoHttp < 300
                    ? conexao.getInputStream()
                    : conexao.getErrorStream();
            String corpoResposta = lerRespostaCompleta(fluxoResposta);

            if (codigoHttp < 200 || codigoHttp >= 300) {
                throw new IOException("Erro ao buscar clima. HTTP "
                        + codigoHttp + ": " + corpoResposta);
            }

            return corpoResposta;
        } finally {
            conexao.disconnect();
        }
    }

    private static String lerRespostaCompleta(InputStream fluxo) throws IOException {
        if (fluxo == null) {
            return "";
        }

        StringBuilder conteudo = new StringBuilder();

        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(fluxo, StandardCharsets.UTF_8))) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                conteudo.append(linha);
            }
        }

        return conteudo.toString();
    }

    private static String extrairObjeto(String json, String nomeCampo) throws IOException {
        int indiceCampo = localizarCampo(json, nomeCampo);
        int inicioObjeto = json.indexOf('{', indiceCampo);

        if (inicioObjeto == -1) {
            throw new IOException("Campo '" + nomeCampo + "' nao encontrado na resposta da API.");
        }

        return recortarTextoBalanceado(json, inicioObjeto, '{', '}');
    }

    private static String extrairPrimeiroObjetoDoArray(String json, String nomeCampo) throws IOException {
        int indiceCampo = localizarCampo(json, nomeCampo);
        int inicioArray = json.indexOf('[', indiceCampo);
        int inicioObjeto = json.indexOf('{', inicioArray);

        if (inicioArray == -1 || inicioObjeto == -1) {
            throw new IOException("Campo '" + nomeCampo + "' nao encontrado na resposta da API.");
        }

        return recortarTextoBalanceado(json, inicioObjeto, '{', '}');
    }

    private static int localizarCampo(String json, String nomeCampo) throws IOException {
        int indiceCampo = json.indexOf("\"" + nomeCampo + "\"");

        if (indiceCampo == -1) {
            throw new IOException("Campo '" + nomeCampo + "' nao encontrado na resposta da API.");
        }

        return indiceCampo;
    }

    private static String recortarTextoBalanceado(String texto, int inicio, char abre, char fecha) throws IOException {
        int profundidade = 0;
        boolean dentroDeTexto = false;
        boolean caractereEscapado = false;

        for (int i = inicio; i < texto.length(); i++) {
            char atual = texto.charAt(i);

            if (caractereEscapado) {
                caractereEscapado = false;
                continue;
            }

            if (atual == '\\') {
                caractereEscapado = true;
                continue;
            }

            if (atual == '"') {
                dentroDeTexto = !dentroDeTexto;
                continue;
            }

            if (dentroDeTexto) {
                continue;
            }

            if (atual == abre) {
                profundidade++;
            } else if (atual == fecha) {
                profundidade--;

                if (profundidade == 0) {
                    return texto.substring(inicio, i + 1);
                }
            }
        }

        throw new IOException("Resposta da API em formato inesperado.");
    }

    private static String lerTexto(String json, String nomeCampo) {
        Pattern padrao = Pattern.compile("\"" + Pattern.quote(nomeCampo)
                + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
        Matcher resultado = padrao.matcher(json);

        if (!resultado.find()) {
            return "";
        }

        return desfazerEscapesJson(resultado.group(1));
    }

    private static double lerNumero(String json, String nomeCampo) throws IOException {
        Pattern padrao = Pattern.compile("\"" + Pattern.quote(nomeCampo)
                + "\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?(?:[eE][+-]?\\d+)?)");
        Matcher resultado = padrao.matcher(json);

        if (!resultado.find()) {
            throw new IOException("Campo numerico '" + nomeCampo + "' nao encontrado na resposta da API.");
        }

        return Double.parseDouble(resultado.group(1));
    }

    private static double lerNumeroOpcional(String json, String nomeCampo, double valorPadrao) throws IOException {
        try {
            return lerNumero(json, nomeCampo);
        } catch (IOException erro) {
            return valorPadrao;
        }
    }

    private static String formatarNumero(double valor) {
        if (valor == Math.rint(valor)) {
            return String.valueOf((int) valor);
        }

        return String.valueOf(valor);
    }

    private static String desfazerEscapesJson(String valor) {
        return valor
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\/", "/")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }
}
