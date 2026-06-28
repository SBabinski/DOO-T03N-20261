package config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por carregar as configurações do arquivo .env.
 */
public class Configuracao {

    private static final String CAMINHO_ENV = ".env";
    private static Map<String, String> propriedades = new HashMap<>();
    private static boolean carregado = false;

    /**
     * Carrega as propriedades do arquivo .env.
     */
    private static void carregar() {
        if (carregado) return;
        try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ENV))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty() || linha.startsWith("#")) continue;
                int posicaoIgual = linha.indexOf('=');
                if (posicaoIgual > 0) {
                    String chave = linha.substring(0, posicaoIgual).trim();
                    String valor = linha.substring(posicaoIgual + 1).trim();
                    propriedades.put(chave, valor);
                }
            }
            carregado = true;
        } catch (IOException e) {
            // Se não encontrar o arquivo .env, a aplicação continua sem chave
            carregado = true;
        }
    }

    /**
     * Retorna o valor de uma propriedade pelo nome da chave.
     *
     * @param chave nome da propriedade
     * @return valor da propriedade ou string vazia se não encontrada
     */
    public static String obterPropriedade(String chave) {
        carregar();
        return propriedades.getOrDefault(chave, "");
    }

    /**
     * Retorna a chave da API do TVMaze.
     *
     * @return chave da API
     */
    public static String obterChaveApi() {
        return obterPropriedade("TVMAZE_API_KEY");
    }
}
