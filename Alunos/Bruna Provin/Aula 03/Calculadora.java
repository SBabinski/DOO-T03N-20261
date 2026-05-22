public class Calculadora {

    private static final double DESCONTO_PERCENTUAL = 0.05;

    public double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }

    public double calcularDesconto(int quantidade, double valorTotal) {
        if (quantidade > 10) {
            return valorTotal * DESCONTO_PERCENTUAL;
        }
        return 0;
    }

    public double calcularValorFinal(double valorTotal, double desconto) {
        return valorTotal - desconto;
    }

    public double calcularTroco(double valorPago, double valorFinal) {
        return valorPago - valorFinal;
    }
}