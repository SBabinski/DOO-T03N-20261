import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Le e grava os dados do usuario em um arquivo JSON, garantindo a persistencia
 * entre execucoes do programa. Todas as falhas de E/S sao tratadas para que o
 * sistema nao quebre caso o arquivo esteja corrompido ou inacessivel.
 */
public class PersistenceService {

    private final Path arquivo;

    public PersistenceService() {
        String nome = EnvLoader.get("ARQUIVO_DADOS", "dados_usuario.json");
        this.arquivo = Paths.get(nome);
    }

    public boolean existeDados() {
        return Files.exists(arquivo);
    }

    /**
     * Carrega o usuario salvo. Se o arquivo nao existir, devolve null.
     * Se existir mas estiver corrompido, lanca AppException com mensagem clara.
     */
    public Usuario carregar() throws AppException {
        if (!Files.exists(arquivo)) return null;
        try {
            String conteudo = Files.readString(arquivo, StandardCharsets.UTF_8);
            if (conteudo.isBlank()) return null;
            Object raiz = Json.parse(conteudo);
            if (!(raiz instanceof Map)) {
                throw new AppException("O arquivo de dados esta em formato invalido.");
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> mapa = (Map<String, Object>) raiz;
            return Usuario.fromMap(mapa);
        } catch (JsonException e) {
            throw new AppException("O arquivo de dados esta corrompido e nao pode ser lido.", e);
        } catch (IOException e) {
            throw new AppException("Nao foi possivel ler o arquivo de dados: " + e.getMessage(), e);
        }
    }

    /** Grava o usuario no arquivo JSON. */
    public void salvar(Usuario usuario) throws AppException {
        if (usuario == null) return;
        try {
            String json = Json.stringify(usuario.toMap());
            Path tmp = arquivo.resolveSibling(arquivo.getFileName() + ".tmp");
            Files.writeString(tmp, json, StandardCharsets.UTF_8);
            // Gravacao atomica: escreve em arquivo temporario e troca, evitando
            // perda de dados caso o programa seja encerrado durante a escrita.
            try {
                Files.move(tmp, arquivo,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException atomicFalhou) {
                Files.move(tmp, arquivo, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new AppException("Nao foi possivel salvar os dados: " + e.getMessage(), e);
        }
    }
}
