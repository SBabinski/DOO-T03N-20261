public class Clima {

    private String tempAtual;
    private String tempMax;
    private String tempMin;
    private String umidade;
    private String condicao;
    private String chuva;
    private String velVento;
    private String dirVento;

    public Clima(String tempAtual, String tempMax, String tempMin,
                 String umidade, String condicao,
                 String chuva, String velVento, String dirVento) {

        this.tempAtual = tempAtual;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.umidade = umidade;
        this.condicao = condicao;
        this.chuva = chuva;
        this.velVento = velVento;
        this.dirVento = dirVento;
    }

    public String getTempAtual() { return tempAtual; }
    public String getTempMax() { return tempMax; }
    public String getTempMin() { return tempMin; }
    public String getUmidade() { return umidade; }
    public String getCondicao() { return condicao; }
    public String getChuva() { return chuva; }
    public String getVelVento() { return velVento; }
    public String getDirVento() { return dirVento; }
}