

/**
 * Responsavel por exibir os dados climaticos formatados no console.
 */
public class WeatherDisplay {

    public void exibir(WeatherData dados) {
        if (dados == null) {
            System.out.println("Nao foi possivel obter os dados do clima.");
            return;
        }

        System.out.println("╔══════════════════════════════════════╗");
        System.out.printf ("║  Clima em: %-25s║%n", dados.getCidade());
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║  Temperatura atual: %-16.1f°C║%n", dados.getTemperaturaAtual());
        System.out.printf ("║  Maxima do dia:     %-16.1f°C║%n", dados.getTempMaxima());
        System.out.printf ("║  Minima do dia:     %-16.1f°C║%n", dados.getTempMinima());
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║  Umidade do ar:     %-15.1f%% ║%n", dados.getUmidade());
        System.out.printf ("║  Condicao:          %-18s║%n", dados.getCondicao());

        if (dados.getPrecipitacao() > 0) {
            System.out.printf("║  Precipitacao:      %-13.1f mm ║%n", dados.getPrecipitacao());
        } else {
            System.out.println("║  Precipitacao:      Sem chuva         ║");
        }

        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║  Vento:             %-11.1f km/h ║%n", dados.getVelocidadeVento());
        System.out.printf ("║  Direcao do vento:  %-18s║%n", dados.getDirecaoVento());
        System.out.println("╚══════════════════════════════════════╝");
    }
}