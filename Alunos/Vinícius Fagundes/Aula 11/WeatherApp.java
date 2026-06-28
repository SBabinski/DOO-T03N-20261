
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherApp extends JFrame {

    private JTextField searchField;
    private JButton searchButton;

    // Componentes de Exibição
    private JLabel cityLabel;
    private JLabel tempLabel;
    private JLabel conditionLabel;
    private JLabel feelsLikeLabel;
    private JLabel humidityLabel;
    private JLabel windLabel;
    private JLabel statusLabel;

    // Painel de previsão
    private JPanel forecastPanel;
    private GradientPanel mainPanel;

    public WeatherApp() {
        setTitle("Previsão do Tempo");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(750, 600));

        // Painel Principal com Fundo Degradê
        mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        setContentPane(mainPanel);

        // --- TOPO: BARRA DE PESQUISA ---
        JPanel searchBarPanel = new JPanel(new BorderLayout(10, 0));
        searchBarPanel.setOpaque(false);

        searchField = new JTextField("São Paulo");
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBackground(new Color(255, 255, 255, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        searchButton = new JButton("Buscar");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(59, 130, 246));
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchBarPanel.add(searchField, BorderLayout.CENTER);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        mainPanel.add(searchBarPanel, BorderLayout.NORTH);

        // --- CENTRO: CLIMA ATUAL ---
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        statusLabel = new JLabel("Digite uma cidade e clique em Buscar");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setForeground(new Color(220, 220, 220));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(statusLabel);
        centerPanel.add(Box.createVerticalStrut(10));

        // Card do Clima Atual (Glassmorphism Panel)
        RoundedPanel currentWeatherCard = new RoundedPanel(20, new Color(255, 255, 255, 18));
        currentWeatherCard.setLayout(new GridBagLayout());
        currentWeatherCard.setBorder(new EmptyBorder(20, 25, 20, 25));
        currentWeatherCard.setMaximumSize(new Dimension(700, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Cidade
        cityLabel = new JLabel("Cidade, País");
        cityLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        cityLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        currentWeatherCard.add(cityLabel, gbc);

        // Temperatura Principal
        tempLabel = new JLabel("--°C");
        tempLabel.setFont(new Font("SansSerif", Font.BOLD, 64));
        tempLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        currentWeatherCard.add(tempLabel, gbc);

        // Condição (Texto + Ícone/Emoji)
        conditionLabel = new JLabel("Condição");
        conditionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        conditionLabel.setForeground(new Color(240, 240, 240));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        currentWeatherCard.add(conditionLabel, gbc);

        // Detalhes extras
        JPanel detailsPanel = new JPanel(new GridLayout(3, 1, 0, 4));
        detailsPanel.setOpaque(false);

        feelsLikeLabel = new JLabel("Sensação térmica: --°C");
        feelsLikeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        feelsLikeLabel.setForeground(new Color(200, 200, 200));

        humidityLabel = new JLabel("Umidade: --%");
        humidityLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        humidityLabel.setForeground(new Color(200, 200, 200));

        windLabel = new JLabel("Vento: -- km/h");
        windLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        windLabel.setForeground(new Color(200, 200, 200));

        detailsPanel.add(feelsLikeLabel);
        detailsPanel.add(humidityLabel);
        detailsPanel.add(windLabel);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        currentWeatherCard.add(detailsPanel, gbc);

        centerPanel.add(currentWeatherCard);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- BASE: PREVISÃO PARA OS PRÓXIMOS DIAS ---
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 10));
        bottomPanel.setOpaque(false);

        JLabel forecastTitle = new JLabel("Previsão para os Próximos Dias");
        forecastTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        forecastTitle.setForeground(Color.WHITE);
        bottomPanel.add(forecastTitle, BorderLayout.NORTH);

        forecastPanel = new JPanel(new GridLayout(1, 4, 12, 0));
        forecastPanel.setOpaque(false);

        // Inicializa com cards vazios
        for (int i = 0; i < 4; i++) {
            forecastPanel.add(createForecastPlaceholderCard());
        }
        bottomPanel.add(forecastPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // --- EVENTOS E LOGICA ---
        ActionListener searchAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        };
        searchButton.addActionListener(searchAction);
        searchField.addActionListener(searchAction);

        // Buscar clima inicial
        performSearch();
    }

    private void performSearch() {
        String city = searchField.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o nome de uma cidade.");
            return;
        }

        statusLabel.setText("Buscando informações climáticas...");
        searchButton.setEnabled(false);

        // Executa a requisição em uma thread separada para não travar a UI
        new Thread(() -> {
            try {
                WeatherData data = WeatherService.fetchWeather(city);
                SwingUtilities.invokeLater(() -> updateUI(data));
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Erro ao obter dados: " + ex.getMessage());
                    searchButton.setEnabled(true);
                });
            }
        }).start();
    }

    private void updateUI(WeatherData data) {
        statusLabel.setText("Clima atualizado em: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        searchButton.setEnabled(true);

        cityLabel.setText(data.resolvedAddress);
        tempLabel.setText(Math.round(data.currentTemp) + "°C");

        String emoji = getWeatherEmoji(data.currentIcon);
        conditionLabel.setText(emoji + "  " + data.currentConditions);

        feelsLikeLabel.setText("Sensação térmica: " + Math.round(data.currentFeelsLike) + "°C");
        humidityLabel.setText("Umidade: " + Math.round(data.currentHumidity) + "%");
        windLabel.setText("Vento: " + Math.round(data.currentWindSpeed) + " km/h");

        // Atualiza a cor de fundo do gradiente dinamicamente de acordo com o clima
        updateBackground(data.currentIcon, data.currentTemp);

        // Atualizar previsão de dias futuros
        forecastPanel.removeAll();
        // O primeiro dia da lista costuma ser o próprio dia corrente. Vamos pular ou
        // usar os próximos.
        // Vamos mostrar do index 1 ao 4 se disponível.
        int startIndex = 1;
        if (data.forecast.size() <= 4) {
            startIndex = 0; // Se a resposta for pequena, começa do 0
        }

        int added = 0;
        for (int i = startIndex; i < data.forecast.size() && added < 4; i++) {
            WeatherData.ForecastDay day = data.forecast.get(i);
            forecastPanel.add(createForecastCard(day));
            added++;
        }

        // Se não houver previsão suficiente, preenche com placeholders
        while (added < 4) {
            forecastPanel.add(createForecastPlaceholderCard());
            added++;
        }

        forecastPanel.revalidate();
        forecastPanel.repaint();
    }

    private JPanel createForecastPlaceholderCard() {
        RoundedPanel card = new RoundedPanel(15, new Color(255, 255, 255, 10));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel dateLabel = new JLabel("--/--");
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel iconLabel = new JLabel("?");
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(new Color(255, 255, 255, 180));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tempMaxMin = new JLabel("--° / --°");
        tempMaxMin.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tempMaxMin.setForeground(new Color(200, 200, 200));
        tempMaxMin.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(dateLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(tempMaxMin);

        return card;
    }

    private JPanel createForecastCard(WeatherData.ForecastDay day) {
        RoundedPanel card = new RoundedPanel(15, new Color(255, 255, 255, 15));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(15, 10, 15, 10));

        // Formatação simples de data (yyyy-MM-dd para dd/MM)
        String displayDate = day.date;
        try {
            if (day.date.length() >= 10) {
                String[] parts = day.date.split("-");
                displayDate = parts[2] + "/" + parts[1];
            }
        } catch (Exception e) {
        }

        JLabel dateLabel = new JLabel(displayDate);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String emoji = getWeatherEmoji(day.icon);
        JLabel iconLabel = new JLabel(emoji);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tempMaxMin = new JLabel(Math.round(day.tempMax) + "° / " + Math.round(day.tempMin) + "°");
        tempMaxMin.setFont(new Font("SansSerif", Font.BOLD, 13));
        tempMaxMin.setForeground(Color.WHITE);
        tempMaxMin.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel condLabel = new JLabel(day.conditions);
        condLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        condLabel.setForeground(new Color(210, 210, 210));
        condLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(dateLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(tempMaxMin);
        card.add(Box.createVerticalStrut(4));
        card.add(condLabel);

        return card;
    }

    private void updateBackground(String icon, double temp) {
        Color c1, c2;
        if (icon == null) {
            c1 = new Color(15, 32, 67);
            c2 = new Color(44, 19, 68);
        } else {
            String lowerIcon = icon.toLowerCase();
            if (lowerIcon.contains("clear-day") || (lowerIcon.contains("clear") && !lowerIcon.contains("night"))) {
                // Ensolarado / Dia limpo: Amarelo e Laranja
                c1 = new Color(245, 158, 11); // Amber
                c2 = new Color(239, 68, 68); // Vermelho Laranja
            } else if (temp < 15 || lowerIcon.contains("snow") || lowerIcon.contains("sleet")
                    || lowerIcon.contains("rain")) {
                // Frio / Chuva / Neve: Azul e Roxo
                c1 = new Color(37, 99, 235); // Azul Royal
                c2 = new Color(124, 58, 237); // Roxo/Violeta
            } else if (lowerIcon.contains("cloudy") || lowerIcon.contains("fog") || lowerIcon.contains("wind")) {
                // Nublado / Nevoeiro / Vento: Tons de Cinza
                c1 = new Color(100, 116, 139); // Slate Gray
                c2 = new Color(51, 65, 85); // Dark Slate
            } else {
                // Noite limpa ou outros casos: Gradiente Azul Escuro
                c1 = new Color(15, 32, 67);
                c2 = new Color(44, 19, 68);
            }
        }
        mainPanel.setColors(c1, c2);
    }

    private String getWeatherEmoji(String icon) {
        if (icon == null)
            return "\u2600"; // ☀
        switch (icon.toLowerCase()) {
            case "clear-day":
                return "\u2600"; // ☀
            case "clear-night":
                return "\u263D"; // ☽
            case "rain":
                return "\u2614"; // ☔
            case "snow":
                return "\u2744"; // ❄
            case "sleet":
                return "\u2744"; // ❄
            case "wind":
                return "\u2248"; // ≈ (vento)
            case "fog":
                return "\u2591"; // ░ (névoa)
            case "cloudy":
                return "\u2601"; // ☁
            case "partly-cloudy-day":
                return "\u26C5"; // ⛅ Sun behind cloud
            case "partly-cloudy-night":
                return "\u2601"; // ☁
            default:
                if (icon.contains("rain"))
                    return "\u2614"; // ☔
                if (icon.contains("cloud"))
                    return "\u2601"; // ☁
                if (icon.contains("clear"))
                    return "\u2600"; // ☀
                return "\u2600";
        }
    }

    // Componente Customizado para Fundo Degradê Premium
    private static class GradientPanel extends JPanel {
        private Color color1 = new Color(15, 32, 67);
        private Color color2 = new Color(44, 19, 68);

        public void setColors(Color c1, Color c2) {
            this.color1 = c1;
            this.color2 = c2;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Painel Personalizado com Cantos Arredondados e Transparência (Efeito Glass)
    private static class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

            // Borda fina translúcida
            g2.setColor(new Color(255, 255, 255, 40));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        }
    }

    public static void main(String[] args) {
        // Look and Feel do sistema ou Nimbus para melhor acabamento
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback para padrão
        }

        SwingUtilities.invokeLater(() -> {
            new WeatherApp().setVisible(true);
        });
    }
}
