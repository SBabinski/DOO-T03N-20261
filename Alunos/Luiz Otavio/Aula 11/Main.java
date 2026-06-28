

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String apiKey = ApiKeyProvider.getKey();

        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("Erro: chave de API não encontrada.");
            System.err.println("Defina a variável de ambiente VISUALCROSSING_API_KEY antes de executar.");
            System.err.println("  Linux/macOS: export VISUALCROSSING_API_KEY=sua_chave");
            System.err.println("  Windows CMD: set VISUALCROSSING_API_KEY=sua_chave");
            System.exit(1);
        }

        WeatherService service = new WeatherService(apiKey);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Consulta de Clima — Visual Crossing ===");
        System.out.println("Digite 'sair' para encerrar.\n");

        while (true) {
            System.out.print("Cidade: ");
            String city = scanner.nextLine().trim();

            if (city.equalsIgnoreCase("sair")) break;
            if (city.isBlank()) continue;

            try {
                WeatherData data = service.fetch(city);
                WeatherPrinter.print(data);
            } catch (WeatherException e) {
                System.err.println("Erro: " + e.getMessage());
            }

            System.out.println();
        }

        System.out.println("Encerrando.");
        scanner.close();
    }
}
