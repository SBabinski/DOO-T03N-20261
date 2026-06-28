/*


    Autor: Guilherme Alves Portela
    Data: 2024-06
*/

public class Main {
    public static void main(String[] args) {
        // 1. Instancia o motor (Infraestrutura)
        WeatherService service = new VisualCrossingWeatherService();

        // 2. Instancia a tela (Apresentação)
        WeatherView view = new WeatherView();

        // 3. Instancia o Controlador injetando a tela e o serviço (Orquestração)
        new WeatherController(view, service);
        
        System.out.println("Sistema de clima iniciado com sucesso!");
    }
}