public class Calculator {
    Double valorArmazenado = null;
     String operador = null;

    public void limpar() {
        valorArmazenado = null;
        operador = null;
    }

    public void definirValorArmazenadoDeString(String texto) throws CalculatorException {
        try {
            valorArmazenado = Double.parseDouble(texto);
        } catch (NumberFormatException e) { // se a string não for um número válido, lança uma exceção personalizada
            throw new CalculatorException("Entrada inválida: use apenas números e ponto decimal.");
        }
    }

    public double calcular(double esquerdo, double direito, String operacao) throws CalculatorException {
        switch (operacao) {
            case "+":
                return esquerdo + direito;
            case "-":
                return esquerdo - direito;
            case "×":
            case "*":
                return esquerdo * direito;
            case "÷":
            case "/":
                if (direito == 0) throw new CalculatorException("Divisão por zero não permitida.");
                return esquerdo / direito; 
            default:
                throw new CalculatorException("Operação desconhecida: " + operacao); 
        }
    }

    public static String formatarDouble(double v) { // formata para não mostrar .0 em inteiros
        if (Double.isNaN(v) || Double.isInfinite(v)) return String.valueOf(v); // NaN e infinito ficam como estão
        if (v == (long) v) return String.format("%d", (long) v); // se for inteiro, mostra sem decimal
        return String.valueOf(v); // caso contrário, mostra o double normalmente
    }
}
