public class WeatherData {

    private double temperaturaAtual;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double velocidadeVento;
    private double direcaoVento;

    public WeatherData(
            double temperaturaAtual,
            double temperaturaMaxima,
            double temperaturaMinima,
            double umidade,
            String condicao,
            double precipitacao,
            double velocidadeVento,
            double direcaoVento) {

        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public void mostrarDados() {

        System.out.println();
        System.out.println("CLIMA DA CIDADE");
        System.out.println("-------------------------");
        System.out.println("Temperatura atual: " + temperaturaAtual + " °C");
        System.out.println("Temperatura maxima: " + temperaturaMaxima + " °C");
        System.out.println("Temperatura minima: " + temperaturaMinima + " °C");
        System.out.println("Umidade: " + umidade + "%");
        System.out.println("Condicao: " + condicao);
        System.out.println("Precipitacao: " + precipitacao + " mm");
        System.out.println("Velocidade do vento: " + velocidadeVento + " km/h");
        System.out.println("Direcao do vento: " + direcaoVento + " graus");
    }
}