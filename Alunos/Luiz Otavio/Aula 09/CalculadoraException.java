public class CalculadoraException extends RuntimeException {

    private final String codigoErro;

    public CalculadoraException(String mensagem, String codigoErro) {
        super(mensagem);
        this.codigoErro = codigoErro;
    }

    public String getCodigoErro() {
        return codigoErro;
    }
}
