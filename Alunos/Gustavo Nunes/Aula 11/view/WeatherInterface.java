/*
 * Graphical user interface of the application.
 * Responsible for:
 * - Capturing the city entered by the user.
 * - Requesting weather data from WeatherService.
 * - Displaying weather information on the screen.
 */

package view;

import javax.swing.*;
import java.awt.*;

import model.WeatherDTO;
import service.WeatherService;

public class WeatherInterface extends JFrame {
    
    // User text field
    private JTextField cityField;
    
    // default window size
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 400;
    
    // Weather search service
    private WeatherService weatherService;
    
    // Output labels
    private JLabel temperatureLabel;
    private JLabel maxTemperatureLabel;
    private JLabel minTemperatureLabel;
    private JLabel humidityLabel;
    private JLabel conditionsLabel;
    private JLabel precipitationLabel;
    private JLabel windSpeedLabel;
    private JLabel windDirectionLabel;
    
    // Constructor / Create the window
    public WeatherInterface() {
        
        // init Weather search service
        weatherService = new WeatherService();
        
        // The Window
        configureFrame();
        
        // Add search panel to window top
        add(buildSearchPanel(), BorderLayout.NORTH);
        
        // Add results panel to window center
        add(buildResultsPanel(), BorderLayout.CENTER);
        
        // Turn visible the window
        setVisible(true);
        
        // initializes with cursor in the field
        cityField.requestFocusInWindow();
    }
    
    private void configureFrame() {
        
        // Window title
        setTitle("Weather APP");
        
        // Window size
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // Window default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Window position
        setLocationRelativeTo(null);
        
        // Window layout
        setLayout(new BorderLayout());
    }
    
    private JPanel buildSearchPanel() {
        
        // Top panel
        JPanel searchPanel = new JPanel();
        
        // Add label to the top panel
        searchPanel.add(new JLabel("Cidade:"));
        
        // sized text field
        cityField = new JTextField(20);
        
        // Allow Enter to search
        cityField.addActionListener(
                e -> searchWeather()
        );
        
        // Add user text field to the top panel
        searchPanel.add(cityField);
        
        // Search button
        JButton searchButton = new JButton("Buscar");
        
        // Add action to button
        searchButton.addActionListener(e -> searchWeather());
        
        // Add search button
        searchPanel.add(searchButton);
        
        return searchPanel;
    }
    
    private JPanel buildResultsPanel() {
        
        // Results panel
        JPanel resultsPanel = new JPanel();
        
        // Results panel layout
        resultsPanel.setLayout(new GridLayout(8, 1));
        
        // Init labels
        temperatureLabel = new JLabel("Temperatura: --");
        maxTemperatureLabel = new JLabel("Máxima: --");
        minTemperatureLabel = new JLabel("Mínima: --");
        humidityLabel = new JLabel("Umidade: --");
        conditionsLabel = new JLabel("Condição: --");
        precipitationLabel = new JLabel("Precipitação: --");
        windSpeedLabel = new JLabel("Velocidade do vento: --");
        windDirectionLabel = new JLabel("Direção do vento: --");
        
        // Add labels to the results panel
        resultsPanel.add(temperatureLabel);
        resultsPanel.add(maxTemperatureLabel);
        resultsPanel.add(minTemperatureLabel);
        resultsPanel.add(humidityLabel);
        resultsPanel.add(conditionsLabel);
        resultsPanel.add(precipitationLabel);
        resultsPanel.add(windSpeedLabel);
        resultsPanel.add(windDirectionLabel);
        
        return resultsPanel;
    }
    
    // Search the Weather
    private void searchWeather() {
        
        // Try capture the city message
        try {
            
            // capture city
            String city = cityField.getText();
            
            // Search city weather
            WeatherDTO weatherDTO = weatherService.searchWeather(city);
            
            // Update weather labels
            updateWeatherLabels(weatherDTO);
        
        } catch (Exception e) {
            
            // Show error message
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    // Update weather labels operation
    private void updateWeatherLabels(WeatherDTO weather) {
        
        temperatureLabel.setText(
                "Temperatura: "
                        + String.format("%.1f", weather.getTemperature())
                        + " °C"
        );
        
        maxTemperatureLabel.setText(
                "Máxima: "
                        + String.format("%.1f", weather.getMaxTemperature())
                        + " °C"
        );
        
        minTemperatureLabel.setText(
                "Mínima: "
                        + String.format("%.1f", weather.getMinTemperature())
                        + " °C"
        );
        
        humidityLabel.setText(
                "Umidade: "
                        + String.format("%.1f", weather.getHumidity())
                        + " %"
        );
        
        conditionsLabel.setText(
                "Condição: "
                        + weather.getConditions()
        );
        
        precipitationLabel.setText(
                "Precipitação: "
                        + String.format("%.1f", weather.getPrecipitation())
                        + " mm"
        );
        
        windSpeedLabel.setText(
                "Velocidade do vento: "
                        + String.format("%.1f", weather.getWindSpeed())
                        + " km/h"
        );
        
        windDirectionLabel.setText(
                "Direção do vento: "
                        + weather.getWindDirection()
        );
        
        
    }
}
