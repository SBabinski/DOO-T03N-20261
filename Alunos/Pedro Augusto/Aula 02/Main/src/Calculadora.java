public class Calculadora {
    public static float calcularPreco(int qtdPlantas, float valorPlanta) {
        return qtdPlantas * valorPlanta;
    }

    public static float calcularTroco(float valorPagoCliente, float valorTotalCompra) {
        return valorPagoCliente - valorTotalCompra;
    }
}