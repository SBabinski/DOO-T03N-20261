import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import javax.swing.Timer;

public class Main extends JFrame {

    // Paleta de cores
    private static final Color BG_DEEP      = new Color(0x0D1B2A);   // azul-noite fundo
    private static final Color BG_CARD      = new Color(0x122436);   // card ligeiramente mais claro
    private static final Color BG_INPUT     = new Color(0x0A1520);   // campo de texto
    private static final Color ACCENT_CYAN  = new Color(0x38BDF8);   // ciano glacial
    private static final Color ACCENT_LIGHT = new Color(0x7DD3FC);   // ciano suave p/ labels
    private static final Color TEXT_PRIMARY = new Color(0xF0F9FF);   // branco gelo
    private static final Color TEXT_DIM     = new Color(0x64748B);   // cinza ardósia
    private static final Color DANGER       = new Color(0xF87171);   // vermelho suave
    private static final Color SUCCESS      = new Color(0x34D399);   // verde menta
    private static final Color DIVIDER      = new Color(0x1E3A5F);   // divisor sutil

    // Fontes
    private static Font fontDisplay;
    private static Font fontBody;
    private static Font fontMono;

    // Componentes reutilizados
    private JTextField campoNome, campoCidade, campoEstado, campoChave;
    private JPasswordField campoChaveField;
    private JButton botaoBuscar;
    private JPanel painelResultado;
    private JLabel lblIconeClima, lblTemperatura, lblCondicao;
    private JLabel lblMaxMin, lblUmidade, lblChuva, lblVento, lblDirecao;
    private JLabel lblStatus;
    private JLabel lblSaudacao;

    // Estado
    private boolean buscando = false;

    public static void main(String[] args) {
        carregarFontes();
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }

    public Main() {
        configurarJanela();
        construirUI();
    }

    //  Janela
    private void configurarJanela() {
        setTitle("Atmosfera");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(820, 560));
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(null);

        try {
            setShape(new RoundRectangle2D.Double(0, 0, 900, 600, 24, 24));
        } catch (Exception ignored) {}

