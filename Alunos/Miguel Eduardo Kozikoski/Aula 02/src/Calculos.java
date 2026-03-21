public class Calculos {

    public static int somar(int valorplantas , int totalplantas) {
        int resultado = valorplantas * totalplantas;

        if (totalplantas >= 10) {
            return (int ) (resultado * 0.95);
        } else {
            return resultado;
        }
    }

    public static int resultado_troco(int Valor_pago , int Valor_total){
        return Valor_pago - Valor_total;
    }
}
