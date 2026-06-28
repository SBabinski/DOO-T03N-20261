package clima;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {

    private static final String API_KEY = "Sua chave api aqui";

    public static String buscarClima(String cidade) throws Exception {

        cidade = cidade.replace(" ", "%20");

        String endereco =
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
                        + cidade
                        + "?unitGroup=metric&key="
                        + API_KEY
                        + "&contentType=json";

        URL url = new URL(endereco);

        HttpURLConnection conexao =
                (HttpURLConnection) url.openConnection();

        conexao.setRequestMethod("GET");

        BufferedReader leitor =
                new BufferedReader(
                        new InputStreamReader(
                                conexao.getInputStream()));

        StringBuilder resposta = new StringBuilder();

        String linha;

        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }

        leitor.close();

        return resposta.toString();
    }
} 