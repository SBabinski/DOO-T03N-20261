import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class BuscadorClima {

    private static final String URL_BASE =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final String chaveApi;

    public BuscadorClima(String chaveApi) {
        this.chaveApi = chaveApi;
        desabilitarVerificacaoSSL();
    }

    public DadosClima buscarClima(String cidade) throws Exception {
        String cidadeCodificada = URLEncoder.encode(cidade, "UTF-8");
        String urlCompleta = URL_BASE + cidadeCodificada
                + "?unitGroup=metric&include=current,days&contentType=json&key=" + chaveApi;

        HttpURLConnection conexao = (HttpURLConnection) new URL(urlCompleta).openConnection();
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(5000);
        conexao.setReadTimeout(5000);

        if (conexao.getResponseCode() != 200) {
            throw new Exception("Erro na API. Código HTTP: " + conexao.getResponseCode());
        }

        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder conteudo = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) conteudo.append(linha);
        leitor.close();
        conexao.disconnect();

        String json = conteudo.toString();

        String condicoesAtuais = extrairBloco(json, "currentConditions");
        String diaAtual        = extrairPrimeiroBloco(json, "days");

        double temperaturaAtual  = extrairDouble(condicoesAtuais, "temp");
        double humidade          = extrairDouble(condicoesAtuais, "humidity");
        String condicaoTempo     = extrairString(condicoesAtuais, "conditions");
        double precipitacao      = extrairDouble(condicoesAtuais, "precip");
        double velocidadeVento   = extrairDouble(condicoesAtuais, "windspeed");
        String direcaoVento      = grausParaPontoCardeal(extrairDouble(condicoesAtuais, "winddir"));
        double temperaturaMaxima = extrairDouble(diaAtual, "tempmax");
        double temperaturaMinima = extrairDouble(diaAtual, "tempmin");
        String cidadeResolvida   = extrairString(json, "resolvedAddress");

        return new DadosClima(temperaturaAtual, temperaturaMaxima, temperaturaMinima,
                              humidade, condicaoTempo, precipitacao,
                              velocidadeVento, direcaoVento, cidadeResolvida);
    }

    private void desabilitarVerificacaoSSL() {
        try {
            TrustManager[] trustAll = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] c, String a) {}
                    public void checkServerTrusted(X509Certificate[] c, String a) {}
                }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAll, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            System.err.println("Aviso: não foi possível configurar SSL - " + e.getMessage());
        }
    }

    private String extrairBloco(String json, String chave) {
        int inicio = json.indexOf("\"" + chave + "\"");
        if (inicio == -1) return "";
        inicio = json.indexOf("{", inicio);
        int profundidade = 0, i = inicio;
        while (i < json.length()) {
            if (json.charAt(i) == '{') profundidade++;
            else if (json.charAt(i) == '}') { profundidade--; if (profundidade == 0) return json.substring(inicio, i + 1); }
            i++;
        }
        return "";
    }

    private String extrairPrimeiroBloco(String json, String chave) {
        int inicio = json.indexOf("\"" + chave + "\"");
        if (inicio == -1) return "";
        inicio = json.indexOf("{", inicio);
        int profundidade = 0, i = inicio;
        while (i < json.length()) {
            if (json.charAt(i) == '{') profundidade++;
            else if (json.charAt(i) == '}') { profundidade--; if (profundidade == 0) return json.substring(inicio, i + 1); }
            i++;
        }
        return "";
    }

    private double extrairDouble(String json, String chave) {
        int idx = json.indexOf("\"" + chave + "\"");
        if (idx == -1) return 0.0;
        int doisPontos = json.indexOf(":", idx);
        int fim = json.indexOf(",", doisPontos);
        if (fim == -1) fim = json.indexOf("}", doisPontos);
        try { return Double.parseDouble(json.substring(doisPontos + 1, fim).trim()); }
        catch (Exception e) { return 0.0; }
    }

    private String extrairString(String json, String chave) {
        int idx = json.indexOf("\"" + chave + "\"");
        if (idx == -1) return "N/A";
        int doisPontos = json.indexOf(":", idx);
        int abre  = json.indexOf("\"", doisPontos);
        int fecha = json.indexOf("\"", abre + 1);
        return json.substring(abre + 1, fecha);
    }

    private String grausParaPontoCardeal(double graus) {
        String[] pontos = {"N","NNE","NE","ENE","L","ESE","SE","SSE",
                           "S","SSO","SO","OSO","O","ONO","NO","NNO"};
        return pontos[(int) Math.round(graus / 22.5) % 16];
    }
}