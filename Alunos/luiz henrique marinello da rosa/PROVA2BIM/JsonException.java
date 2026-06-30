// meu erro proprio pra problemas de JSON (ler ou montar).
// ter um erro so meu deixa claro de onde veio o problema.

public class JsonException extends Exception {

    public JsonException(String mensagem) {
        super(mensagem);
    }

    public JsonException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
