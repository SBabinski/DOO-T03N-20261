package persistence;

import exception.ExcecaoPersistencia;
import model.Usuario;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Repositório responsável por carregar e salvar os dados do usuário
 * em um arquivo JSON local ({@value #NOME_ARQUIVO}).
 *
 * <p>O arquivo é gravado na mesma pasta de onde a aplicação é executada.
 * Em caso de falha na gravação, é mantido um arquivo de backup
 * ({@value #NOME_BACKUP}) com a última versão válida.</p>
 */
public class RepositorioUsuario {

    private static final String NOME_ARQUIVO = "dados_usuario.json";
    private static final String NOME_BACKUP  = "dados_usuario.bak.json";

    private static final DateTimeFormatter FORMATADOR_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final SerializadorJson serializador;
    private LocalDateTime ultimaSalvagem;

    /**
     * Cria o repositório com um serializador padrão.
     */
    public RepositorioUsuario() {
        this.serializador = new SerializadorJson();
    }

    // -------------------------------------------------------------------------
    // Leitura
    // -------------------------------------------------------------------------

    /**
     * Verifica se existe um arquivo de dados salvo.
     *
     * @return {@code true} se o arquivo existir e não estiver vazio
     */
    public boolean existemDadosSalvos() {
        File arquivo = new File(NOME_ARQUIVO);
        return arquivo.exists() && arquivo.length() > 0;
    }

    /**
     * Carrega o usuário a partir do arquivo JSON.
     *
     * @return usuário reconstituído
     * @throws ExcecaoPersistencia se o arquivo não existir ou estiver corrompido
     */
    public Usuario carregar() throws ExcecaoPersistencia {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            throw new ExcecaoPersistencia(
                    "Arquivo de dados não encontrado: " + arquivo.getAbsolutePath());
        }
        try {
            String json = Files.readString(arquivo.toPath(), StandardCharsets.UTF_8);
            return serializador.desserializar(json);
        } catch (IOException e) {
            throw new ExcecaoPersistencia(
                    "Erro ao ler o arquivo de dados: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new ExcecaoPersistencia(
                    "Arquivo de dados corrompido: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // Gravação
    // -------------------------------------------------------------------------

    /**
     * Salva o usuário no arquivo JSON.
     * Antes de sobrescrever, copia o arquivo atual para o backup.
     *
     * @param usuario usuário a salvar
     * @throws ExcecaoPersistencia se ocorrer erro na gravação
     */
    public void salvar(Usuario usuario) throws ExcecaoPersistencia {
        try {
            String json = serializador.serializar(usuario);
            Path caminho = Paths.get(NOME_ARQUIVO);

            // Faz backup da versão anterior antes de sobrescrever
            if (Files.exists(caminho)) {
                Files.copy(caminho, Paths.get(NOME_BACKUP),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // Grava de forma atômica: escreve em temp e renomeia
            Path temp = Paths.get(NOME_ARQUIVO + ".tmp");
            Files.writeString(temp, json, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.move(temp, caminho, StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);

            ultimaSalvagem = LocalDateTime.now();

        } catch (IOException e) {
            throw new ExcecaoPersistencia(
                    "Erro ao salvar os dados: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna a data/hora da última salvagem bem-sucedida formatada,
     * ou {@code null} se ainda não houve nenhuma nesta sessão.
     *
     * @return string formatada ou null
     */
    public String obterTextoUltimaSalvagem() {
        if (ultimaSalvagem == null) return null;
        return ultimaSalvagem.format(FORMATADOR_DATA);
    }

    /**
     * Retorna o caminho absoluto do arquivo de dados.
     *
     * @return caminho como string
     */
    public String obterCaminhoArquivo() {
        return new File(NOME_ARQUIVO).getAbsolutePath();
    }
}
