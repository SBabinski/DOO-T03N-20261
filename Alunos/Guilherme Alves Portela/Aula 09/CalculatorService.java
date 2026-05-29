import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável pelas regras de negócio e validações da calculadora.
 * Isola a lógica matemática da interface gráfica (Swing).
 */
public class CalculatorService {
        
    // Mapa que armazena as operações associadas aos seus símbolos (+, -, ×, ÷)
    private final Map<String, MathOperation> operations = new HashMap<>();

    public CalculatorService() {
        // Registra as operações no mapa de controle
        operations.put("+", new Addition());
        operations.put("-", new Subtraction());
        operations.put("×", new Multiplication());
        operations.put("÷", new Division());
    }

    /**
     * Identifica e executa a operação escolhida pelo usuário.
     */
    public double compute(String op, double num1, double num2) throws CalculatorException {
        MathOperation operation = operations.get(op);
        if (operation == null) {
            throw new CalculatorException("Operação inválida: " + op);
        }
        return operation.calculate(num1, num2);
    }

    /**
     * Converte o texto digitado na tela em um número do tipo double.
     * Caso o texto contenha letras ou caracteres inválidos, lança nossa exceção.
     */
    public double parseInput(String input) throws CalculatorException {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            // Captura o erro nativo do Java e transforma em uma mensagem amigável
            throw new CalculatorException("Entrada inválida: '" + input + "' não é um número.");
        }
    }
}