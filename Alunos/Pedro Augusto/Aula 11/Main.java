import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class Main extends JFrame {

    private static final String CONFIG_FILE = "config.properties";
    private String apiKey;

    // Cores modernas
    private final Color COLOR_BACKGROUND = new Color(245, 247, 250);
    private final Color COLOR_CARD = Color.WHITE;
    private final Color COLOR_PRIMARY = new Color(46, 204, 113); // Verde esmeralda moderno
    private final Color COLOR_TEXT_MAIN = new Color(44, 62, 80);
    private final Color COLOR_TEXT_SECONDARY = new Color(127, 140, 141);

    private final JTextField campoCidade = new JTextField("São Paulo", 15);
    private final JButton btnBuscar = new JButton("Buscar");
    private final JLabel lblTemp = new JLabel("--\u00B0C", SwingConstants.CENTER);
    private final JLabel lblCondicao = new JLabel("Aguardando...", SwingConstants.CENTER);
    private final JLabel lblCidade = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel lblDetalhes = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel lblStatus = new JLabel(" ", SwingConstants.CENTER);

    public Main() {
        super("App - Consulta de Clima");
        
        carregarApiKey();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        build();
    }

    private void carregarApiKey() {
        Properties prop = new Properties();
        File file = new File(CONFIG_FILE);

        if (file.exists()) {
            try (InputStream is = new FileInputStream(file)) {
                prop.load(is);
                this.apiKey = prop.getProperty("api_key");
            } catch (IOException e) {
                System.err.println("Erro ao ler config: " + e.getMessage());
            }
        }

        if (this.apiKey == null || this.apiKey.trim().isEmpty()) {
            this.apiKey = JOptionPane.showInputDialog(null, 
                "API Key não encontrada.\nPor favor, insira sua Visual Crossing API Key:",
                "Configuração Inicial", 
                JOptionPane.QUESTION_MESSAGE);

            if (this.apiKey != null && !this.apiKey.trim().isEmpty()) {
                salvarApiKey(this.apiKey.trim());
            } else {
                JOptionPane.showMessageDialog(null, "A API Key é necessária para o funcionamento.");
                System.exit(0);
            }
        }
    }

    private void salvarApiKey(String key) {
        Properties prop = new Properties();
        prop.setProperty("api_key", key);
        try (OutputStream os = new FileOutputStream(CONFIG_FILE)) {
            prop.store(os, "Configurações do WeatherApp");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o arquivo de configuração.");
        }
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(COLOR_BACKGROUND);

        // Painel de Entrada (Topo)
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        painelBusca.setOpaque(false);
        
        campoCidade.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoCidade.setPreferredSize(new Dimension(200, 35));
        campoCidade.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));

        btnBuscar.setBackground(COLOR_PRIMARY);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnBuscar.setPreferredSize(new Dimension(100, 35));
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        painelBusca.add(campoCidade);
        painelBusca.add(btnBuscar);
        root.add(painelBusca, BorderLayout.NORTH);

        // Card Central (Dados)
        JPanel containerCentro = new JPanel(new GridBagLayout());
        containerCentro.setOpaque(false);
        
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));
        card.setPreferredSize(new Dimension(320, 380));

        lblTemp.setFont(new Font("SansSerif", Font.BOLD, 72));
        lblTemp.setForeground(COLOR_TEXT_MAIN);
        lblTemp.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblCondicao.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblCondicao.setForeground(COLOR_PRIMARY);
        lblCondicao.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblCidade.setFont(new Font("SansSerif", Font.ITALIC, 16));
        lblCidade.setForeground(COLOR_TEXT_SECONDARY);
        lblCidade.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDetalhes.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDetalhes.setForeground(COLOR_TEXT_MAIN);
        lblDetalhes.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblTemp);
        card.add(Box.createVerticalStrut(10));
        card.add(lblCondicao);
        card.add(Box.createVerticalStrut(5));
        card.add(lblCidade);
        card.add(Box.createVerticalStrut(30));
        card.add(lblDetalhes);

        containerCentro.add(card);
        root.add(containerCentro, BorderLayout.CENTER);

        // Rodapé (Status)
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblStatus.setBorder(new EmptyBorder(10, 10, 10, 10));
        root.add(lblStatus, BorderLayout.SOUTH);

        add(root);

        btnBuscar.addActionListener(e -> buscar());
        campoCidade.addActionListener(e -> buscar());
        
        // Efeito de hover no botão (restaurado)
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscar.setBackground(COLOR_PRIMARY.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscar.setBackground(COLOR_PRIMARY);
            }
        });
    }

    private void buscar() {
        String cidade = campoCidade.getText().trim();

        if (cidade.isEmpty()) {
            status("Informe a cidade.", Color.ORANGE);
            return;
        }

        btnBuscar.setEnabled(false);
        status("Buscando dados...", COLOR_TEXT_SECONDARY);

        new SwingWorker<WeatherData, Void>() {
            @Override
            protected WeatherData doInBackground() throws Exception {
                return new WeatherClient(apiKey).buscar(cidade);
            }

            @Override
            protected void done() {
                btnBuscar.setEnabled(true);
                try {
                    mostrar(get());
                    status("Dados atualizados com sucesso.", new Color(39, 174, 96));
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    status(cause.getMessage(), new Color(192, 57, 43));
                }
            }
        }.execute();
    }

    private void mostrar(WeatherData d) {
        lblTemp.setText(String.format("%.0f\u00B0", d.getTempAtual()));
        lblCondicao.setText(d.getCondicaoPt());
        lblCidade.setText(d.getCidade());
        
        String detalhes = String.format(
            "<html><center>" +
            "<div style='margin-bottom: 8px;'>Máx: <b>%.1f\u00B0</b> | Mín: <b>%.1f\u00B0</b></div>" +
            "<div style='color: #7f8c8d;'>" +
            "Umidade: %.0f%%<br>" +
            "Chuva: %.1fmm<br>" +
            "Vento: %.1fkm/h %s" +
            "</div></center></html>",
            d.getTempMax(), d.getTempMin(), d.getHumidade(),
            d.getPrecipitacao(), d.getVento(), d.getDirecaoTexto()
        );
        lblDetalhes.setText(detalhes);
    }

    private void status(String msg, Color cor) {
        lblStatus.setText(msg);
        lblStatus.setForeground(cor);
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
