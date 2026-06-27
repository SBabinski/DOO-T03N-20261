public class ErroApi extends Exception {

    public ErroApi(String mensagem) {
        super(mensagem);
    }

    public ErroApi(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

//erro de conexão com a internet/API TVMaze (sem net, servidor fora do ar,).