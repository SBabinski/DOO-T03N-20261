package com.weatherapp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Interface principal — busca por Cidade + Estado + País (todos obrigatórios).
 */
public class WeatherFrame extends JFrame {

    // ---- Cores ----
    private static final Color BG_MAIN      = new Color(15,  23,  42);
    private static final Color BG_CARD      = new Color(30,  41,  59);
    private static final Color BG_INPUT     = new Color(51,  65,  85);
    private static final Color BG_INPUT_ERR = new Color(80,  30,  30);
    private static final Color ACCENT       = new Color(56, 189, 248);
    private static final Color ACCENT_WARM  = new Color(251,191,  36);
    private static final Color TEXT_MAIN    = new Color(248,250,252);
    private static final Color TEXT_MUTED   = new Color(148,163,184);
    private static final Color BORDER_COLOR = new Color(71,  85, 105);
    private static final Color BORDER_ERR   = new Color(239,  68,  68);
    private static final Color BTN_HOVER    = new Color(14, 165,233);
    private static final Color COLOR_OK     = new Color(74, 222,128);
    private static final Color COLOR_ERR    = new Color(248,113,113);

    // ---- Fontes ----
    private static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_TEMP_BIG = new Font("Segoe UI", Font.BOLD,  64);
    private static final Font FONT_LABEL    = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_VALUE    = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_COND     = new Font("Segoe UI", Font.PLAIN, 20);
    private static final Font FONT_BUTTON   = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_INPUT    = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_FIELD_LB = new Font("Segoe UI", Font.BOLD,  11);

    // ---- Campos de busca ----
    private JTextField fieldCity;
    private JTextField fieldState;
    private JTextField fieldCountry;
    private JLabel     errCity;
    private JLabel     errState;
    private JLabel     errCountry;

    // ---- Resultado ----
    private JButton searchButton;
    private JPanel  contentPanel;
    private JLabel  lblStatus;

    // ---- Labels de resultado ----
    private JLabel lblCity, lblAddress, lblTempBig, lblCondition, lblUpdated;
    private JLabel valTempMax, valTempMin, valHumidity, valPrecip, valWindSpeed, valWindDir;

    private final WeatherService service = new WeatherService();

