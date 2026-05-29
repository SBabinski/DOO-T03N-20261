package calculator;

public class CalculatorEngine {

    
    public double calcular(double primeiroNumero, double segundoNumero, String operador) throws CalculatorException {
        switch (operador) {
            case "+":
                return verificarEstouro(primeiroNumero + segundoNumero);
            case "-":
                return verificarEstouro(primeiroNumero - segundoNumero);
            case "×":
                return verificarEstouro(primeiroNumero * segundoNumero);
            case "÷":
                if (segundoNumero == 0) {
                    throw new CalculatorException(
                        CalculatorException.TipoErro.DIVISAO_POR_ZERO,
                        "Divisão por zero detectada!\nRedirecionando para o Atacadão..."
                    );
                }
                return verificarEstouro(primeiroNumero / segundoNumero);
            default:
                throw new CalculatorException(
                    CalculatorException.TipoErro.OPERADOR_NAO_SELECIONADO,
                    "Nenhuma operação foi selecionada."
                );
        }
    }

    
    public double converterEntrada(String entrada) throws CalculatorException {
        if (entrada == null || entrada.isEmpty() || entrada.equals("-")) {
            throw new CalculatorException(
                CalculatorException.TipoErro.ENTRADA_INVALIDA,
                "Entrada inválida: o campo está vazio."
            );
        }
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            throw new CalculatorException(
                CalculatorException.TipoErro.ENTRADA_INVALIDA,
                "Entrada inválida: \"" + entrada + "\" não é um número."
            );
        }
    }

    
    private double verificarEstouro(double resultado) throws CalculatorException {
        if (Double.isInfinite(resultado) || Double.isNaN(resultado)) {
            throw new CalculatorException(
                CalculatorException.TipoErro.ESTOURO_DE_VALOR,
                "Resultado fora do intervalo suportado pela calculadora."
            );
        }
        return resultado;
    }
}
