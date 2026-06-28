import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherController {
    private final WeatherView view;
    private final WeatherService service;

    // SOLID: Injeção de Dependência através do construtor
    public WeatherController(WeatherView view, WeatherService service) {
        this.view = view;
        this.service = service;

        // Avisa a View que este Controlador vai cuidar do clique do botão
        this.view.addSearchListener(new SearchButtonListener());
    }

    // Classe interna (Listener) que escuta o clique do botão da tela
    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 1. Pega a cidade digitada na View
            String city = view.getCityInput();

            // Validação antes de gastar requisição na API
            if (city.isEmpty()) {
                return;
            }

            // 2. Aciona o serviço para buscar os dados de forma assíncrona/protegida
            try {
                // O Controlador chama o contrato (WeatherService) sem saber qual API está rodando por trás
                WeatherData data = service.getWeather(city);
                
                // 3. Devolve os dados recebidos para a View se atualizar
                view.updateView(data);

            } catch (Exception ex) {
                // Por enquanto, printamos o erro no console. 
                // Na Fase de Tratamento de Erros, vamos melhorar isso aqui!
                ex.printStackTrace();
            }
        }
    }
}