    public WeatherFrame() {
        setTitle("Consulta de Clima");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 740);
        setMinimumSize(new Dimension(480, 660));
        setLocationRelativeTo(null);
        setResizable(true);
        buildUI();
    }

    // =========================================================
    //  UI
    // =========================================================

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_MAIN);
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(root);

        root.add(buildHeader(),  BorderLayout.NORTH);
        root.add(buildContent(), BorderLayout.CENTER);
        root.add(buildFooter(),  BorderLayout.SOUTH);
    }

    // ---- Cabeçalho ----

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(BG_MAIN);
        panel.setBorder(new EmptyBorder(0, 0, 16, 0));

        // Título
        JLabel title = new JLabel("Consulta de Clima");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_MAIN);
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleRow.setBackground(BG_MAIN);
        titleRow.add(title);
        panel.add(titleRow, BorderLayout.NORTH);

        // Formulário de busca
        panel.add(buildSearchForm(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSearchForm() {
        // Card com borda
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(16, 16, 16, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        // --- Linha: Cidade (largura total) ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0;
        card.add(makeFieldLabel("Cidade *"), gbc);

        gbc.gridy = 1;
        fieldCity = makeTextField("Ex: Curitiba, São Paulo, New York...");
        card.add(fieldCity, gbc);

        gbc.gridy = 2;
        errCity = makeErrLabel();
        card.add(errCity, gbc);

        gbc.gridy = 3;
        card.add(Box.createVerticalStrut(8), gbc);

        // --- Linha: Estado | País ---
        gbc.gridwidth = 1; gbc.gridy = 4;

        // Estado
        gbc.gridx = 0; gbc.weightx = 0.5; gbc.insets = new Insets(0, 0, 0, 8);
        card.add(makeFieldLabel("Estado / Província *"), gbc);

        gbc.gridx = 1; gbc.insets = new Insets(0, 0, 0, 0);
        card.add(makeFieldLabel("País *"), gbc);

        gbc.gridy = 5;
        fieldState = makeTextField("Ex: Paraná, PR, California...");
        gbc.gridx = 0; gbc.insets = new Insets(0, 0, 0, 8);
        card.add(fieldState, gbc);

        fieldCountry = makeTextField("Ex: Brasil, Brazil, US...");
        gbc.gridx = 1; gbc.insets = new Insets(0, 0, 0, 0);
        card.add(fieldCountry, gbc);

        gbc.gridy = 6;
        errState = makeErrLabel();
        gbc.gridx = 0; gbc.insets = new Insets(0, 0, 0, 8);
        card.add(errState, gbc);

        errCountry = makeErrLabel();
        gbc.gridx = 1; gbc.insets = new Insets(0, 0, 0, 0);
        card.add(errCountry, gbc);

        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2; gbc.insets = new Insets(0,0,0,0);
        card.add(Box.createVerticalStrut(12), gbc);

        // --- Botão Buscar ---
        searchButton = createButton("Buscar");
        searchButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        searchButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        gbc.gridy = 8;
        card.add(searchButton, gbc);

        // Ações
        ActionListener onEnter = e -> doSearch();
        searchButton.addActionListener(onEnter);
        fieldCity.addActionListener(onEnter);
        fieldState.addActionListener(onEnter);
        fieldCountry.addActionListener(onEnter);

        // Limpa erro ao digitar
        DocumentAdapter clearErr = new DocumentAdapter();
        fieldCity.getDocument().addDocumentListener(clearErr.forField(fieldCity, errCity));
        fieldState.getDocument().addDocumentListener(clearErr.forField(fieldState, errState));
        fieldCountry.getDocument().addDocumentListener(clearErr.forField(fieldCountry, errCountry));

        return card;
    }

    // ---- Conteúdo ----

    private JPanel buildContent() {
        contentPanel = new JPanel(new BorderLayout(0, 14));
        contentPanel.setBackground(BG_MAIN);
        contentPanel.setBorder(new EmptyBorder(14, 0, 0, 0));

        JLabel hint = new JLabel("Preencha os campos acima e clique em Buscar.", SwingConstants.CENTER);
        hint.setFont(FONT_SUBTITLE);
        hint.setForeground(TEXT_MUTED);
        contentPanel.add(hint, BorderLayout.CENTER);
        return contentPanel;
    }

    // ---- Rodapé ----

    private JPanel buildFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(BG_MAIN);
        panel.setBorder(new EmptyBorder(8, 0, 0, 0));
        lblStatus = new JLabel(" ");
        lblStatus.setFont(FONT_SUBTITLE);
        lblStatus.setForeground(TEXT_MUTED);
        panel.add(lblStatus);
        return panel;
    }

    // =========================================================
    //  Busca
    // =========================================================

    private void doSearch() {
        String city    = fieldCity.getText().trim();
        String state   = fieldState.getText().trim();
        String country = fieldCountry.getText().trim();

        boolean ok = true;
        ok = validate(fieldCity,    errCity,    city,    "Informe a cidade.")    && ok;
        ok = validate(fieldState,   errState,   state,   "Informe o estado.")    && ok;
        ok = validate(fieldCountry, errCountry, country, "Informe o país.")      && ok;
        if (!ok) {
            setStatus("Preencha todos os campos obrigatórios.", Color.ORANGE);
            return;
        }

        // Monta a query: "Cidade, Estado, País"
        String location = city + ", " + state + ", " + country;

        setInputsEnabled(false);
        setStatus("Buscando \"" + location + "\"...", TEXT_MUTED);

        SwingWorker<WeatherData, Void> worker = new SwingWorker<>() {
            @Override protected WeatherData doInBackground() throws Exception {
                return service.fetchWeather(location);
            }
            @Override protected void done() {
                setInputsEnabled(true);
                try {
                    WeatherData data = get();
                    displayWeather(data);
                    String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                    setStatus("Atualizado às " + time + ".", COLOR_OK);
                } catch (Exception ex) {
                    String msg = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
                    showError(msg);
                    setStatus("Erro na consulta.", COLOR_ERR);
                }
            }
        };
        worker.execute();
    }

    // =========================================================
    //  Exibição dos dados
    // =========================================================

    private void displayWeather(WeatherData data) {
        contentPanel.removeAll();

        // Card principal
        JPanel mainCard = new JPanel(new BorderLayout(0, 6));
        mainCard.setBackground(BG_CARD);
        mainCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(20, 24, 20, 24)
        ));

        lblCity = new JLabel(data.getResolvedAddress());
        lblCity.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCity.setForeground(TEXT_MAIN);

        lblAddress = new JLabel(" " + data.getCity());
        lblAddress.setFont(FONT_SUBTITLE);
        lblAddress.setForeground(TEXT_MUTED);

        JPanel cityPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        cityPanel.setBackground(BG_CARD);
        cityPanel.add(lblCity);
        cityPanel.add(lblAddress);

        lblTempBig = new JLabel(String.format("%.1f°C", data.getTempCurrent()), SwingConstants.RIGHT);
        lblTempBig.setFont(FONT_TEMP_BIG);
        lblTempBig.setForeground(ACCENT_WARM);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(BG_CARD);
        topRow.add(cityPanel,  BorderLayout.WEST);
        topRow.add(lblTempBig, BorderLayout.EAST);

        lblCondition = new JLabel(data.getConditionsPT());
        lblCondition.setFont(FONT_COND);
        lblCondition.setForeground(ACCENT);

        lblUpdated = new JLabel("Consulta: " + data.getDatetime(), SwingConstants.RIGHT);
        lblUpdated.setFont(FONT_SUBTITLE);
        lblUpdated.setForeground(TEXT_MUTED);

        JPanel condRow = new JPanel(new BorderLayout());
        condRow.setBackground(BG_CARD);
        condRow.add(lblCondition, BorderLayout.WEST);
        condRow.add(lblUpdated,   BorderLayout.EAST);

        mainCard.add(topRow,  BorderLayout.NORTH);
        mainCard.add(condRow, BorderLayout.CENTER);

        // Grid de métricas
        JPanel grid = new JPanel(new GridLayout(2, 3, 12, 12));
        grid.setBackground(BG_MAIN);

        valTempMax   = new JLabel(String.format("%.1f°C",  data.getTempMax()));
        valTempMin   = new JLabel(String.format("%.1f°C",  data.getTempMin()));
        valHumidity  = new JLabel(String.format("%.0f%%",  data.getHumidity()));
        valPrecip    = new JLabel(formatPrecip(data.getPrecipitation()));
        valWindSpeed = new JLabel(String.format("%.1f km/h", data.getWindSpeed()));
        valWindDir   = new JLabel(String.format("%.0f° %s", data.getWindDir(), data.getWindDirectionName()));

        grid.add(metricCard("Máxima",       valTempMax,   new Color(251,191, 36)));
        grid.add(metricCard("Mínima",        valTempMin,   new Color( 56,189,248)));
        grid.add(metricCard("Umidade",       valHumidity,  new Color(129,140,248)));
        grid.add(metricCard("Precipitação",  valPrecip,    new Color( 74,222,128)));
        grid.add(metricCard("Vel. Vento",    valWindSpeed, new Color(251,146, 60)));
        grid.add(metricCard("Dir. Vento",    valWindDir,   new Color(236, 72,153)));

        contentPanel.add(mainCard, BorderLayout.NORTH);
        contentPanel.add(grid,     BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel metricCard(String label, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 6));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(14, 16, 14, 16)
        ));
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);
        valueLabel.setFont(FONT_VALUE);
        valueLabel.setForeground(accent);
        card.add(lbl);
        card.add(valueLabel);
        return card;
    }

    private void showError(String message) {
        contentPanel.removeAll();
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(BG_MAIN);
        JLabel icon = new JLabel("⚠", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icon.setForeground(COLOR_ERR);
        JLabel msg = new JLabel(
            "<html><center>" + message.replace("\n","<br>") + "</center></html>",
            SwingConstants.CENTER);
        msg.setFont(FONT_SUBTITLE);
        msg.setForeground(COLOR_ERR);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.insets = new Insets(0,0,8,0);
        p.add(icon, c);
        c.gridy = 1; p.add(msg, c);
        contentPanel.add(p, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =========================================================
    //  Utilitários de formulário
    // =========================================================

    /** Valida campo: aplica borda vermelha e exibe msg de erro se vazio. */
    private boolean validate(JTextField field, JLabel errLbl, String value, String msg) {
        if (value.isEmpty()) {
            highlightError(field, errLbl, msg);
            return false;
        }
        clearError(field, errLbl);
        return true;
    }

    private void highlightError(JTextField f, JLabel lbl, String msg) {
        f.setBackground(BG_INPUT_ERR);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_ERR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        lbl.setText("⚠ " + msg);
        lbl.setVisible(true);
    }

    private void clearError(JTextField f, JLabel lbl) {
        f.setBackground(BG_INPUT);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        lbl.setText(" ");
    }

    private JTextField makeTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(FONT_INPUT);
        tf.setBackground(BG_INPUT);
        tf.setForeground(TEXT_MAIN);
        tf.setCaretColor(TEXT_MAIN);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        tf.putClientProperty("JTextField.placeholderText", placeholder);
        return tf;
    }

    private JLabel makeFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_FIELD_LB);
        lbl.setForeground(TEXT_MUTED);
        lbl.setBorder(new EmptyBorder(0, 0, 4, 0));
        return lbl;
    }

    private JLabel makeErrLabel() {
        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(COLOR_ERR);
        lbl.setBorder(new EmptyBorder(2, 2, 0, 0));
        return lbl;
    }

    private void setInputsEnabled(boolean en) {
        searchButton.setEnabled(en);
        fieldCity.setEnabled(en);
        fieldState.setEnabled(en);
        fieldCountry.setEnabled(en);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? BTN_HOVER : ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BUTTON);
        btn.setForeground(BG_MAIN);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void setStatus(String text, Color color) {
        lblStatus.setText(text);
        lblStatus.setForeground(color);
    }

    private String formatPrecip(double mm) {
        return mm <= 0 ? "Sem chuva" : String.format("%.1f mm", mm);
    }

    // =========================================================
    //  Adaptador de Document para limpar erros ao digitar
    // =========================================================

    private static class DocumentAdapter {
        javax.swing.event.DocumentListener forField(JTextField field, JLabel errLbl) {
            return new javax.swing.event.DocumentListener() {
                void clear() {
                    field.setBackground(new Color(51, 65, 85));
                    field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(71, 85, 105), 1, true),
                        new EmptyBorder(8, 12, 8, 12)
                    ));
                    errLbl.setText(" ");
                }
                public void insertUpdate(javax.swing.event.DocumentEvent e)  { clear(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e)  { clear(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { clear(); }
            };
        }
    }
}
