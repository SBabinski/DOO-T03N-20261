import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carrega variaveis de configuracao a partir de um arquivo .env.
 * Procura o arquivo na pasta atual e tambem na pasta do projeto.
 */
public final class EnvLoader {

    private static Map<String, String> cache;

    private EnvLoader() {}

    /** Devolve o valor de uma chave do .env, ou o padrao informado se ausente. */
    public static String get(String chave, String padrao) {
        String valor = carregar().get(chave);
        return (valor == null || valor.isBlank()) ? padrao : valor;
    }

    private static synchronized Map<String, String> carregar() {
        if (cache != null) return cache;
        cache = new LinkedHashMap<>();

        Path path = localizarArquivo();
        if (path == null) return cache;

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty() || linha.startsWith("#")) continue;
                int eq = linha.indexOf('=');
                if (eq < 1) continue;
                String chave = linha.substring(0, eq).trim();
                String valor = linha.substring(eq + 1).trim();
                if (valor.length() >= 2 &&
                    ((valor.startsWith("\"") && valor.endsWith("\"")) ||
                     (valor.startsWith("'") && valor.endsWith("'")))) {
                    valor = valor.substring(1, valor.length() - 1);
                }
                cache.put(chave, valor);
            }
        } catch (IOException ignored) {
            // .env ausente ou ilegivel: usamos apenas os valores padrao.
        }
        return cache;
    }

    private static Path localizarArquivo() {
        Path[] candidatos = {
            Paths.get(".env"),
            Paths.get("SeriesTracker", ".env")
        };
        for (Path p : candidatos) {
            if (Files.exists(p)) return p;
        }
        return null;
    }
}
