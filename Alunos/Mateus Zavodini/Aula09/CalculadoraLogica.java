public class CalculadoraLogica {

    public double parsearNumero(String valor, String nomeCampo) throws CalculadoraCalc {
        if (valor == null || valor.trim().isEmpty()) {
            throw new CalculadoraCalc(
                CalculadoraCalc.TipoErro.CAMPO_VAZIO,
                "O campo '" + nomeCampo + "' não pode estar vazio."
            );
        }

        String valorLimpo = valor.trim().replace(",", ".");

        if (!valorLimpo.matches("-?\\d+(\\.\\d+)?")) {
            throw new CalculadoraCalc(
                CalculadoraCalc.TipoErro.ENTRADA_INVALIDA,
                "O valor '" + valor.trim() + "' em '" + nomeCampo + "' não é um número válido.\n"
                + "Use apenas dígitos e ponto decimal (ex: 3.14)."
            );
        }

        try {
            double numero = Double.parseDouble(valorLimpo);
            if (Double.isInfinite(numero)) {
                throw new CalculadoraCalc(
                    CalculadoraCalc.TipoErro.OVERFLOW,
                    "O número inserido é grande demais para ser processado."
                );
            }
            return numero;
        } catch (NumberFormatException e) {
            throw new CalculadoraCalc(
                CalculadoraCalc.TipoErro.ENTRADA_INVALIDA,
                "Não foi possível interpretar '" + valor.trim() + "' como número.",
                e
            );
        }
    }

    public double calcular(double num1, double num2, String operacao) throws CalculadoraCalc {
        double resultado;

        switch (operacao) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "×":
            case "*":
                resultado = num1 * num2;
                break;
            case "÷":
            case "/":
                if (num2 == 0) {
                    throw new CalculadoraCalc(
                        CalculadoraCalc.TipoErro.DIVISAO_POR_ZERO,
                        "Não é possível dividir " + formatarNumero(num1) + " por zero.\n"
                        + "O divisor deve ser diferente de zero."
                    );
                }
                resultado = num1 / num2;
                break;
            default:
                throw new CalculadoraCalc(
                    CalculadoraCalc.TipoErro.OPERACAO_INVALIDA,
                    "A operação '" + operacao + "' não é suportada."
                );
        }

        if (Double.isInfinite(resultado) || Double.isNaN(resultado)) {
            throw new CalculadoraCalc(
                CalculadoraCalc.TipoErro.OVERFLOW,
                "O resultado excede os limites numéricos suportados."
            );
        }

        return resultado;
    }

    public String formatarNumero(double numero) {
        if (numero == Math.floor(numero) && !Double.isInfinite(numero)) {
            return String.valueOf((long) numero);
        }
        String formatado = String.format("%.10f", numero);
        formatado = formatado.replaceAll("0+$", "").replaceAll("\\.$", "");
        return formatado;
    }
}