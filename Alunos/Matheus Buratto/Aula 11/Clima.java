public class Clima {

    // Dados de temperatura
    private double tempAtual;
    private double tempMax;
    private double tempMin;

    // Dados atmosféricos
    private double umidade;
    private String descricao;
    private double precipitacao;

    // Dados de vento
    private double velocidadeVento;
    private double direcaoVento;

    public Clima() {}

    public double getTempAtual() {
        return tempAtual;
    }

    public void setTempAtual(double tempAtual) {
        this.tempAtual = tempAtual;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(double precipitacao) {
        this.precipitacao = precipitacao;
    }

    public double getVelocidadeVento() {
        return velocidadeVento;
    }

    public void setVelocidadeVento(double velocidadeVento) {
        this.velocidadeVento = velocidadeVento;
    }

    public double getDirecaoVento() {
        return direcaoVento;
    }

    public void setDirecaoVento(double direcaoVento) {
        this.direcaoVento = direcaoVento;
    }

    @Override
    public String toString() {
        return "Clima{" +
                "tempAtual=" + tempAtual +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", umidade=" + umidade +
                ", descricao='" + descricao + '\'' +
                ", precipitacao=" + precipitacao +
                ", velocidadeVento=" + velocidadeVento +
                ", direcaoVento=" + direcaoVento +
                '}';
    }
}