/**
 * Classe responsavel por fazer os calculos matematicos.
 * Separada da interface grafica para organizar melhor o codigo.
 */
public class Calculator {

    /**
     * Realiza a operacao matematica entre dois numeros.
     * Lanca CalculatorException se houver divisao por zero.
     */
    public double calculate(double a, double b, String operador)
            throws CalculatorException {

        switch (operador) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "x":
                return a * b;
            case "/":
                // Verifica divisao por zero ANTES de dividir
                if (b == 0) {
                    throw new CalculatorException(
                        CalculatorException.ErrorType.DIVISION_BY_ZERO,
                        "Divisor igual a zero"
                    );
                }
                return a / b;
            default:
                throw new CalculatorException(
                    CalculatorException.ErrorType.NO_OPERATOR_SELECTED,
                    "Nenhum operador valido"
                );
        }
    }

    /**
     * Converte texto em numero double.
     * Lanca CalculatorException se o texto nao for um numero valido.
     */
    public double parseNumber(String texto) throws CalculatorException {
        // Verifica se o campo esta vazio
        if (texto == null || texto.trim().isEmpty()) {
            throw new CalculatorException(
                CalculatorException.ErrorType.INVALID_INPUT,
                "Campo vazio"
            );
        }

        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            // NumberFormatException ocorre quando o texto nao e um numero
            throw new CalculatorException(
                CalculatorException.ErrorType.INVALID_INPUT,
                "Texto invalido: " + texto
            );
        }
    }

    /**
     * Formata o resultado: se for numero inteiro, remove o ".0"
     * Exemplo: 10.0 vira "10", mas 3.14 continua "3.14"
     */
    public String formatarResultado(double valor) {
        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}
