import java.io.IOException;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Aplicativo de consulta de clima - Visual Crossing");
        System.out.println();

        System.out.print("Digite sua chave da API Visual Crossing: ");
        String apiKey = scanner.nextLine().trim();

        System.out.print("Digite a cidade para consultar o clima: ");
        String cidade = scanner.nextLine().trim();

        try {
            WeatherService service = new WeatherService(apiKey);
            WeatherData dados = service.buscarClima(cidade);

            dados.imprimirRelatorio();
        } catch (IllegalArgumentException erro) {
            System.out.println();
            System.out.println("Entrada inválida: " + erro.getMessage());
        } catch (IOException erro) {
            System.out.println();
            System.out.println("Não foi possível consultar a API.");
            System.out.println("Detalhes do erro: " + erro.getMessage());
        }

        scanner.close();
    }
}