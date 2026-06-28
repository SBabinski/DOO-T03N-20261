
/**
 * Modelo que representa os dados climaticos retornados pela API.
 */
public class WeatherData {

    private String cidade;
    private double temperaturaAtual;
    private double tempMaxima;
    private double tempMinima;
    private double umidade;
    private String condicao;
    private double precipitacao;
    private double velocidadeVento;
    private String direcaoVento;

    public WeatherData(String cidade, double temperaturaAtual, double tempMaxima,
                       double tempMinima, double umidade, String condicao,
                       double precipitacao, double velocidadeVento, String direcaoVento) {
        this.cidade = cidade;
        this.temperaturaAtual = temperaturaAtual;
        this.tempMaxima = tempMaxima;
        this.tempMinima = tempMinima;
        this.umidade = umidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVento = direcaoVento;
    }

    public String getCidade() { return cidade; }
    public double getTemperaturaAtual() { return temperaturaAtual; }
    public double getTempMaxima() { return tempMaxima; }
    public double getTempMinima() { return tempMinima; }
    public double getUmidade() { return umidade; }
    public String getCondicao() { return condicao; }
    public double getPrecipitacao() { return precipitacao; }
    public double getVelocidadeVento() { return velocidadeVento; }
    public String getDirecaoVento() { return direcaoVento; }
}