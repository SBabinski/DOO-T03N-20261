public class CalculatorException extends Exception {
    public CalculatorException(String message) { // construtor que recebe a mensagem de erro
        super(message); // chama o construtor da classe pai (Exception) para armazenar a mensagem de erro
    }
}
