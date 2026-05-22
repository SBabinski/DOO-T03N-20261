public class Calculadora {

    public double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }

    public double calcularTroco(double valorPago, double valorTotal) {
        return valorPago - valorTotal;
    }
}