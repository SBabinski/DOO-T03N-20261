package obj;
public class Calculadora {

    private static final double descontoPercentual = 0.05;

    public double calcularValorTotal(int quantidade, double precoUnitario) {
        double valorBruto = quantidade * precoUnitario;

        if (quantidade > 10) {
            double desconto = valorBruto * descontoPercentual;
            return valorBruto - desconto;
        }

        return valorBruto;
    }

    public double calcularDesconto(int quantidade, double precoUnitario) {
        if (quantidade > 10) {
            return quantidade * precoUnitario * descontoPercentual;
        }
        return 0;
    }

    public double calcularTroco(double valorPago, double valorCompra) {

        return valorPago - valorCompra;
    }
}