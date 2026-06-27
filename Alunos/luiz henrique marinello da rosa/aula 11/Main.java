

import java.util.Scanner;

/**
 * Ponto de entrada do aplicativo de clima.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Aplicativo de Clima ===");
        System.out.print("Digite o nome da cidade (ex: Curitiba,BR): ");
        String cidade = scanner.nextLine().trim();

        if (cidade.isEmpty()) {
            System.out.println("Cidade invalida. Encerrando.");
            scanner.close();
            return;
        }

        System.out.println("\nBuscando dados do clima...");

        WeatherService servico = new WeatherService();
        WeatherData dados = servico.buscarClima(cidade);

        WeatherDisplay display = new WeatherDisplay();
        display.exibir(dados);

        scanner.close();
    }
}