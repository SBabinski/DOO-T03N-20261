
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Interface grafica do aplicativo de clima com animacoes, scroll e tela cheia.
 */
public class WeatherApp extends JFrame {

    // ── CORES ───────────────────────────────────────────────────────────────
    private static final Color C_FUNDO      = new Color(15, 17, 23);
    private static final Color C_FUNDO_CARD = new Color(26, 31, 46);
    private static final Color C_FUNDO_TOPO = new Color(10, 12, 20);
    private static final Color C_AZUL       = new Color(59, 91, 219);
    private static final Color C_TEXTO      = new Color(232, 237, 248);
    private static final Color C_MUTED      = new Color(74, 80, 104);
    private static final Color C_BORDA      = new Color(30, 34, 54);
    private static final Color C_VERMELHO   = new Color(248, 113, 113);
    private static final Color C_AZUL_CLARO = new Color(96, 165, 250);

    // ── COMPONENTES ─────────────────────────────────────────────────────────
    private JTextField campoCidade;
    private JLabel labelCidade, labelData, labelTemp, labelCondicao;
    private JLabel labelUmidade, labelPrecip, labelVento;
    private JLabel labelMax, labelMin, labelStatus;
    private AnimacaoPanel painelAnimacao;

    public WeatherApp() {
        configurarJanela();
        construirInterface();
    }

    // ── JANELA ──────────────────────────────────────────────────────────────
    private void configurarJanela() {
        setTitle("App Clima");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 680);
        setMinimumSize(new Dimension(420, 500));   // tamanho minimo
        setLocationRelativeTo(null);
        setResizable(true);                         // permite redimensionar

