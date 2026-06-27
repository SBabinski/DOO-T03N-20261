import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WeatherGUI extends JFrame {

    private static final Color BG_DARK       = new Color(15,  23,  42);
    private static final Color BG_CARD       = new Color(30,  41,  59);
    private static final Color BG_CARD2      = new Color(51,  65,  85);
    private static final Color ACCENT_BLUE   = new Color(56,  189, 248);
    private static final Color ACCENT_BLUE_H = new Color(14,  165, 233);
    private static final Color ACCENT_BLUE_P = new Color(2,   132, 199);
    private static final Color ACCENT_GREEN  = new Color(74,  222, 128);
    private static final Color COLOR_RED     = new Color(248, 113, 113);
    private static final Color TEXT_WHITE    = new Color(248, 250, 252);
    private static final Color TEXT_MUTED    = new Color(148, 163, 184);
    private static final Color BORDER_COLOR  = new Color(71,  85,  105);

    private static final String[] PRESET_CITIES = {
        "[ Selecione ou digite uma cidade ]",
        "Sao Paulo, BR",
        "Rio de Janeiro, BR",
        "Brasilia, BR",
        "Belo Horizonte, BR",
        "Curitiba, BR",
        "Porto Alegre, BR",
        "Salvador, BR",
        "Fortaleza, BR",
        "Recife, BR",
        "Manaus, BR",
        "Florianopolis, BR",
        "Goiania, BR",
        "--- Internacional ---",
        "Lisboa, PT",
        "Porto, PT",
        "London, GB",
        "Paris, FR",
        "New York, US",
        "Tokyo, JP",
        "Buenos Aires, AR",
        "Santiago, CL",
        "Bogota, CO",
        "Mexico City, MX",
        "Berlin, DE",
        "Madrid, ES",
        "Rome, IT",
        "Sydney, AU",
        "Dubai, AE",
    };

    private final WeatherEngine engine = new WeatherEngine();

    private JComboBox<String> cityCombo;
    private FlatButton        searchButton;

    private JLabel lblCity;
    private JLabel lblCondition;
    private JLabel lblTemp;
    private JLabel lblTempMax;
    private JLabel lblTempMin;
    private JLabel lblHumidity;
    private JLabel lblPrecip;
    private JLabel lblWindSpeed;
    private JLabel lblWindDir;
    private JLabel lblStatus;

    public WeatherGUI() {
        setTitle("Clima ao Vivo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_DARK);
        p.setBorder(new EmptyBorder(26, 32, 10, 32));

        JLabel title = new JLabel("  Consulta de Clima");
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        title.setForeground(TEXT_WHITE);

        JLabel sub = new JLabel("Visual Crossing Weather API  •  Unidades metricas");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(TEXT_MUTED);

        JPanel texts = new JPanel(new GridLayout(2, 1, 0, 3));
        texts.setOpaque(false);
        texts.add(title);
        texts.add(sub);
        p.add(texts, BorderLayout.WEST);

        String keyStatus;
        Color  keyColor;
        try {
            WeatherEngine.loadApiKey();
            keyStatus = "API Key carregada";
            keyColor  = ACCENT_GREEN;
        } catch (WeatherException e) {
            keyStatus = ".env nao encontrado";
            keyColor  = COLOR_RED;
        }
        JLabel badge = new JLabel(keyStatus);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(keyColor);
        p.add(badge, BorderLayout.EAST);

        return p;
    }

    private JPanel buildCenter() {
        JPanel wrapper = new JPanel(new BorderLayout(0, 14));
        wrapper.setBackground(BG_DARK);
        wrapper.setBorder(new EmptyBorder(4, 32, 16, 32));
        wrapper.add(buildSearchPanel(), BorderLayout.NORTH);
        wrapper.add(buildResultArea(),  BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildSearchPanel() {
        JPanel card = card(BG_CARD);
        card.setLayout(new BorderLayout(12, 0));

        JLabel lbl = new JLabel("Cidade:");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(TEXT_MUTED);
        lbl.setBorder(new EmptyBorder(0, 0, 0, 4));
        card.add(lbl, BorderLayout.WEST);

        cityCombo = new JComboBox<>(PRESET_CITIES) {
            @Override public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                return new Dimension(Math.max(d.width, 340), 38);
            }
        };
        cityCombo.setEditable(true);
        cityCombo.setSelectedIndex(0);
        styleCombo(cityCombo);

        cityCombo.addActionListener(e -> {
            if ("comboBoxChanged".equals(e.getActionCommand())) {
                Object sel = cityCombo.getSelectedItem();
                if (sel instanceof String s
                        && !s.startsWith("[")
                        && !s.startsWith("---")) {
                    doSearch();
                }
            }
        });

        cityCombo.getEditor().addActionListener(e -> doSearch());

        card.add(cityCombo, BorderLayout.CENTER);

        searchButton = new FlatButton("Buscar");
        searchButton.addActionListener(e -> doSearch());
        card.add(searchButton, BorderLayout.EAST);

        return card;
    }

    private JPanel buildResultArea() {
        JPanel resultCard = card(BG_CARD);
        resultCard.setLayout(new BorderLayout(0, 14));

        JPanel top = new JPanel(new BorderLayout(8, 0));
        top.setOpaque(false);

        lblCity = new JLabel("—");
        lblCity.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCity.setForeground(TEXT_WHITE);

        lblCondition = new JLabel("Selecione uma cidade ou digite e clique em Buscar");
        lblCondition.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblCondition.setForeground(TEXT_MUTED);

        top.add(lblCity,      BorderLayout.WEST);
        top.add(lblCondition, BorderLayout.EAST);

        lblTemp = new JLabel("—");
        lblTemp.setFont(new Font("Segoe UI", Font.BOLD, 64));
        lblTemp.setForeground(ACCENT_BLUE);
        lblTemp.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel grid = new JPanel(new GridLayout(2, 3, 12, 12));
        grid.setOpaque(false);

        lblTempMax   = new JLabel("—");
        lblTempMin   = new JLabel("—");
        lblHumidity  = new JLabel("—");
        lblPrecip    = new JLabel("—");
        lblWindSpeed = new JLabel("—");
        lblWindDir   = new JLabel("—");

        grid.add(metric(lblTempMax,   "Maxima",       "T+"));
        grid.add(metric(lblTempMin,   "Minima",       "T-"));
        grid.add(metric(lblHumidity,  "Humidade",     "H"));
        grid.add(metric(lblPrecip,    "Precipitacao", "P"));
        grid.add(metric(lblWindSpeed, "Vento",        "V"));
        grid.add(metric(lblWindDir,   "Direcao",      "D"));

        resultCard.add(top,       BorderLayout.NORTH);
        resultCard.add(lblTemp,   BorderLayout.CENTER);
        resultCard.add(grid,      BorderLayout.SOUTH);

        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatus.setForeground(TEXT_MUTED);
        lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel wrap = new JPanel(new BorderLayout(0, 4));
        wrap.setOpaque(false);
        wrap.add(resultCard, BorderLayout.CENTER);
        wrap.add(lblStatus,  BorderLayout.SOUTH);
        return wrap;
    }

    private JPanel buildFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(BG_DARK);
        p.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel l = new JLabel("visualcrossing.com  •  °C  •  km/h  •  mm");
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(TEXT_MUTED);
        p.add(l);
        return p;
    }

    private void doSearch() {
        Object sel = cityCombo.getSelectedItem();
        String query = sel == null ? "" : sel.toString().trim();

        if (query.isEmpty() || query.startsWith("[") || query.startsWith("---")) {
            JOptionPane.showMessageDialog(this,
                "Por favor, selecione ou digite o nome de uma cidade.",
                "Campo obrigatorio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        searchButton.setEnabled(false);
        searchButton.setText("Buscando...");
        lblStatus.setText("Buscando cidades...");
        lblStatus.setForeground(ACCENT_BLUE);

        SwingWorker<WeatherEngine.WeatherData, Void> worker = new SwingWorker<>() {
            @Override
            protected WeatherEngine.WeatherData doInBackground() throws Exception {
                List<WeatherEngine.CityCandidate> candidates = engine.searchCandidates(query);

                WeatherEngine.CityCandidate chosen;
                if (candidates.isEmpty()) {
                    return engine.fetch(query);
                } else if (candidates.size() == 1) {
                    chosen = candidates.get(0);
                } else {
                    chosen = askUserToChoose(candidates);
                    if (chosen == null) return null;
                }

                return engine.fetchByCoords(chosen.lat, chosen.lon);
            }

            @Override
            protected void done() {
                searchButton.setEnabled(true);
                searchButton.setText("Buscar");
                try {
                    WeatherEngine.WeatherData data = get();
                    if (data != null) displayData(data);
                    else {
                        lblStatus.setText("Busca cancelada.");
                        lblStatus.setForeground(TEXT_MUTED);
                    }
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    lblStatus.setText("Erro: " + cause.getMessage());
                    lblStatus.setForeground(COLOR_RED);
                    JOptionPane.showMessageDialog(WeatherGUI.this,
                        cause.getMessage(), "Erro ao buscar clima",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private WeatherEngine.CityCandidate askUserToChoose(
            List<WeatherEngine.CityCandidate> candidates) throws Exception {

        final WeatherEngine.CityCandidate[] result = {null};

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(BG_CARD);
        panel.setBorder(new EmptyBorder(6, 4, 4, 4));

        JLabel msg = new JLabel("Multiplas cidades encontradas. Selecione a desejada:");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        msg.setForeground(TEXT_WHITE);
        panel.add(msg, BorderLayout.NORTH);

        DefaultListModel<WeatherEngine.CityCandidate> model = new DefaultListModel<>();
        candidates.forEach(model::addElement);
        JList<WeatherEngine.CityCandidate> list = new JList<>(model);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        list.setForeground(TEXT_WHITE);
        list.setBackground(BG_CARD2);
        list.setSelectionBackground(ACCENT_BLUE_P);
        list.setSelectionForeground(Color.WHITE);
        list.setFixedCellHeight(32);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                    l, value, index, isSelected, cellHasFocus);
                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                lbl.setForeground(isSelected ? Color.WHITE : TEXT_WHITE);
                lbl.setBackground(isSelected ? ACCENT_BLUE_P : BG_CARD2);
                lbl.setBorder(new EmptyBorder(4, 12, 4, 12));
                return lbl;
            }
        });

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(520, Math.min(candidates.size() * 32 + 4, 220)));
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scroll.getViewport().setBackground(BG_CARD2);
        panel.add(scroll, BorderLayout.CENTER);

        javax.swing.SwingUtilities.invokeAndWait(() -> {
            UIManager.put("OptionPane.background",        BG_CARD);
            UIManager.put("Panel.background",             BG_CARD);
            UIManager.put("OptionPane.messageForeground", TEXT_WHITE);
            UIManager.put("Button.background",            BG_CARD2);
            UIManager.put("Button.foreground",            TEXT_WHITE);

            int opt = JOptionPane.showConfirmDialog(
                WeatherGUI.this, panel,
                "Selecionar cidade",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (opt == JOptionPane.OK_OPTION)
                result[0] = list.getSelectedValue();
        });

        return result[0];
    }

    private void displayData(WeatherEngine.WeatherData d) {
        lblCity.setText(d.resolvedAddress.isEmpty()
            ? cityCombo.getSelectedItem().toString() : d.resolvedAddress);
        lblCondition.setText(d.conditions.isEmpty() ? "—" : d.conditions);
        lblTemp.setText(fmt(d.temp) + " °C");
        lblTempMax.setText(fmt(d.tempMax)    + " °C");
        lblTempMin.setText(fmt(d.tempMin)    + " °C");
        lblHumidity.setText(fmt(d.humidity)  + " %");
        lblPrecip.setText(d.precip == 0 ? "0 mm" : fmt(d.precip) + " mm");
        lblWindSpeed.setText(fmt(d.windSpeed) + " km/h");
        lblWindDir.setText(d.windDirLabel()   + "  (" + fmt(d.windDir) + "°)");

        lblStatus.setForeground(ACCENT_GREEN);
        lblStatus.setText("Atualizado: "
            + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    private JPanel card(Color bg) {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g2d) {
                Graphics2D g = (Graphics2D) g2d.create();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(getBackground());
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g.dispose();
            }
        };
        p.setBackground(bg);
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(16, 20, 16, 20));
        return p;
    }

    private JPanel metric(JLabel valueLabel, String title, String icon) {
        JPanel p = card(BG_CARD2);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel titleLbl = new JLabel(icon + " " + title);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLbl.setForeground(TEXT_MUTED);
        titleLbl.setAlignmentX(LEFT_ALIGNMENT);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        valueLabel.setForeground(TEXT_WHITE);
        valueLabel.setAlignmentX(LEFT_ALIGNMENT);

        p.add(titleLbl);
        p.add(Box.createVerticalStrut(5));
        p.add(valueLabel);
        return p;
    }

    private class HintTextField extends JTextField {
        private final String hint;
        HintTextField(String hint) {
            super(28);
            this.hint = hint;
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setForeground(TEXT_WHITE);
            setBackground(BG_CARD2);
            setCaretColor(TEXT_WHITE);
            setBorder(new EmptyBorder(7, 10, 7, 10));
            setSelectedTextColor(TEXT_WHITE);
            setSelectionColor(ACCENT_BLUE_P);
        }
        @Override protected void paintComponent(Graphics g2d) {
            super.paintComponent(g2d);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g = (Graphics2D) g2d.create();
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setColor(TEXT_MUTED);
                g.setFont(getFont().deriveFont(Font.ITALIC));
                FontMetrics fm = g.getFontMetrics();
                g.drawString(hint, 10, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g.dispose();
            }
        }
    }

    private class HintComboEditor implements ComboBoxEditor {
        private final HintTextField field;
        HintComboEditor() { field = new HintTextField("Digite ou selecione uma cidade..."); }
        @Override public Component getEditorComponent() { return field; }
        @Override public void setItem(Object item) {
            String s = item == null ? "" : item.toString();
            field.setText(s.startsWith("[") || s.startsWith("---") ? "" : s);
        }
        @Override public Object getItem() { return field.getText(); }
        @Override public void selectAll()  { field.selectAll(); }
        @Override public void addActionListener(ActionListener l)    { field.addActionListener(l); }
        @Override public void removeActionListener(ActionListener l) { field.removeActionListener(l); }
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setForeground(TEXT_WHITE);
        combo.setBackground(BG_CARD2);
        combo.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        combo.setUI(new BasicComboBoxUI() {
            @Override protected JButton createArrowButton() {
                JButton btn = new JButton() {
                    @Override public void paint(Graphics g2d) {
                        Graphics2D g = (Graphics2D) g2d.create();
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                        g.setColor(BG_CARD2);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setColor(TEXT_MUTED);
                        g.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        FontMetrics fm = g.getFontMetrics();
                        String arrow = "v";
                        g.drawString(arrow,
                            (getWidth()  - fm.stringWidth(arrow)) / 2,
                            (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                        g.dispose();
                    }
                };
                btn.setPreferredSize(new Dimension(28, 38));
                btn.setBorder(BorderFactory.createEmptyBorder());
                btn.setFocusPainted(false);
                btn.setContentAreaFilled(false);
                btn.setOpaque(true);
                return btn;
            }

            @Override public void installUI(JComponent c) {
                super.installUI(c);
                c.setBackground(BG_CARD2);
            }
        });

        combo.setEditor(new HintComboEditor());

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                String s = value == null ? "" : value.toString();
                boolean isSep = s.startsWith("[") || s.startsWith("---");

                lbl.setFont(new Font("Segoe UI", isSep ? Font.ITALIC : Font.PLAIN, 13));
                lbl.setForeground(isSep ? TEXT_MUTED : TEXT_WHITE);
                lbl.setBackground(isSelected && !isSep ? BG_CARD2.brighter() : BG_CARD);
                lbl.setBorder(new EmptyBorder(5, 12, 5, 12));
                lbl.setEnabled(!isSep);
                return lbl;
            }
        });

        combo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
                    Object popup = combo.getUI().getAccessibleChild(combo, 0);
                    if (popup instanceof JPopupMenu pm) {
                        pm.setBackground(BG_CARD);
                        pm.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
                        for (Component c : pm.getComponents()) {
                            if (c instanceof JScrollPane sp) {
                                sp.getViewport().setBackground(BG_CARD);
                                if (sp.getViewport().getView() instanceof JList<?> jl) {
                                    jl.setBackground(BG_CARD);
                                    jl.setForeground(TEXT_WHITE);
                                    jl.setSelectionBackground(BG_CARD2);
                                    jl.setSelectionForeground(TEXT_WHITE);
                                }
                            }
                        }
                    }
                });
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });
    }

    private String fmt(double v) { return String.format("%.1f", v); }

    private class FlatButton extends JPanel {
        private boolean hovered  = false;
        private boolean pressed  = false;
        private boolean enabled  = true;
        private String  text;
        private final java.util.List<ActionListener> listeners = new java.util.ArrayList<>();

        FlatButton(String text) {
            this.text = text;
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(110, 38));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {
                    if (enabled) { hovered = true;  repaint(); }
                }
                @Override public void mouseExited(MouseEvent e) {
                    hovered = false; repaint();
                }
                @Override public void mousePressed(MouseEvent e) {
                    if (enabled) { pressed = true;  repaint(); }
                }
                @Override public void mouseReleased(MouseEvent e) {
                    if (enabled && pressed) {
                        pressed = false; repaint();
                        ActionEvent ae = new ActionEvent(FlatButton.this,
                            ActionEvent.ACTION_PERFORMED, text);
                        for (ActionListener l : listeners) l.actionPerformed(ae);
                    }
                    pressed = false;
                }
            });
        }

        void addActionListener(ActionListener l)    { listeners.add(l); }
        void removeActionListener(ActionListener l) { listeners.remove(l); }

        void setText(String t) { this.text = t; repaint(); }
        String getText()       { return text; }

        @Override public void setEnabled(boolean b) {
            this.enabled = b;
            setCursor(Cursor.getPredefinedCursor(
                b ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
            repaint();
        }
        @Override public boolean isEnabled() { return enabled; }

        @Override protected void paintComponent(Graphics g2d) {
            Graphics2D g = (Graphics2D) g2d.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Color bg = !enabled ? BG_CARD2
                     : pressed  ? ACCENT_BLUE_P
                     : hovered  ? ACCENT_BLUE_H
                     :            ACCENT_BLUE;

            g.setColor(bg);
            g.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

            g.setColor(enabled ? Color.WHITE : TEXT_MUTED);
            g.setFont(new Font("Segoe UI", Font.BOLD, 13));
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth()  - fm.stringWidth(text)) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g.drawString(text, x, y);
            g.dispose();
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(WeatherGUI::new);
    }
}
