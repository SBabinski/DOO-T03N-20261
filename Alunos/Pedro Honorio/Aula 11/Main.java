import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new TelaClima();
        });

        Scanner sc = new Scanner(System.in);
        WeatherService service = new WeatherService();

        String cidade;

        while (true) {
            System.out.print("Digite a cidade: ");
            cidade = sc.nextLine();
            cidade = cidade.trim();

            if (cidade.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                break;
            }

            System.out.println("Cidade inválida! Digite apenas nomes de cidades.");
        }

        try {
            Clima clima = service.buscarClima(cidade);

            if (clima == null) {
                System.out.println("Não foi possível buscar o clima dessa cidade.");
                return;
            }

            System.out.println(" CLIMA EM TEMPO REAL");
            System.out.println("----------------------");
            System.out.println("Cidade: " + cidade);
            System.out.println("Temperatura atual: " + clima.getTemperaturaAtual() + "°C");
            System.out.println("Máxima: " + clima.getTemperaturaMaxima() + "°C");
            System.out.println("Mínima: " + clima.getTemperaturaMinima() + "°C");
            System.out.println("Umidade: " + clima.getUmidade() + "%");
            System.out.println("Condição: " + clima.getCondicao());
            System.out.println("Precipitação: " + clima.getPrecipitacao() + " mm");
            System.out.println("Vento: " + clima.getVelocidadeVento() + " km/h");
            System.out.println("Direção do vento: " + clima.getDirecaoVento() + "°");

        } catch (Exception e) {
            System.out.println("Erro ao buscar clima: " + e.getMessage());
        }

        sc.close();
    }
}
