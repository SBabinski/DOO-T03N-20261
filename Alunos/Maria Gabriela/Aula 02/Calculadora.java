public class Calculadora {
    public double  calcularPrecoTotal(double precoUnitario, int quantidade) {
        return precoUnitario * quantidade;
    }

    public double calcularTroco(double valorPago, double precoTotal) {
        return valorPago - precoTotal;
    }
}