        addDragSupport();
    }

    private void addDragSupport() {
        final Point[] dragOrigin = {null};
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { dragOrigin[0] = e.getPoint(); }
            public void mouseReleased(MouseEvent e) { dragOrigin[0] = null; }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragOrigin[0] != null) {
                    Point loc = getLocation();
                    setLocation(loc.x + e.getX() - dragOrigin[0].x,
                            loc.y + e.getY() - dragOrigin[0].y);
                }
            }
        });
    }

    //  Contrução da UI
    private void construirUI() {
        JPanel root = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_DEEP);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.dispose();
            }
        };
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(root);

        root.add(criarHeader(), BorderLayout.NORTH);

        JPanel corpo = new JPanel(new GridLayout(1, 2, 0, 0));
        corpo.setOpaque(false);
        corpo.add(criarPainelEsquerdo());
        corpo.add(criarPainelDireito());
        root.add(corpo, BorderLayout.CENTER);

        root.add(criarStatusBar(), BorderLayout.SOUTH);
    }

    // Header
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(20, 28, 16, 20));

        // logo + título
        JPanel logo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logo.setOpaque(false);

        JLabel icone = new JLabel("🌤");
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JLabel titulo = new JLabel("Atmosfera");
        titulo.setFont(fontDisplay != null ? fontDisplay.deriveFont(Font.BOLD, 22f)
                : new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(TEXT_PRIMARY);

        JLabel subtitulo = new JLabel("  clima em tempo real");
        subtitulo.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 12f)
                : new Font("Segoe UI", Font.PLAIN, 12));
        subtitulo.setForeground(TEXT_DIM);

        logo.add(icone);
        logo.add(titulo);
        logo.add(subtitulo);
        header.add(logo, BorderLayout.WEST);

        // botão fechar
        JButton fechar = criarBotaoIcone("✕");
        fechar.addActionListener(e -> System.exit(0));
        header.add(fechar, BorderLayout.EAST);

        // linha divisória
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(header, BorderLayout.CENTER);
        wrapper.add(criarDivisor(), BorderLayout.SOUTH);
        return wrapper;
    }

    // Painel esquerdo
    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(24, 28, 24, 20));

        lblSaudacao = new JLabel("Olá! Como posso te chamar?");
        lblSaudacao.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 13f)
                : new Font("Segoe UI", Font.PLAIN, 13));
        lblSaudacao.setForeground(ACCENT_LIGHT);
        lblSaudacao.setAlignmentX(LEFT_ALIGNMENT);
        painel.add(lblSaudacao);
        painel.add(Box.createVerticalStrut(8));

        campoNome = criarCampo("Seu nome");
        painel.add(campoNome);
        painel.add(Box.createVerticalStrut(20));

        painel.add(criarLabel("Cidade"));
        painel.add(Box.createVerticalStrut(6));
        campoCidade = criarCampo("ex: São Paulo");
        painel.add(campoCidade);
        painel.add(Box.createVerticalStrut(14));

        painel.add(criarLabel("Estado  " + dim("(opcional)")));
        painel.add(Box.createVerticalStrut(6));
        campoEstado = criarCampo("ex: SP");
        painel.add(campoEstado);
        painel.add(Box.createVerticalStrut(14));

        painel.add(criarLabel("Chave da API"));
        painel.add(Box.createVerticalStrut(6));
        campoChave = criarCampo("Sua chave Visual Crossing");
        painel.add(campoChave);
        painel.add(Box.createVerticalStrut(28));

        botaoBuscar = criarBotaoPrimario("Consultar clima");
        botaoBuscar.setAlignmentX(LEFT_ALIGNMENT);
        botaoBuscar.addActionListener(e -> executarBusca());
        painel.add(botaoBuscar);

        ActionListener buscar = e -> executarBusca();
        campoNome.addActionListener(buscar);
        campoCidade.addActionListener(buscar);
        campoEstado.addActionListener(buscar);
        campoChave.addActionListener(buscar);

        return painel;
    }

    // Painel direito
    private JPanel criarPainelDireito() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);

        JPanel linha = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(DIVIDER);
                g.fillRect(0, 20, 1, getHeight() - 40);
            }
        };
        linha.setPreferredSize(new Dimension(1, 0));
        linha.setOpaque(false);
        outer.add(linha, BorderLayout.WEST);

        painelResultado = new JPanel();
        painelResultado.setLayout(new BoxLayout(painelResultado, BoxLayout.Y_AXIS));
        painelResultado.setOpaque(false);
        painelResultado.setBorder(new EmptyBorder(24, 24, 24, 28));

        exibirPlaceholder();

        outer.add(painelResultado, BorderLayout.CENTER);
        return outer;
    }

    private void exibirPlaceholder() {
        painelResultado.removeAll();

        JLabel ph = new JLabel("☁");
        ph.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        ph.setForeground(new Color(0x1E3A5F));
        ph.setAlignmentX(CENTER_ALIGNMENT);

        JLabel msg = new JLabel("<html><center>Digite uma cidade<br>para ver o clima ao vivo</center></html>");
        msg.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 13f)
                : new Font("Segoe UI", Font.PLAIN, 13));
        msg.setForeground(TEXT_DIM);
        msg.setAlignmentX(CENTER_ALIGNMENT);

        painelResultado.add(Box.createVerticalGlue());
        painelResultado.add(ph);
        painelResultado.add(Box.createVerticalStrut(12));
        painelResultado.add(msg);
        painelResultado.add(Box.createVerticalGlue());
        painelResultado.revalidate();
        painelResultado.repaint();
    }

    private void exibirResultado(Clima c, String cidade) {
        painelResultado.removeAll();
        String nomeUser = campoNome.getText().trim();
        if (nomeUser.isEmpty()) nomeUser = "viajante";

        // cidade e saudação
        JLabel lblCidade = new JLabel(cidade.toUpperCase());
        lblCidade.setFont(fontMono != null ? fontMono.deriveFont(Font.BOLD, 11f)
                : new Font("Monospaced", Font.BOLD, 11));
        lblCidade.setForeground(ACCENT_CYAN);
        lblCidade.setAlignmentX(LEFT_ALIGNMENT);

        // ícone de clima
        lblIconeClima = new JLabel(iconeParaCondicao(c.getDescricao()));
        lblIconeClima.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));
        lblIconeClima.setAlignmentX(LEFT_ALIGNMENT);

        // temperatura grande
        lblTemperatura = new JLabel(String.format("%.0f°", c.getTempAtual()));
        lblTemperatura.setFont(fontDisplay != null ? fontDisplay.deriveFont(Font.BOLD, 64f)
                : new Font("Segoe UI", Font.BOLD, 64));
        lblTemperatura.setForeground(TEXT_PRIMARY);
        lblTemperatura.setAlignmentX(LEFT_ALIGNMENT);

        // condição
        lblCondicao = new JLabel(traduzirCondicao(c.getDescricao()));
        lblCondicao.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 14f)
                : new Font("Segoe UI", Font.PLAIN, 14));
        lblCondicao.setForeground(ACCENT_LIGHT);
        lblCondicao.setAlignmentX(LEFT_ALIGNMENT);

        painelResultado.add(lblCidade);
        painelResultado.add(Box.createVerticalStrut(10));
        painelResultado.add(lblIconeClima);
        painelResultado.add(Box.createVerticalStrut(4));
        painelResultado.add(lblTemperatura);
        painelResultado.add(lblCondicao);
        painelResultado.add(Box.createVerticalStrut(18));
        painelResultado.add(criarDivisorH());
        painelResultado.add(Box.createVerticalStrut(16));

        // grid de métricas 2×3
        JPanel grid = new JPanel(new GridLayout(3, 2, 12, 10));
        grid.setOpaque(false);
        grid.setAlignmentX(LEFT_ALIGNMENT);
        grid.setMaximumSize(new Dimension(360, 140));

        grid.add(criarMetrica("🔺 Máxima",  String.format("%.1f°C", c.getTempMax())));
        grid.add(criarMetrica("🔻 Mínima",  String.format("%.1f°C", c.getTempMin())));
        grid.add(criarMetrica("💧 Umidade", String.format("%.0f%%",  c.getUmidade())));
        grid.add(criarMetrica("🌧 Chuva",   String.format("%.1fmm", c.getPrecipitacao())));
        grid.add(criarMetrica("💨 Vento",   String.format("%.0f km/h", c.getVelocidadeVento())));
        grid.add(criarMetrica("🧭 Direção", grausParaDirecao(c.getDirecaoVento())));

        painelResultado.add(grid);
        painelResultado.add(Box.createVerticalStrut(16));
        painelResultado.add(criarDivisorH());
        painelResultado.add(Box.createVerticalStrut(12));

        // dica contextual
        JLabel dica = new JLabel("<html><span style='color:#94A3B8'>💡 </span>"
                + gerarDica(c, nomeUser) + "</html>");
        dica.setFont(fontBody != null ? fontBody.deriveFont(Font.ITALIC, 12f)
                : new Font("Segoe UI", Font.ITALIC, 12));
        dica.setForeground(new Color(0x94A3B8));
        dica.setAlignmentX(LEFT_ALIGNMENT);
        painelResultado.add(dica);

        painelResultado.revalidate();
        painelResultado.repaint();
    }

    private void exibirErroResultado(String msg) {
        painelResultado.removeAll();

        JLabel icone = new JLabel("⚠");
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        icone.setForeground(DANGER);
        icone.setAlignmentX(CENTER_ALIGNMENT);

        JLabel texto = new JLabel("<html><center>" + msg + "</center></html>");
        texto.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 13f)
                : new Font("Segoe UI", Font.PLAIN, 13));
        texto.setForeground(DANGER);
        texto.setAlignmentX(CENTER_ALIGNMENT);

        painelResultado.add(Box.createVerticalGlue());
        painelResultado.add(icone);
        painelResultado.add(Box.createVerticalStrut(10));
        painelResultado.add(texto);
        painelResultado.add(Box.createVerticalGlue());
        painelResultado.revalidate();
        painelResultado.repaint();
    }

    // Status
    private JPanel criarStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(new EmptyBorder(0, 28, 14, 28));

        lblStatus = new JLabel("Pronto para consultar");
        lblStatus.setFont(fontMono != null ? fontMono.deriveFont(Font.PLAIN, 10f)
                : new Font("Monospaced", Font.PLAIN, 10));
        lblStatus.setForeground(TEXT_DIM);

        JLabel versao = new JLabel("Atmosfera v1.0  ·  Visual Crossing API");
        versao.setFont(fontMono != null ? fontMono.deriveFont(Font.PLAIN, 10f)
                : new Font("Monospaced", Font.PLAIN, 10));
        versao.setForeground(new Color(0x1E3A5F));

        bar.add(lblStatus, BorderLayout.WEST);
        bar.add(versao, BorderLayout.EAST);
        return bar;
    }

    // Lógica de busca
    private void executarBusca() {
        if (buscando) return;

        String nome   = campoNome.getText().trim();
        String cidade = campoCidade.getText().trim();
        String estado = campoEstado.getText().trim();
        String chave  = campoChave.getText().trim();

        // saudação
        if (!nome.isEmpty()) {
            lblSaudacao.setText("Consultando para " + capitalize(nome) + " ✦");
        }

        // validação antes de chamar API
        if (cidade.isEmpty()) {
            exibirErroResultado("Informe o nome de uma cidade.");
            setStatus("⚠ Cidade não informada");
            return;
        }
        if (chave.isEmpty()) {
            exibirErroResultado("Insira sua chave da API Visual Crossing.");
            setStatus("⚠ Chave da API ausente");
            return;
        }

        buscando = true;
        botaoBuscar.setEnabled(false);
        botaoBuscar.setText("Buscando...");
        setStatus("🔍 Buscando dados de " + cidade + "...");

        exibirLoading(cidade);

        // busca em thread separada
        new Thread(() -> {
            try {
                ClimaApi api   = new ClimaApi();
                Clima clima    = api.consultarClima(cidade, estado, chave);
                SwingUtilities.invokeLater(() -> {
                    String titulo = cidade + (estado.isBlank() ? "" : " — " + estado.toUpperCase());
                    exibirResultado(clima, titulo);
                    setStatus("✓ Dados atualizados com sucesso");
                    resetBotao();
                });
            } catch (ClimaException e) {
                SwingUtilities.invokeLater(() -> {
                    exibirErroResultado(e.getMessage());
                    setStatus("✗ " + e.getMessage());
                    resetBotao();
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    exibirErroResultado("Erro de conexão. Verifique a internet.");
                    setStatus("✗ Falha na conexão");
                    resetBotao();
                });
            }
        }).start();
    }

    private void exibirLoading(String cidade) {
        painelResultado.removeAll();

        JLabel spin = new JLabel("⟳");
        spin.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        spin.setForeground(ACCENT_CYAN);
        spin.setAlignmentX(CENTER_ALIGNMENT);

        JLabel msg = new JLabel("<html><center>Buscando clima em<br><b>"
                + cidade + "</b></center></html>");
        msg.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 13f)
                : new Font("Segoe UI", Font.PLAIN, 13));
        msg.setForeground(TEXT_DIM);
        msg.setAlignmentX(CENTER_ALIGNMENT);

        // animação de rotação via timer
        Timer t = new Timer(80, null);
        String[] frames = {"⟳", "↻", "⟲", "↺"};
        int[] idx = {0};
        t.addActionListener(e -> {
            spin.setText(frames[idx[0]++ % frames.length]);
        });
        t.start();

        painelResultado.add(Box.createVerticalGlue());
        painelResultado.add(spin);
        painelResultado.add(Box.createVerticalStrut(12));
        painelResultado.add(msg);
        painelResultado.add(Box.createVerticalGlue());
        painelResultado.revalidate();
        painelResultado.repaint();
    }

    private void resetBotao() {
        buscando = false;
        botaoBuscar.setEnabled(true);
        botaoBuscar.setText("Consultar clima");
    }

    private void setStatus(String msg) {
        lblStatus.setText(msg);
    }

    //  FACTORY HELPERS
    private JTextField criarCampo(String placeholder) {
        JTextField campo = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_INPUT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    g2.setColor(TEXT_DIM);
                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(placeholder, 12, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                }
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isFocusOwner() ? ACCENT_CYAN : DIVIDER);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        campo.setOpaque(false);
        campo.setForeground(TEXT_PRIMARY);
        campo.setCaretColor(ACCENT_CYAN);
        campo.setBorder(new EmptyBorder(10, 12, 10, 12));
        campo.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 13f)
                : new Font("Segoe UI", Font.PLAIN, 13));
        campo.setAlignmentX(LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { campo.repaint(); }
            public void focusLost(FocusEvent e)   { campo.repaint(); }
        });
        return campo;
    }

    private JLabel criarLabel(String texto) {
        JLabel l = new JLabel("<html>" + texto + "</html>");
        l.setFont(fontBody != null ? fontBody.deriveFont(Font.PLAIN, 12f)
                : new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(ACCENT_LIGHT);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto) {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = !isEnabled() ? TEXT_DIM
                        : hover ? ACCENT_LIGHT : ACCENT_CYAN;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(BG_DEEP);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(fontBody != null ? fontBody.deriveFont(Font.BOLD, 14f)
                : new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(BG_DEEP);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(220, 44));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return btn;
    }

    private JButton criarBotaoIcone(String icone) {
        JButton btn = new JButton(icone) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0x1E3A5F) : new Color(0,0,0,0));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(TEXT_DIM);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(32, 32));
        return btn;
    }

    private JPanel criarMetrica(String label, String valor) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel lbl = new JLabel(label);
        lbl.setFont(fontMono != null ? fontMono.deriveFont(Font.PLAIN, 10f)
                : new Font("Monospaced", Font.PLAIN, 10));
        lbl.setForeground(TEXT_DIM);

        JLabel val = new JLabel(valor);
        val.setFont(fontBody != null ? fontBody.deriveFont(Font.BOLD, 15f)
                : new Font("Segoe UI", Font.BOLD, 15));
        val.setForeground(TEXT_PRIMARY);

        card.add(lbl);
        card.add(val);
        return card;
    }

    private JPanel criarDivisor() {
        JPanel d = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(DIVIDER);
                g.fillRect(0, 0, getWidth(), 1);
            }
        };
        d.setPreferredSize(new Dimension(0, 1));
        d.setOpaque(false);
        return d;
    }

    private JPanel criarDivisorH() {
        JPanel d = criarDivisor();
        d.setAlignmentX(LEFT_ALIGNMENT);
        d.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return d;
    }

    //  Utilitários
    private static String iconeParaCondicao(String c) {
        String s = c.toLowerCase();
        if (s.contains("rain") || s.contains("drizzle")) return "🌧";
        if (s.contains("snow") || s.contains("sleet"))   return "❄";
        if (s.contains("storm")|| s.contains("thunder")) return "⛈";
        if (s.contains("cloud")|| s.contains("overcast"))return "☁";
        if (s.contains("fog")  || s.contains("mist"))    return "🌫";
        if (s.contains("clear")|| s.contains("sun"))     return "☀";
        return "🌤";
    }

    private static String traduzirCondicao(String c) {
        String s = c.toLowerCase();
        if (s.contains("clear"))          return "Céu limpo";
        if (s.contains("sunny"))          return "Ensolarado";
        if (s.contains("partly cloudy"))  return "Parcialmente nublado";
        if (s.contains("overcast"))       return "Encoberto";
        if (s.contains("rain"))           return "Chuva";
        if (s.contains("drizzle"))        return "Garoa";
        if (s.contains("snow"))           return "Neve";
        if (s.contains("storm"))          return "Tempestade";
        if (s.contains("thunder"))        return "Trovoada";
        if (s.contains("fog"))            return "Nevoeiro";
        if (s.contains("cloud"))          return "Nublado";
        return c;
    }

    private static String grausParaDirecao(double g) {
        String[] d = {"Norte","NE","Leste","SE","Sul","SO","Oeste","NO"};
        return d[(int) Math.round(g / 45) % 8];
    }

    private static String gerarDica(Clima c, String nome) {
        if (c.getPrecipitacao() > 10) return nome + ", leve guarda-chuva hoje!";
        if (c.getTempAtual() >= 35) return "Calor intenso — hidrate-se bastante, " + nome + "!";
        if (c.getTempAtual() <= 10) return "Está frio! Agasalhe-se bem, " + nome + ".";
        if (c.getUmidade() < 30)    return "Ar seco. Beba mais água, " + nome + ".";
        if (c.getVelocidadeVento() > 50) return "Vento forte hoje. Cuidado ao sair, " + nome + ".";
        String[] motivacionais = {
                "Bom dia para aproveitar o clima, " + nome + "!",
                "O céu é lindo hoje, " + nome + ". Aproveite!",
                "Tempo agradável — ótimo para atividades ao ar livre!"
        };
        return motivacionais[new Random().nextInt(motivacionais.length)];
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    private static String dim(String t) {
        return "<span style='color:#64748B'>" + t + "</span>";
    }

    private static void carregarFontes() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] disponiveis = ge.getAvailableFontFamilyNames();
        fontDisplay = escolherFonte(disponiveis, new String[]{"Inter","Segoe UI","SF Pro Display","Helvetica Neue","Arial"}, Font.BOLD,   22f);
        fontBody    = escolherFonte(disponiveis, new String[]{"Inter","Segoe UI","SF Pro Text","Helvetica Neue","Arial"},   Font.PLAIN,  13f);
        fontMono    = escolherFonte(disponiveis, new String[]{"JetBrains Mono","Fira Code","Consolas","Courier New"},       Font.PLAIN,  11f);
    }

    private static Font escolherFonte(String[] disponiveis, String[] candidatos, int style, float size) {
        java.util.Set<String> set = new java.util.HashSet<>(java.util.Arrays.asList(disponiveis));
        for (String c : candidatos) {
            if (set.contains(c)) return new Font(c, style, (int) size).deriveFont(size);
        }
        return new Font("SansSerif", style, (int) size);
    }
}