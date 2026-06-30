/*
    Interface para API, define o contrato de busca de séries, aplicando o conceito de abstração SOLID

*/
import java.util.List;

public interface ITvApiClient {
    /**
     * Realiza uma busca na API do TVMaze pelo nome fornecido.
     * @param nome O termo de busca para a série.
     * @return Uma lista de objetos Serie encontrados.
     * @throws ExceptionManager Se houver falha na rede, timeout ou erro de parsing do JSON.
     */
    List<Serie> buscarSeriesPorNome(String nome) throws ExceptionManager;
}
