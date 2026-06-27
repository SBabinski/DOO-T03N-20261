public class WeatherData {
    private final String cidade;
    private final double temperaturaAtual;
    private final double temperaturaMaxima;
    private final double temperaturaMinima;
    private final double umidade;
    private final String condicao;
    private final double precipitacao;
    private final double velocidadeVento;
    private final double direcaoVento;

    public WeatherData(String cidade, double temperaturaAtual, double temperaturaMaxima,
                       double temperaturaMinima, double umidade, String condicao,
                       double precipitacao, double velocidadeVento, double direcaoVento) {
        this.cidade = cidade;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public void imprimirRelatorio() {
        System.out.println();
        System.out.println("========== INFORMAÇÕES DO CLIMA ==========");
        System.out.println("Cidade/local: " + cidade);
        System.out.printf("Temperatura atual: %.1f °C%n", temperaturaAtual);
        System.out.printf("Máxima do dia: %.1f °C%n", temperaturaMaxima);
        System.out.printf("Mínima do dia: %.1f °C%n", temperaturaMinima);
        System.out.printf("Umidade do ar: %.0f%%%n", umidade);
        System.out.println("Condição do tempo: " + condicao);
        System.out.printf("Precipitação: %.1f mm%n", precipitacao);
        System.out.printf("Velocidade do vento: %.1f km/h%n", velocidadeVento);
        System.out.printf("Direção do vento: %.0f° (%s)%n", direcaoVento, converterDirecaoVento(direcaoVento));
        System.out.println("==========================================");
    }

    private String converterDirecaoVento(double graus) {
        if (Double.isNaN(graus)) {
            return "não informada";
        }

        String[] direcoes = {"N", "NE", "L", "SE", "S", "SO", "O", "NO"};
        int indice = (int) Math.round(graus / 45.0) % 8;

        return direcoes[indice];
    }
}