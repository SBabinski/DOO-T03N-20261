/** Lancada quando um texto JSON nao pode ser interpretado. */
public class JsonException extends Exception {
    public JsonException(String mensagem) {
        super(mensagem);
    }

    public JsonException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
