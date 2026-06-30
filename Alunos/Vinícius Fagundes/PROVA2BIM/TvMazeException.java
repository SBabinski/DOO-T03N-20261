
/** Falha recuperável ao consultar ou interpretar a API TVmaze. */
public class TvMazeException extends Exception {
    private static final long serialVersionUID = 1L;

    public TvMazeException(String mensagem) {
        super(mensagem);
    }

    public TvMazeException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
