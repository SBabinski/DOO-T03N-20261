package exception;

/**
 * Exceção lançada quando ocorre um erro na comunicação com a API do TVMaze.
 */
public class ExcecaoApi extends Exception {

    /**
     * Cria uma nova ExcecaoApi com mensagem descritiva.
     *
     * @param mensagem descrição do erro
     */
    public ExcecaoApi(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria uma nova ExcecaoApi com mensagem e causa raiz.
     *
     * @param mensagem descrição do erro
     * @param causa    exceção original
     */
    public ExcecaoApi(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
