package exception;

/**
 * Exceção lançada quando o usuário realiza uma operação inválida ou fornece
 * dados incorretos (pesquisa vazia, série não encontrada, etc.).
 */
public class ExcecaoUsuario extends Exception {

    /**
     * Cria uma nova ExcecaoUsuario com mensagem descritiva.
     *
     * @param mensagem descrição do erro
     */
    public ExcecaoUsuario(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria uma nova ExcecaoUsuario com mensagem e causa raiz.
     *
     * @param mensagem descrição do erro
     * @param causa    exceção original
     */
    public ExcecaoUsuario(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
