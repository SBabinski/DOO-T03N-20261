package exception;

/**
 * Exceção lançada quando a comunicação com a API falha por problema de rede —
 * sem conexão, DNS não resolvido, timeout ou bloqueio de firewall.
 *
 * <p>É uma especialização de {@link ExcecaoApi}: todo código que já captura
 * {@code ExcecaoApi} continua funcionando sem alteração. Quando se quiser
 * dar um tratamento diferenciado (mensagem de "verifique sua conexão"),
 * basta capturar {@code ExcecaoRede} antes de {@code ExcecaoApi}.</p>
 */
public class ExcecaoRede extends ExcecaoApi {

    /**
     * Cria uma ExcecaoRede com mensagem orientada ao usuário.
     *
     * @param mensagem descrição do problema de rede
     */
    public ExcecaoRede(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria uma ExcecaoRede preservando a causa raiz original.
     *
     * @param mensagem descrição do problema de rede
     * @param causa    exceção original lançada pela JVM/HTTP client
     */
    public ExcecaoRede(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
