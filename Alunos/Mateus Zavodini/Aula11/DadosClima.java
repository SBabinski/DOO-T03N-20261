public class DadosClima {
    public double temperaturaAtual, temperaturaMaxima, temperaturaMinima;
    public double humidade, precipitacao, velocidadeVento;
    public String condicaoTempo, direcaoVento, cidade;

    public DadosClima(double temperaturaAtual, double temperaturaMaxima, double temperaturaMinima,
                      double humidade, String condicaoTempo, double precipitacao,
                      double velocidadeVento, String direcaoVento, String cidade) {
        this.temperaturaAtual   = temperaturaAtual;
        this.temperaturaMaxima  = temperaturaMaxima;
        this.temperaturaMinima  = temperaturaMinima;
        this.humidade           = humidade;
        this.condicaoTempo      = condicaoTempo;
        this.precipitacao       = precipitacao;
        this.velocidadeVento    = velocidadeVento;
        this.direcaoVento       = direcaoVento;
        this.cidade             = cidade;
    }
}