public class CalculadoraCalc extends Exception {

    public enum TipoErro {
        DIVISAO_POR_ZERO("Divisão por Zero"),
        ENTRADA_INVALIDA("Entrada Inválida"),
        OVERFLOW("Estouro Numérico"),
        OPERACAO_INVALIDA("Operação Inválida"),
        CAMPO_VAZIO("Campo Vazio");

        private final String descricao;

        TipoErro(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private final TipoErro tipoErro;

    public CalculadoraCalc(TipoErro tipoErro, String mensagem) {
        super(mensagem);
        this.tipoErro = tipoErro;
    }

    public CalculadoraCalc(TipoErro tipoErro, String mensagem, Throwable causa) {
        super(mensagem, causa);
        this.tipoErro = tipoErro;
    }

    public TipoErro getTipoErro() {
        return tipoErro;
    }

    public String getMensagemAmigavel() {
        return "[" + tipoErro.getDescricao() + "] " + getMessage();
    }

    @Override
    public String toString() {
        return "CalculadoraException{tipo=" + tipoErro + ", mensagem='" + getMessage() + "'}";
    }
}