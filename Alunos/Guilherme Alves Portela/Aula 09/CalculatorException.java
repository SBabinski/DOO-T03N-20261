
//Exceção customizada para centralizar e identificar os erros da calculadora.

public class CalculatorException extends Exception {
    
    // Construtor que recebe a mensagem de erro explicativa
    public CalculatorException(String message) {
        super(message);
    }
}