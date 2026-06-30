// meu erro proprio pra falha ao salvar/ler o arquivo no disco.
// assim a interface trata sem o programa fechar.

public class PersistenciaException extends Exception {

    public PersistenciaException(String mensagem) {
        super(mensagem);
    }

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
