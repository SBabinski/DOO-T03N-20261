public class Calculadora {

    private double primeiroNumero;
    private String operacao;

    public void salvarOperacao(double numero, String operacao) {
        this.primeiroNumero = numero;
        this.operacao = operacao;
    }

    public double calcular(double segundoNumero) throws CalculadoraException {

        if (operacao == null || operacao.isEmpty()) {
            return segundoNumero;
        }

        double resultado;
        switch (operacao) {
            case "+": resultado = primeiroNumero + segundoNumero; break;
            case "-": resultado = primeiroNumero - segundoNumero; break;
            case "×": resultado = primeiroNumero * segundoNumero; break;
            case "÷":
                if (segundoNumero == 0) {
                    throw new CalculadoraException("Não é possível dividir por zero");
                }
                resultado = primeiroNumero / segundoNumero;
                break;
            default:
                throw new CalculadoraException("Operação inválida");
        }

        this.primeiroNumero = resultado;
        return resultado;
    }

    public double converterNumero(String texto) throws CalculadoraException {
        try {
            return Double.parseDouble(texto.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Digite apenas números");
        }
    }

    public String formatar(double resultado) {
        if (resultado == (long) resultado) {
            return String.valueOf((long) resultado);
        }

        return String.valueOf(resultado).replace(".", ",");
    }

    public void limpar() {
        primeiroNumero = 0;
        operacao = "";
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}