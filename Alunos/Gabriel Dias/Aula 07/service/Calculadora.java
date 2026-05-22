package myplant.service;

/**
 * Serviço responsável pelos cálculos de venda.
 * Encapsula regras de negócio: preço total, troco e desconto.
 */
public class Calculadora {

    private static final double DESCONTO_PERCENTUAL = 0.05;
    private static final int LIMITE_DESCONTO = 10;

    /**
     * Calcula o preço total sem desconto.
     */
    public double calcularPrecoTotal(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }

    /**
     * Calcula o preço total aplicando desconto de 5% quando quantidade > 10.
     */
    public double calcularPrecoComDesconto(int quantidade, double precoUnitario) {
        double total = calcularPrecoTotal(quantidade, precoUnitario);
        if (quantidade > LIMITE_DESCONTO) {
            double desconto = total * DESCONTO_PERCENTUAL;
            System.out.printf("✅ Desconto especial de 5%% aplicado: -R$ %.2f%n", desconto);
            return total - desconto;
        }
        return total;
    }

    /**
     * Calcula o valor do desconto para um total.
     */
    public double calcularValorDesconto(int quantidade, double precoUnitario) {
        if (quantidade > LIMITE_DESCONTO) {
            return calcularPrecoTotal(quantidade, precoUnitario) * DESCONTO_PERCENTUAL;
        }
        return 0;
    }

    /**
     * Calcula o troco.
     */
    public double calcularTroco(double valorPago, double valorTotal) {
        return valorPago - valorTotal;
    }
}
