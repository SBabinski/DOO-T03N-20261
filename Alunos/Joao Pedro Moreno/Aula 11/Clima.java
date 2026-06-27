public class Clima {

    private double temperatura;
    private double tempMinima;
    private double tempMaxima;
    private double umidade;
    private String condicao;
    private double qtdChuva;
    private double velVento;
    private double dirVento;

    //CONSTRUTOR
    public Clima(double temperatura, double tempMinima, double tempMaxima, double umidade, String condicao, double qtdChuva, double velVento, double dirVento) {
        this.temperatura = temperatura;
        this.tempMinima = tempMinima;
        this.tempMaxima = tempMaxima;
        this.umidade = umidade;
        this.condicao = condicao;
        this.qtdChuva = qtdChuva;
        this.velVento = velVento;
        this.dirVento = dirVento;
    }

    //GETTERS
    public double getTemperatura() {
        return temperatura;
    }

    public double getTempMinima() {
        return tempMinima;
    }

    public double getTempMaxima() {
        return tempMaxima;
    }

    public double getumidade() {
        return umidade;
    }

    public String getCondicao() {
        return condicao;
    }

    public double getQtdChuva() {
        return qtdChuva;
    }

    public double getVelVento() {
        return velVento;
    }

    public double getDirVento() {
        return dirVento;
    }

}