
public class DadosCLima {

    private final String cidade;
    private final double tempAtual;
    private final double tempMax;
    private final double tempMin;
    private final double humidade;
    private final String condicao;
    private final double precipitacao;
    private final double vento;
    private final double direcaoVento;

    public DadosCLima(String cidade, double tempAtual, double tempMax, double tempMin,
                      double humidade, String condicao, double precipitacao,
                      double vento, double direcaoVento) {
        this.cidade = cidade;
        this.tempAtual = tempAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.humidade = humidade;
        this.condicao = condicao;
        this.precipitacao = precipitacao;
        this.vento = vento;
        this.direcaoVento = direcaoVento;
    }

    public String getCidade() { return cidade; }
    public double getTempAtual() { return tempAtual; }
    public double getTempMax() { return tempMax; }
    public double getTempMin() { return tempMin; }
    public double getHumidade() { return humidade; }
    public String getCondicao() { return condicao; }
    public double getPrecipitacao() { return precipitacao; }
    public double getVento() { return vento; }

    public String getDirecaoTexto() {
        String[] pts = {"N", "NE", "L", "SE", "S", "SO", "O", "NO"};
        return pts[(int) Math.round(direcaoVento / 45.0) % 8];
    }
}
