

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servico responsavel por buscar dados climaticos na Visual Crossing API.
 */
public class WeatherService {

    private static final String API_KEY = "NAWBVJ3YQKEDJDE27A8REGPLV";
    private static final String BASE_URL =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public WeatherData buscarClima(String cidade) {
        try {

            // esse encoder é para poder inserir a cidade em forma de codigo, para poder se encaixar na url
       String cidadeCodificada =
    URLEncoder.encode(cidade, "UTF-8");

            String urlStr = BASE_URL + cidadeCodificada
                    + "?unitGroup=metric&include=current&key=" + API_KEY
                    + "&contentType=json";

            URL url = new URL(urlStr);// transforma a string num endereço Java

            //esse aqui é o metodo nativo de java para "navegar em endereços https"
            HttpURLConnection conexao = (HttpURLConnection)
                    url.openConnection();// abre a conexao cm o server

            conexao.setRequestMethod("GET");//metodo de request que apenas busca dados e traz

            conexao.setConnectTimeout(5000);// aqui sao os timeouts pra se a api nao retornar ele nao fica pra sempre esperando 5000=5 segundos

            conexao.setReadTimeout(5000);//esse é para se nao ler e o de cima para se nao conectar

            //CódigoSignificado200OK — deu certo400Bad Request — URL mal formada401Unauthorized — API Key inválida429Too Many Requests — limite do Free Tier atingido500Server Error — problema no servidor deles
            int codigoResposta = conexao.getResponseCode();

            if (codigoResposta != 200) {
                System.err.println("Erro na API. Código HTTP: " + codigoResposta);
                return null;
            } // esse print aqui é pra ele dizer o tipo de erro que aconteceu caso aconteça, *se atentar a tabela*

            // agora eu preciso ler o que retorna da api
            BufferedReader leitor = new BufferedReader(  //bufferedreader é o leitor que le uma linha por vez ao inves de um carac por vez
                    new InputStreamReader(conexao.getInputStream()));

            StringBuilder resposta = new StringBuilder(); // isso aq é para conforme o reader retorna ele le e bota em resposta, por isso builder
            String linha; // guarda o conteudo de cada linha temporariamente

            // preciso de um laço que mande ler uma por vez enquanto tiver linha
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);//esse append significa acrescentar ao final basicamente
            }

            leitor.close();// isso é so uma boa pratica, pra tipo evitar de problemas ou gasto de memoria

            return parsearResposta(cidade, resposta.toString());

        } catch (Exception e) {
            System.err.println("Erro ao buscar clima: " + e.getMessage());
            return null;
        }
    }

    // agora o que vem do JSON ta numa string enorme, e como eu quero dados especificos dela eu faço um "buscador"m ele vai achar o tipo do dado ,
    // ai ele le o que tiver dps do tipo e antes da virgula     //."tempmax": 24.5, "tempmin"...
    //  ↑         ↑    ↑
    //achou     início  fim
    private double extrairDouble(String json, String chave) {

        // passo 1 acha aonde a chave começa
        int inicio = json.indexOf(chave);

        // se nao achar o campo ele retona zero
        if (inicio == -1) {
            return 0.0;
        }

        // passo 2: pula o nome da chave pra chegar no valor
        inicio += chave.length();

        // passo 3: acha onde o valor termina (na vírgula ou na chave })
        int fim = json.indexOf(",", inicio);
        if (fim == -1) fim = json.indexOf("}", inicio);

        // passo 4: recorta o valor e converte pra número
        return Double.parseDouble(json.substring(inicio, fim).trim());
    }

    //Para campos de texto como "conditions":"Partially cloudy" funciona igual, mas em vez de parar na vírgula, para nas aspas de fechamento:
    private String extrairString(String json, String chave) {

        // chave aqui inclui as aspas do valor: "conditions":"
        int inicio = json.indexOf(chave);
        if (inicio == -1) return "N/A";

        // pula a chave pra chegar na primeira letra do valor
        inicio += chave.length();

        // o valor termina nas próximas aspas
        int fim = json.indexOf("\"", inicio);

        return json.substring(inicio, fim);
    }

    // aq eu fui genio, ele pega a direção em graus do vento que a api da e divide por 45, transformando ela em direções da rosa dos ventos
    private String grausParaDirecao(double graus) {

        String[] direcoes = {"Norte", "Nordeste", "Leste", "Sudeste",
                "Sul", "Sudoeste", "Oeste", "Noroeste"};

        int indice = (int) ((graus + 22.5) / 45) % 8;
        return direcoes[indice];
    }

    private WeatherData parsearResposta(String cidade, String json) {

        double tempAtual = extrairDouble(json, "\"temp\":");
        double tempMax = extrairDouble(json, "\"tempmax\":");
        double tempMin = extrairDouble(json, "\"tempmin\":");
        double umidade = extrairDouble(json, "\"humidity\":");
        double precipitacao = extrairDouble(json, "\"precip\":");
        double vento = extrairDouble(json, "\"windspeed\":");
        double direcaoGrau = extrairDouble(json, "\"winddir\":");
        String condicao = extrairString(json, "\"conditions\":\"");

        return new WeatherData(cidade, tempAtual, tempMax, tempMin,
                umidade, condicao, precipitacao, vento,
                grausParaDirecao(direcaoGrau));
    }
}