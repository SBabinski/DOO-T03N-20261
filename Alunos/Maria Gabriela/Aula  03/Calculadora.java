public class Calculadora {

    public double calcularPrecoTotal(double precoUnitario, int quantidade) {
        double total = precoUnitario * quantidade;

        if (quantidade > 10) {
            total = total * 0.95; 
        }

        return total;
    }

    public double calcularTroco(double valorPago, double precoTotal) {
        return valorPago - precoTotal;
    }
}
