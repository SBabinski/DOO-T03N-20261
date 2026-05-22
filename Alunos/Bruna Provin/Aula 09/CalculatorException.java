
public class CalculatorException extends Exception {

    // Enum = lista fixa de categorias de erro possiveis
    public enum ErrorType {
        DIVISION_BY_ZERO,
        INVALID_INPUT,
        NO_OPERATOR_SELECTED
    }

    // Guarda qual tipo de erro aconteceu
    private ErrorType errorType;

    // Construtor: recebe o tipo de erro e uma mensagem tecnica
    public CalculatorException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    // Retorna uma mensagem clara e amigavel para o usuario
    public String getFriendlyMessage() {
        switch (errorType) {
            case DIVISION_BY_ZERO:
                return "Erro: Nao e possivel dividir por zero!";
            case INVALID_INPUT:
                return "Erro: Digite apenas numeros validos!";
            case NO_OPERATOR_SELECTED:
                return "Erro: Selecione uma operacao (+, -, x, /)";
            default:
                return "Erro desconhecido!";
        }
    }
}
