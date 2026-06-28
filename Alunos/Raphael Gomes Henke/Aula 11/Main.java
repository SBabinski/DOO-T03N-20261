import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("CONSULTA DE CLIMA");
        System.out.println();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        WeatherService service = new WeatherService();

        WeatherData clima = service.buscarClima(cidade);

        clima.mostrarDados();

        scanner.close();
    }
}