        // tecla F11 para alternar tela cheia
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "telaCheia");
        getRootPane().getActionMap().put("telaCheia", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                alternarTelaCheia();
            }
        });
    }

    private void alternarTelaCheia() {
        GraphicsDevice gd = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();
        if (gd.getFullScreenWindow() == null) {
            dispose();
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            gd.setFullScreenWindow(null);
            dispose();
            setUndecorated(false);
            setVisible(true);
        }
    }

    private void construirInterface() {
        setLayout(new BorderLayout());
        add(criarTopo(),   BorderLayout.NORTH);
        add(criarCentro(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);
    }

    // ── TOPO ────────────────────────────────────────────────────────────────
    private JPanel criarTopo() {
        JPanel topo = new JPanel();
        topo.setLayout(new BoxLayout(topo, BoxLayout.Y_AXIS));
        topo.setBackground(C_FUNDO_TOPO);

        // barra mac
        JPanel barraMac = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 10));
        barraMac.setBackground(C_FUNDO_TOPO);
        barraMac.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, C_BORDA));

        for (Color c : new Color[]{
                new Color(255, 95, 87),
                new Color(254, 188, 46),
                new Color(40, 200, 64)}) {
            final Color cor = c;
            JPanel dot = new JPanel() {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(cor);
                    g2.fillOval(0, 0, 12, 12);
                }
            };
            dot.setPreferredSize(new Dimension(12, 12));
            dot.setOpaque(false);
            barraMac.add(dot);
        }

        JLabel titulo = new JLabel("APP CLIMA  ·  v1.0   |   F11 = Tela Cheia");
        titulo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titulo.setForeground(C_MUTED);
        titulo.setBorder(new EmptyBorder(0, 80, 0, 0));
        barraMac.add(titulo);

        // animacao
        painelAnimacao = new AnimacaoPanel();
        painelAnimacao.setPreferredSize(new Dimension(500, 180));

        // busca
        JPanel linhaBusca = new JPanel(new BorderLayout(10, 0));
        linhaBusca.setBackground(C_FUNDO_TOPO);
        linhaBusca.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, C_BORDA),
            new EmptyBorder(12, 16, 12, 16)
        ));

        campoCidade = new JTextField("Curitiba,BR");
        campoCidade.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campoCidade.setBackground(C_FUNDO_CARD);
        campoCidade.setForeground(C_TEXTO);
        campoCidade.setCaretColor(C_TEXTO);
        campoCidade.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDA, 1),
            new EmptyBorder(9, 12, 9, 12)
        ));

        JButton btnBuscar = new JButton("Buscar") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed()  ? C_AZUL.darker()   :
                            getModel().isRollover() ? C_AZUL.brighter() : C_AZUL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setPreferredSize(new Dimension(90, 38));
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBuscar.addActionListener(e -> buscarClima());
        campoCidade.addActionListener(e -> buscarClima());

        linhaBusca.add(campoCidade, BorderLayout.CENTER);
        linhaBusca.add(btnBuscar,   BorderLayout.EAST);

        topo.add(barraMac);
        topo.add(painelAnimacao);
        topo.add(linhaBusca);
        return topo;
    }

    // ── CENTRO COM SCROLL ────────────────────────────────────────────────────
    private JScrollPane criarCentro() {
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(C_FUNDO);
        conteudo.setBorder(new EmptyBorder(20, 20, 20, 20));

        // cidade e data
        labelCidade = label("--", 28, Font.BOLD, C_TEXTO);
        labelData   = label("Digite uma cidade e busque", 12, Font.PLAIN, C_MUTED);
        conteudo.add(labelCidade);
        conteudo.add(Box.createVerticalStrut(3));
        conteudo.add(labelData);
        conteudo.add(Box.createVerticalStrut(14));

        // temperatura
        JPanel rowTemp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rowTemp.setBackground(C_FUNDO);
        rowTemp.setAlignmentX(LEFT_ALIGNMENT);
        labelTemp = label("--", 72, Font.PLAIN, C_TEXTO);
        JLabel sym = label("°C", 22, Font.PLAIN, C_MUTED);
        sym.setBorder(new EmptyBorder(36, 6, 0, 16));
        labelCondicao = label("", 13, Font.PLAIN, new Color(100, 140, 255));
        labelCondicao.setBorder(new EmptyBorder(40, 0, 0, 0));
        rowTemp.add(labelTemp);
        rowTemp.add(sym);
        rowTemp.add(labelCondicao);
        conteudo.add(rowTemp);
        conteudo.add(Box.createVerticalStrut(20));

        // cards umidade / chuva / vento
        JPanel grid3 = new JPanel(new GridLayout(1, 3, 10, 0));
        grid3.setBackground(C_FUNDO);
        grid3.setAlignmentX(LEFT_ALIGNMENT);
        grid3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        labelUmidade = label("--",  20, Font.BOLD, C_TEXTO);
        labelPrecip  = label("--",  20, Font.BOLD, C_TEXTO);
        labelVento   = label("--",  20, Font.BOLD, C_TEXTO);
        grid3.add(card("💧", "UMIDADE", labelUmidade, "do ar"));
        grid3.add(card("🌧", "CHUVA",   labelPrecip,  "mm"));
        grid3.add(card("💨", "VENTO",   labelVento,   "km/h"));
        conteudo.add(grid3);
        conteudo.add(Box.createVerticalStrut(10));

        // cards max / min
        JPanel grid2 = new JPanel(new GridLayout(1, 2, 10, 0));
        grid2.setBackground(C_FUNDO);
        grid2.setAlignmentX(LEFT_ALIGNMENT);
        grid2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        labelMax = label("--°C", 20, Font.BOLD, C_TEXTO);
        labelMin = label("--°C", 20, Font.BOLD, C_TEXTO);
        grid2.add(cardMinMax("↑", "MÁXIMA", labelMax, C_VERMELHO));
        grid2.add(cardMinMax("↓", "MÍNIMA", labelMin, C_AZUL_CLARO));
        conteudo.add(grid2);
        conteudo.add(Box.createVerticalStrut(20));

        // dica de tela cheia
        JLabel dica = label("Pressione F11 para alternar tela cheia", 11, Font.PLAIN, C_MUTED);
        dica.setAlignmentX(CENTER_ALIGNMENT);
        conteudo.add(dica);

        // scroll
        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.setBackground(C_FUNDO);
        scroll.getViewport().setBackground(C_FUNDO);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // estiliza a scrollbar
        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            protected void configureScrollBarColors() {
                thumbColor      = new Color(59, 91, 219);
                trackColor      = new Color(26, 31, 46);
            }
            protected JButton createDecreaseButton(int o) { return botaoInvisivel(); }
            protected JButton createIncreaseButton(int o) { return botaoInvisivel(); }
            private JButton botaoInvisivel() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });

        return scroll;
    }

    // ── RODAPÉ ───────────────────────────────────────────────────────────────
    private JPanel criarRodape() {
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        rodape.setBackground(C_FUNDO_TOPO);
        rodape.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, C_BORDA));
        labelStatus = new JLabel("Digite uma cidade e clique em Buscar");
        labelStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        labelStatus.setForeground(C_MUTED);
        rodape.add(labelStatus);
        return rodape;
    }

    // ── HELPERS ──────────────────────────────────────────────────────────────
    private JLabel label(String txt, int size, int style, Color cor) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", style, size));
        l.setForeground(cor);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JPanel card(String icone, String titulo, JLabel labelValor, String unidade) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(C_FUNDO_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDA, 1),
            new EmptyBorder(12, 14, 12, 14)
        ));
        JLabel ico = new JLabel(icone);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbl.setForeground(C_MUTED);
        JLabel uni = new JLabel(unidade);
        uni.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        uni.setForeground(C_MUTED);
        p.add(ico);
        p.add(Box.createVerticalStrut(4));
        p.add(lbl);
        p.add(Box.createVerticalStrut(4));
        p.add(labelValor);
        p.add(Box.createVerticalStrut(2));
        p.add(uni);
        return p;
    }

    private JPanel cardMinMax(String seta, String titulo, JLabel labelValor, Color cor) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(C_FUNDO_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDA, 1),
            new EmptyBorder(12, 14, 12, 14)
        ));
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        row.setBackground(C_FUNDO_CARD);
        row.setAlignmentX(LEFT_ALIGNMENT);
        JLabel arrow = new JLabel(seta);
        arrow.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        arrow.setForeground(cor);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbl.setForeground(C_MUTED);
        row.add(arrow);
        row.add(lbl);
        p.add(row);
        p.add(Box.createVerticalStrut(4));
        p.add(labelValor);
        return p;
    }

    // ── BUSCA ────────────────────────────────────────────────────────────────
    private void buscarClima() {
        String cidade = campoCidade.getText().trim();
        if (cidade.isEmpty()) {
            labelStatus.setText("Digite o nome de uma cidade.");
            return;
        }
        labelStatus.setText("Buscando dados...");
        campoCidade.setEnabled(false);

        SwingWorker<WeatherData, Void> worker = new SwingWorker<WeatherData, Void>() {
            protected WeatherData doInBackground() {
                return new WeatherService().buscarClima(cidade);
            }
            protected void done() {
                campoCidade.setEnabled(true);
                try {
                    WeatherData d = get();
                    if (d != null) {
                        atualizarInterface(d);
                        labelStatus.setText("Atualizado agora  ·  Visual Crossing API");
                    } else {
                        labelStatus.setText("Cidade não encontrada ou erro na API.");
                    }
                } catch (Exception ex) {
                    labelStatus.setText("Erro: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void atualizarInterface(WeatherData d) {
        labelCidade.setText(d.getCidade());
        String data = LocalDate.now().format(
            DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy",
            new Locale("pt", "BR")));
        labelData.setText(Character.toUpperCase(data.charAt(0)) + data.substring(1));
        labelTemp.setText(String.valueOf((int) d.getTemperaturaAtual()));
        labelCondicao.setText(d.getCondicao());
        labelUmidade.setText(String.format("%.0f%%", d.getUmidade()));
        labelPrecip.setText(d.getPrecipitacao() > 0
            ? String.format("%.1f", d.getPrecipitacao()) : "0");
        labelVento.setText(String.format("%.0f · %s",
            d.getVelocidadeVento(), d.getDirecaoVento()));
        labelMax.setText(String.format("%.1f°C", d.getTempMaxima()));
        labelMin.setText(String.format("%.1f°C", d.getTempMinima()));
        painelAnimacao.setCondicao(d.getCondicao().toLowerCase());
        revalidate();
        repaint();
    }

    // ════════════════════════════════════════════════════════════════════════
    // PAINEL DE ANIMAÇÃO
    // ════════════════════════════════════════════════════════════════════════
    static class AnimacaoPanel extends JPanel {
        private String condicao = "clear";
        private Timer timer;
        private float anguloSol = 0f;
        private float pulseSol  = 0f;
        private final List<Particula> particulas = new ArrayList<Particula>();
        private final Random rand = new Random();

        AnimacaoPanel() {
            setBackground(new Color(13, 24, 41));
            gerarParticulas();
            timer = new Timer(30, e -> {
                anguloSol += 0.5f;
                pulseSol  += 0.05f;
                for (Particula p : particulas) p.atualizar(getHeight(), condicao);
                repaint();
            });
            timer.start();
        }

        void setCondicao(String c) {
            this.condicao = c;
            gerarParticulas();
        }

        private void gerarParticulas() {
            particulas.clear();
            int qtd = condicao.contains("rain") || condicao.contains("drizzle") ? 60 :
                      condicao.contains("snow") ? 40 : 0;
            for (int i = 0; i < qtd; i++) particulas.add(new Particula(rand, condicao));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int cx = w / 2, cy = h / 2 - 10;

            Color c1, c2;
            if (condicao.contains("rain") || condicao.contains("drizzle")) {
                c1 = new Color(20, 25, 40); c2 = new Color(40, 50, 70);
            } else if (condicao.contains("cloud")) {
                c1 = new Color(20, 30, 50); c2 = new Color(50, 60, 80);
            } else if (condicao.contains("snow")) {
                c1 = new Color(30, 35, 55); c2 = new Color(60, 70, 90);
            } else {
                c1 = new Color(13, 24, 41); c2 = new Color(26, 40, 80);
            }

            GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, w, h);

            if (condicao.contains("rain") || condicao.contains("drizzle")) {
                desenharChuva(g2, cx, cy);
            } else if (condicao.contains("snow")) {
                desenharNeve(g2);
            } else if (condicao.contains("cloud")) {
                desenharSolComNuvens(g2, cx, cy);
            } else {
                desenharSol(g2, cx, cy);
            }

            // tag condicao
            String tag = condicao.isEmpty() ? "--" : condicao;
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(tag) + 28;
            int tx = (w - tw) / 2, ty = h - 28;
            g2.setColor(new Color(255, 255, 255, 25));
            g2.fillRoundRect(tx, ty, tw, 22, 20, 20);
            g2.setColor(new Color(160, 174, 192));
            g2.drawString(tag, tx + 14, ty + 15);
        }

        private void desenharSol(Graphics2D g2, int cx, int cy) {
            for (int i = 0; i < 12; i++) {
                double ang = Math.toRadians(anguloSol + i * 30);
                float alpha = 0.15f + 0.1f * (float) Math.sin(pulseSol + i);
                g2.setColor(new Color(1f, 0.85f, 0.2f, Math.min(1f, alpha)));
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int x1 = (int)(cx + 44 * Math.cos(ang));
                int y1 = (int)(cy + 44 * Math.sin(ang));
                int x2 = (int)(cx + 62 * Math.cos(ang));
                int y2 = (int)(cy + 62 * Math.sin(ang));
                g2.drawLine(x1, y1, x2, y2);
            }
            float pulse = (float)(Math.sin(pulseSol) * 6 + 36);
            g2.setColor(new Color(255, 210, 0, 40));
            g2.fillOval((int)(cx - pulse), (int)(cy - pulse),
                        (int)(pulse * 2), (int)(pulse * 2));
            RadialGradientPaint rgp = new RadialGradientPaint(
                cx, cy, 30,
                new float[]{0f, 1f},
                new Color[]{new Color(255, 230, 80), new Color(255, 140, 0)}
            );
            g2.setPaint(rgp);
            g2.fillOval(cx - 30, cy - 30, 60, 60);
        }

        private void desenharSolComNuvens(Graphics2D g2, int cx, int cy) {
            desenharSol(g2, cx - 30, cy - 10);
            desenharNuvem(g2, cx + 20, cy + 10, 1.2f, new Color(180, 195, 220, 220));
            desenharNuvem(g2, cx - 20, cy + 30, 0.8f, new Color(160, 175, 200, 180));
        }

        private void desenharNuvem(Graphics2D g2, int x, int y, float escala, Color cor) {
            g2.setColor(cor);
            int b = (int)(28 * escala), h2 = (int)(18 * escala);
            g2.fillRoundRect(x - b, y - h2 / 2, b * 2, h2, h2, h2);
            g2.fillOval(x - (int)(16 * escala), y - (int)(20 * escala),
                        (int)(30 * escala), (int)(26 * escala));
            g2.fillOval(x + (int)(2  * escala), y - (int)(14 * escala),
                        (int)(22 * escala), (int)(20 * escala));
        }

        private void desenharChuva(Graphics2D g2, int cx, int cy) {
            desenharNuvem(g2, cx, cy - 20, 1.5f, new Color(80, 95, 120, 220));
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (Particula p : particulas) {
                g2.setColor(new Color(100, 160, 255, (int)(180 * p.alpha)));
                g2.drawLine((int)p.x, (int)p.y, (int)(p.x - 3), (int)(p.y + 12));
            }
        }

        private void desenharNeve(Graphics2D g2) {
            for (Particula p : particulas) {
                g2.setColor(new Color(220, 235, 255, (int)(200 * p.alpha)));
                int r = (int)(p.tamanho / 2);
                g2.fillOval((int)p.x - r, (int)p.y - r, r * 2, r * 2);
            }
        }
    }

    // ── PARTÍCULA ────────────────────────────────────────────────────────────
    static class Particula {
        float x, y, velX, velY, alpha, tamanho;
        Random rand;

        Particula(Random rand, String condicao) {
            this.rand = rand;
            reiniciar(condicao, true);
        }

        void reiniciar(String condicao, boolean aleatorio) {
            x = rand.nextInt(500);
            y = aleatorio ? rand.nextInt(200) : -10;
            if (condicao.contains("snow")) {
                velX = (rand.nextFloat() - 0.5f) * 1.5f;
                velY = rand.nextFloat() * 1.5f + 0.5f;
                tamanho = rand.nextFloat() * 5 + 2;
            } else {
                velX = -2f;
                velY = rand.nextFloat() * 8 + 8;
                tamanho = 2;
            }
            alpha = rand.nextFloat() * 0.6f + 0.4f;
        }

        void atualizar(int altura, String condicao) {
            x += velX;
            y += velY;
            if (y > altura) reiniciar(condicao, false);
        }
    }

    // ── MAIN ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WeatherApp().setVisible(true);
            }
        });
    }
}