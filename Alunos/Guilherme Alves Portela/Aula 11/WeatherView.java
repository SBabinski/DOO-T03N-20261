import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WeatherView extends JFrame {
    // Componentes de Entrada
    private JTextField cityTextField;
    private JButton searchButton;

    // Componentes de Exibição (Labels)
    private JLabel cityLabel;
    private JLabel tempLabel;
    private JLabel maxTempLabel;
    private JLabel minTempLabel;
    private JLabel humidityLabel;
    private JLabel conditionLabel;
    private JLabel precipLabel;
    private JLabel windSpeedLabel;
    private JLabel windDirLabel;

    public WeatherView() {

        setTitle("Sistema de Clima Acadêmico");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a tela
        setLayout(new BorderLayout(10, 10)); // Layout principal

        initializeComponents();
        
        setVisible(true);
    }

    private void initializeComponents() {
        // --- PAINEL DE BUSCA (HEAD) ---
        JPanel searchPanel = new JPanel(new FlowLayout());
        cityTextField = new JTextField(20);
        searchButton = new JButton("Buscar Clima");
        
        searchPanel.add(new JLabel("Cidade:"));
        searchPanel.add(cityTextField);
        searchPanel.add(searchButton);
        
        add(searchPanel, BorderLayout.NORTH);

        // --- PAINEL DE RESULTADOS (BODY) ---
        // Usamos GridLayout(8 linhas, 2 colunas) para os 8 dados solicitados
        JPanel resultPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Informações do Clima"));

        // Inicializando os labels de exibição
        cityLabel = new JLabel("-");
        tempLabel = new JLabel("-");
        minTempLabel = new JLabel("-");
        maxTempLabel = new JLabel("-");
        humidityLabel = new JLabel("-");
        conditionLabel = new JLabel("-");
        precipLabel = new JLabel("-");
        windSpeedLabel = new JLabel("-");
        windDirLabel = new JLabel("-");

        // Adicionando na grade (Rótulo na esquerda, Valor na direita)
        resultPanel.add(new JLabel("Cidade:"));              resultPanel.add(cityLabel);
        resultPanel.add(new JLabel("Temperatura Atual:"));   resultPanel.add(tempLabel);
        resultPanel.add(new JLabel("Mínima do Dia:"));       resultPanel.add(minTempLabel);
        resultPanel.add(new JLabel("Máxima do Dia:"));       resultPanel.add(maxTempLabel);
        resultPanel.add(new JLabel("Umidade do Ar:"));       resultPanel.add(humidityLabel);
        resultPanel.add(new JLabel("Condição:"));            resultPanel.add(conditionLabel);
        resultPanel.add(new JLabel("Precipitação:"));        resultPanel.add(precipLabel);
        resultPanel.add(new JLabel("Velocidade do Vento:")); resultPanel.add(windSpeedLabel);
        resultPanel.add(new JLabel("Direção do Vento:"));    resultPanel.add(windDirLabel);

        add(resultPanel, BorderLayout.CENTER);
    }

    // --- MÉTODOS DE COMUNICAÇÃO (Para o Controlador usar) ---

    // Permite ao controlador pegar o que o usuário digitou
    public String getCityInput() {
        return cityTextField.getText().trim();
    }

    // Permite ao controlador registrar o evento de clique no botão
    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    // Permite ao controlador atualizar a tela com os dados do modelo
    public void updateView(WeatherData data) {
        cityLabel.setText(data.getCity());
        tempLabel.setText(data.getTemperature() + " °C");
        minTempLabel.setText(data.getMinTemperature() + " °C");
        maxTempLabel.setText(data.getMaxTemperature() + " °C"); // Invertido na exibição conforme seu modelo
        humidityLabel.setText(data.getHumidity() + " %");
        conditionLabel.setText(data.getConditions());
        precipLabel.setText(data.getPrecipitation() + " mm");
        windSpeedLabel.setText(data.getWindSpeed() + " km/h");
        windDirLabel.setText(data.getWindDirection() + "°");
    }
}