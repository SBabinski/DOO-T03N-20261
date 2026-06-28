

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Carrega a chave de API a partir de:
 *  1. Variável de ambiente  VISUALCROSSING_API_KEY  (recomendado em produção)
 *  2. Arquivo .env na raiz do projeto                (conveniente em desenvolvimento)
 *
 * O arquivo .env deve conter uma linha no formato:
 *   VISUALCROSSING_API_KEY=sua_chave_aqui
 *
 * NUNCA versione o arquivo .env ou a chave em texto simples no código-fonte.
 * Adicione .env ao .gitignore.
 */
public class ApiKeyProvider {

    private static final String ENV_VAR = "VISUALCROSSING_API_KEY";
    private static final String DOT_ENV  = ".env";

    private ApiKeyProvider() {}

    public static String getKey() {
        String fromEnv = System.getenv(ENV_VAR);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv.trim();
        }
        return readFromDotEnv();
    }

    private static String readFromDotEnv() {
        Path path = Paths.get(DOT_ENV);
        if (!Files.exists(path)) return null;

        try {
            for (String line : Files.readAllLines(path)) {
                line = line.trim();
                if (line.startsWith(ENV_VAR + "=")) {
                    String value = line.substring(ENV_VAR.length() + 1).trim();
                    return value.isEmpty() ? null : value;
                }
            }
        } catch (IOException e) {
            System.err.println("Aviso: não foi possível ler o arquivo .env — " + e.getMessage());
        }
        return null;
    }
}
