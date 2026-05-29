
// Interface que define o comportamento padrão de qualquer operação matemática.
// Graças ao SOLID (OCP), novas operações podem ser criadas sem mexer no código antigo.

public interface MathOperation {
    // Método que calcula o resultado. Pode lançar nossa exceção customizada se algo der errado.
    double calculate(double num1, double num2) throws CalculatorException;
}

// Implementação da operação de Adição.

class Addition implements MathOperation {
    @Override
    public double calculate(double num1, double num2) {
        return num1 + num2;
    }
}

// Implementação da operação de Subtração.

class Subtraction implements MathOperation {
    @Override
    public double calculate(double num1, double num2) {
        return num1 - num2;
    }
}

// Implementação da operação de Multiplicação.

class Multiplication implements MathOperation {
    @Override
    public double calculate(double num1, double num2) {
        return num1 * num2;
    }
}

// Implementação da operação de Divisão.
// Contém a regra de negócio que valida a divisão por zero.
 
class Division implements MathOperation {
    @Override
    public double calculate(double num1, double num2) throws CalculatorException {
        // Validação: Lança o erro se o usuário tentar dividir por zero
        if (num2 == 0) {
            throw new CalculatorException("Erro: Não é possível dividir por zero.");
        }
        return num1 / num2;
    }
}