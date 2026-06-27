/**
 * Excecao de dominio da aplicacao. Carrega sempre uma mensagem amigavel,
 * pronta para ser exibida ao usuario, evitando que o programa feche
 * inesperadamente diante de erros previsiveis (rede, API, arquivos, etc).
 */
public class AppException extends Exception {

    public AppException(String mensagem) {
        super(mensagem);
    }

    public AppException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
