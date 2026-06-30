// meu erro proprio pra quando a API falha (sem net, timeout, erro http).
// separado pra interface mostrar um aviso e o programa nao fechar.

public class ApiException extends Exception {

    public ApiException(String mensagem) {
        super(mensagem);
    }

    public ApiException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
