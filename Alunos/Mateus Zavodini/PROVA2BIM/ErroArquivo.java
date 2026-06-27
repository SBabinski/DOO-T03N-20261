

public class ErroArquivo extends Exception {

    public ErroArquivo(String mensagem) {
        super(mensagem);
    }

    public ErroArquivo(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

//erro ao salvar/ler o arquivo dados.json (sem permissão, arquivo corrompido,).