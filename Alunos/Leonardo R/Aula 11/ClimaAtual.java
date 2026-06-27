public class ClimaAtual {
    private final String nomeCidade;
    private final double temperaturaAtual;
    private final double temperaturaMinima;
    private final double temperaturaMaxima;
    private final double umidade;
    private final String descricaoCondicao;
    private final double volumeChuva;
    private final double velocidadeVento;
    private final String direcaoVentoGraus;

    public ClimaAtual(
            String nomeCidade,
            double temperaturaAtual,
            double temperaturaMinima,
            double temperaturaMaxima,
            double umidade,
            String descricaoCondicao,
            double volumeChuva,
            double velocidadeVento,
            String direcaoVentoGraus) {
        this.nomeCidade = nomeCidade;
        this.temperaturaAtual = temperaturaAtual;
        this.temperaturaMinima = temperaturaMinima;
        this.temperaturaMaxima = temperaturaMaxima;
        this.umidade = umidade;
        this.descricaoCondicao = descricaoCondicao;
        this.volumeChuva = volumeChuva;
        this.velocidadeVento = velocidadeVento;
        this.direcaoVentoGraus = direcaoVentoGraus;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public double getUmidade() {
        return umidade;
    }

    public String getDescricaoCondicao() {
        return descricaoCondicao;
    }

    public double getVolumeChuva() {
        return volumeChuva;
    }

    public double getVelocidadeVento() {
        return velocidadeVento;
    }

    public String getDirecaoVentoGraus() {
        return direcaoVentoGraus;
    }
}
