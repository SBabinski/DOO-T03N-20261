package calculator;

public class CalculatorException extends Exception {

    public enum TipoErro {
        DIVISAO_POR_ZERO,
        ENTRADA_INVALIDA,
        OPERADOR_NAO_SELECIONADO,
        ESTOURO_DE_VALOR
    }

    private final TipoErro tipoErro;

    public CalculatorException(TipoErro tipoErro, String mensagem) {
        super(mensagem);
        this.tipoErro = tipoErro;
    }

    public TipoErro getTipoErro() {
        return tipoErro;
    }
}
