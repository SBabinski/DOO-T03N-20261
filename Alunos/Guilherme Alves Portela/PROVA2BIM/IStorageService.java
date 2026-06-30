/*
    Service interface para storage, define os métodos abstratos para salvar e carregar os dados do usuário.
*/

public interface IStorageService {
    /**
     * Salva todos os dados do usuário atual em um arquivo JSON.
     * @param usuario O objeto usuário com suas respectivas listas.
     * @throws ExceptionManager Se ocorrer um erro de escrita ou IO.
     */
    void salvarDados(Usuario usuario) throws ExceptionManager;

    /**
     * Carrega os dados do usuário a partir do arquivo JSON local.
     * @return O objeto Usuario populado, ou null se o arquivo não existir.
     * @throws ExceptionManager Se o arquivo estiver corrompido ou houver erro de leitura.
     */
    Usuario carregarDados() throws ExceptionManager;
}
