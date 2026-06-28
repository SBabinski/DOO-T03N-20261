public class Calculator {

    public double calcular(double num1, double num2, String operacao)
            throws CalculatorException {

        switch (operacao) {

            case "+":
                return num1 + num2;

            case "-":
                return num1 - num2;

            case "*":
                return num1 * num2;

            case "/":

                if (num2 == 0) {
                    throw new CalculatorException("Erro: divisão por zero!");
                }

                return num1 / num2;

            default:
                throw new CalculatorException("Operação inválida!");
        }
    }
     public String formatarResultado(double valor) {
        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}