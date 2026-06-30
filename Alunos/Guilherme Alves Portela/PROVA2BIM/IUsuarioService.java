import java.util.List;

public interface IUsuarioService {
    // Gerenciamento do Perfil
    void inicializarUsuario(String nome);
    Usuario getUsuarioAtual();

    // Manipulação das Listas (Favoritos, Assistidas, Deseja Assistir)
    void adicionarFavorito(Serie serie) throws ExceptionManager;
    void removerFavorito(Serie serie) throws ExceptionManager;

    void adicionarAssistido(Serie serie) throws ExceptionManager;
    void removerAssistido(Serie serie) throws ExceptionManager;

    void adicionarDesejaAssistir(Serie serie) throws ExceptionManager;
    void removerDesejaAssistir(Serie serie) throws ExceptionManager;

    // Ordenação (O critério pode ser um Enum ou String, ex: "ALFABETICA", "NOTA", etc.)
    void ordenarListaFavoritos(String criterio);
    void ordenarListaAssistidos(String criterio);
    void ordenarListaDesejaAssistir(String criterio);
}