package exception;

/**
 * Exceção lançada quando ocorre falha ao ler ou gravar dados no arquivo JSON.
 */
public class ExcecaoPersistencia extends Exception {

    public ExcecaoPersistencia(String mensagem) {
        super(mensagem);
    }

    public ExcecaoPersistencia(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
