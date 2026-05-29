package calculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

public class CalculatorGUI extends JFrame {

    private static final Color COR_FUNDO_APP      = new Color(0x1C, 0x1C, 0x1C);
    private static final Color COR_FUNDO_DISPLAY  = new Color(0x1C, 0x1C, 0x1C);
    private static final Color COR_BOTAO_NUMERO   = new Color(0x33, 0x33, 0x33);
    private static final Color COR_BOTAO_NUM_HOVER= new Color(0x44, 0x44, 0x44);
    private static final Color COR_BOTAO_OP       = new Color(0x28, 0x28, 0x28);
    private static final Color COR_BOTAO_OP_HOVER = new Color(0x3A, 0x3A, 0x3A);
    private static final Color COR_BOTAO_DESTAQUE = new Color(0xFF, 0x6D, 0x00);
    private static final Color COR_BOTAO_DEST_H   = new Color(0xFF, 0x8C, 0x2A);
    private static final Color COR_TEXTO_PRINCIPAL= new Color(0xFF, 0xFF, 0xFF);
    private static final Color COR_TEXTO_SECUNDAR = new Color(0xAA, 0xAA, 0xAA);
    private static final Color COR_OP_SELECIONADO = new Color(0xFF, 0x6D, 0x00);

    private static final Font FONTE_DISPLAY   = new Font("Segoe UI", Font.PLAIN, 52);
    private static final Font FONTE_EXPRESSAO = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONTE_BOTAO     = new Font("Segoe UI", Font.PLAIN, 20);
    private static final Font FONTE_BOTAO_P   = new Font("Segoe UI", Font.PLAIN, 15);

    
    private double primeiroOperando    = 0;
    private String operadorAtual       = null;
    private boolean iniciarNovaEntrada = true;

    private final CalculatorEngine motor     = new CalculatorEngine();
    private final DecimalFormat formatador   = new DecimalFormat("#.##########");

    private JLabel labelDisplay;
    private JLabel labelExpressao;
    private JButton ultimoBotaoOp = null;

    public CalculatorGUI() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        JPanel painelRaiz = new JPanel(new BorderLayout());
        painelRaiz.setBackground(COR_FUNDO_APP);
        painelRaiz.setBorder(BorderFactory.createLineBorder(new Color(0x55, 0x55, 0x55), 1));

        painelRaiz.add(construirBarraTitulo(), BorderLayout.NORTH);
        painelRaiz.add(construirDisplay(),     BorderLayout.CENTER);
        painelRaiz.add(construirGradeBotoes(), BorderLayout.SOUTH);

        setContentPane(painelRaiz);
        pack();
        setSize(320, 540);
        setLocationRelativeTo(null);
        adicionarSuporteArrasto(painelRaiz);
    }

    private JPanel construirBarraTitulo() {
        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(COR_FUNDO_APP);
        barra.setPreferredSize(new Dimension(320, 36));
        barra.setBorder(new EmptyBorder(0, 16, 0, 0));

        JLabel titulo = new JLabel("Calculadora");
        titulo.setForeground(COR_TEXTO_PRINCIPAL);
        titulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        barra.add(titulo, BorderLayout.WEST);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controles.setBackground(COR_FUNDO_APP);
        controles.add(criarBotaoTitulo("—", COR_BOTAO_OP, COR_BOTAO_OP_HOVER,
                e -> setState(Frame.ICONIFIED)));
        controles.add(criarBotaoTitulo("X", COR_BOTAO_OP, new Color(0xC4, 0x2B, 0x1A),
                e -> System.exit(0)));
        barra.add(controles, BorderLayout.EAST);
        return barra;
    }

    private JButton criarBotaoTitulo(String texto, Color fundo, Color hover, ActionListener acao) {
        JButton b = new JButton(texto) {
            private boolean sobreBtn = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { sobreBtn = true;  repaint(); }
                public void mouseExited (MouseEvent e) { sobreBtn = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                g.setColor(sobreBtn ? hover : fundo);
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        b.setForeground(COR_TEXTO_PRINCIPAL);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setPreferredSize(new Dimension(46, 36));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.addActionListener(acao);
        return b;
    }

    private JPanel construirDisplay() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(COR_FUNDO_DISPLAY);
        painel.setBorder(new EmptyBorder(12, 16, 8, 16));

        painel.setMinimumSize(new Dimension(320, 110));
        painel.setPreferredSize(new Dimension(320, 110));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        labelExpressao = new JLabel(" ");
        labelExpressao.setFont(FONTE_EXPRESSAO);
        labelExpressao.setForeground(COR_TEXTO_SECUNDAR);
        labelExpressao.setAlignmentX(Component.RIGHT_ALIGNMENT);

        labelDisplay = new JLabel("0");
        labelDisplay.setFont(FONTE_DISPLAY);
        labelDisplay.setForeground(COR_TEXTO_PRINCIPAL);
        labelDisplay.setAlignmentX(Component.RIGHT_ALIGNMENT);

        painel.add(Box.createVerticalGlue());
        painel.add(labelExpressao);
        painel.add(Box.createVerticalStrut(2));
        painel.add(labelDisplay);
        painel.add(Box.createVerticalStrut(10));
        return painel;
    }

    private JPanel construirGradeBotoes() {
        JPanel grade = new JPanel(new GridLayout(5, 4, 1, 1));
        grade.setBackground(new Color(0x11, 0x11, 0x11));
        grade.setBorder(new EmptyBorder(1, 0, 0, 0));

        Font fonteSans  = new Font("SansSerif", Font.BOLD, 18);
        Font fonteSansP = new Font("SansSerif", Font.BOLD, 15);
        grade.add(criarBotao("CE", COR_BOTAO_OP, COR_BOTAO_OP_HOVER, fonteSansP, e -> tratarLimparEntrada()));
        grade.add(criarBotao("C",  COR_BOTAO_OP, COR_BOTAO_OP_HOVER, fonteSans,  e -> tratarLimparTudo()));
        grade.add(criarBotao("<",  COR_BOTAO_OP, COR_BOTAO_OP_HOVER, fonteSans,  e -> tratarApagar()));
        grade.add(criarBotaoOperador("÷"));

        grade.add(criarBotaoNumero("7")); grade.add(criarBotaoNumero("8")); grade.add(criarBotaoNumero("9"));
        grade.add(criarBotaoOperador("×"));

        grade.add(criarBotaoNumero("4")); grade.add(criarBotaoNumero("5")); grade.add(criarBotaoNumero("6"));
        grade.add(criarBotaoOperador("-"));

        grade.add(criarBotaoNumero("1")); grade.add(criarBotaoNumero("2")); grade.add(criarBotaoNumero("3"));
        grade.add(criarBotaoOperador("+"));

        grade.add(criarBotao("+/−", COR_BOTAO_NUMERO,   COR_BOTAO_NUM_HOVER, FONTE_BOTAO_P, e -> tratarInverterSinal()));
        grade.add(criarBotaoNumero("0"));
        grade.add(criarBotao(".",   COR_BOTAO_NUMERO,   COR_BOTAO_NUM_HOVER, FONTE_BOTAO,   e -> tratarPonto()));
        grade.add(criarBotao("=",   COR_BOTAO_DESTAQUE, COR_BOTAO_DEST_H,    FONTE_BOTAO,   e -> tratarIgual()));

        return grade;
    }

    private JButton criarBotaoNumero(String digito) {
        return criarBotao(digito, COR_BOTAO_NUMERO, COR_BOTAO_NUM_HOVER, FONTE_BOTAO,
                e -> tratarDigito(digito));
    }

    private JButton criarBotaoOperador(String operador) {
        JButton b = criarBotao(operador, COR_BOTAO_OP, COR_BOTAO_OP_HOVER, FONTE_BOTAO,
                e -> tratarOperador(operador));
        b.putClientProperty("botaoOp", operador);
        return b;
    }

    private JButton criarBotao(String texto, Color fundo, Color hover, Font fonte, ActionListener acao) {
        JButton b = new JButton(texto) {
            private boolean sobreBtn   = false;
            private boolean pressionado = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered (MouseEvent e) { sobreBtn    = true;  repaint(); }
                    public void mouseExited  (MouseEvent e) { sobreBtn    = false; pressionado = false; repaint(); }
                    public void mousePressed (MouseEvent e) { pressionado = true;  repaint(); }
                    public void mouseReleased(MouseEvent e) { pressionado = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color corBase = (Color) getClientProperty("corSobreposta");
                if (corBase == null) corBase = fundo;
                Color corFinal = pressionado ? corBase.darker() : (sobreBtn ? hover : corBase);
                g2.setColor(corFinal);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(fonte);
        b.setForeground(COR_TEXTO_PRINCIPAL);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(79, 74));
        b.addActionListener(acao);
        return b;
    }

    
    private void tratarDigito(String digito) {
        if (iniciarNovaEntrada) {

            labelDisplay.setText(digito);
            iniciarNovaEntrada = false;
        } else {
            if (!labelDisplay.getText().equals("0")) {
                if (labelDisplay.getText().length() < 15)
                    labelDisplay.setText(labelDisplay.getText() + digito);
            } else {

                labelDisplay.setText(digito);
            }
        }
    }

    
    private void tratarPonto() {
        if (iniciarNovaEntrada) {
            labelDisplay.setText("0.");
            iniciarNovaEntrada = false;
            return;
        }
        if (!labelDisplay.getText().contains("."))
            labelDisplay.setText(labelDisplay.getText() + ".");
    }

    
    private void tratarOperador(String operador) {
        try {
            double valorAtual = motor.converterEntrada(labelDisplay.getText());

            if (operadorAtual != null && !iniciarNovaEntrada) {

                double resultadoParcial = motor.calcular(primeiroOperando, valorAtual, operadorAtual);
                labelDisplay.setText(formatarResultado(resultadoParcial));
                labelExpressao.setText(formatarResultado(resultadoParcial) + " " + operador);
                primeiroOperando = resultadoParcial;
            } else {

                primeiroOperando = valorAtual;
                labelExpressao.setText(formatarResultado(primeiroOperando) + " " + operador);
            }

            operadorAtual    = operador;
            iniciarNovaEntrada = true;
            destacarBotaoOperador(operador);

        } catch (CalculatorException ex) {
            exibirErro(ex);
        }
    }

    
    private void tratarIgual() {
        if (operadorAtual == null) return;
        try {
            double segundoOperando = motor.converterEntrada(labelDisplay.getText());
            double resultado = motor.calcular(primeiroOperando, segundoOperando, operadorAtual);

            labelExpressao.setText(
                formatarResultado(primeiroOperando) + " " + operadorAtual +
                " " + formatarResultado(segundoOperando) + " ="
            );
            labelDisplay.setText(formatarResultado(resultado));

            primeiroOperando   = resultado;
            operadorAtual      = null;
            iniciarNovaEntrada = true;
            limparDestaqueBotaoOp();

        } catch (CalculatorException ex) {
            tratarExcecaoCalculadora(ex);
        }
    }

    
    private void tratarLimparTudo() {
        labelDisplay.setText("0");
        labelExpressao.setText(" ");
        primeiroOperando   = 0;
        operadorAtual      = null;
        iniciarNovaEntrada = true;
        limparDestaqueBotaoOp();
    }

    
    private void tratarLimparEntrada() {
        labelDisplay.setText("0");
        iniciarNovaEntrada = true;
    }

    
    private void tratarApagar() {
        if (iniciarNovaEntrada) return;
        String texto = labelDisplay.getText();
        if (texto.length() <= 1 || (texto.length() == 2 && texto.startsWith("-")))
            labelDisplay.setText("0");
        else
            labelDisplay.setText(texto.substring(0, texto.length() - 1));
    }

    
    private void tratarInverterSinal() {
        try {
            double valor = motor.converterEntrada(labelDisplay.getText());
            labelDisplay.setText(formatarResultado(-valor));
        } catch (CalculatorException ex) {
            exibirErro(ex);
        }
    }

    
    private void tratarExcecaoCalculadora(CalculatorException ex) {
        if (ex.getTipoErro() == CalculatorException.TipoErro.DIVISAO_POR_ZERO) {
            exibirDialogDivisaoPorZero();
        } else {
            exibirErro(ex);
        }
        tratarLimparTudo();
    }

    
    private void exibirErro(CalculatorException ex) {
        labelDisplay.setText("Erro");
        JOptionPane.showMessageDialog(this,
            ex.getMessage(),
            "Erro — " + ex.getTipoErro().name().replace("_", " "),
            JOptionPane.ERROR_MESSAGE);
        tratarLimparTudo();
    }

    
    private void exibirDialogDivisaoPorZero() {
        JDialog dialog = new JDialog(this, "Erro de Divisão", true);
        dialog.setUndecorated(true);
        dialog.setSize(340, 230);
        dialog.setLocationRelativeTo(this);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(0x2A, 0x2A, 0x2A));
        painel.setBorder(BorderFactory.createLineBorder(new Color(0x55, 0x55, 0x55), 1));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(new Color(0xC4, 0x2B, 0x1A));
        cabecalho.setBorder(new EmptyBorder(10, 16, 10, 10));
        JLabel tituloCabecalho = new JLabel("⚠   Erro: Divisão por Zero!");
        tituloCabecalho.setForeground(Color.WHITE);
        tituloCabecalho.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cabecalho.add(tituloCabecalho, BorderLayout.WEST);
        painel.add(cabecalho, BorderLayout.NORTH);

        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(new Color(0x2A, 0x2A, 0x2A));
        corpo.setBorder(new EmptyBorder(20, 20, 16, 20));

        JLabel msg1 = new JLabel("Não é possível dividir um número por zero.");
        msg1.setForeground(COR_TEXTO_PRINCIPAL);
        msg1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        msg1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg2 = new JLabel("Que tal aproveitar as ofertas do Atacadão? 🛒");
        msg2.setForeground(COR_TEXTO_SECUNDAR);
        msg2.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        msg2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel fileiraBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        fileiraBotoes.setBackground(new Color(0x2A, 0x2A, 0x2A));

        JButton botaoOk = criarBotaoDialog("  OK  ",
                new Color(0x3A, 0x3A, 0x3A), new Color(0x4A, 0x4A, 0x4A));
        botaoOk.addActionListener(e -> dialog.dispose());

        JButton botaoAtacadao = criarBotaoDialog("🔗  Atacadão LinkedIn",
                COR_BOTAO_DESTAQUE, COR_BOTAO_DEST_H);
        botaoAtacadao.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://www.linkedin.com/company/atacadao/"));
            } catch (Exception ignorado) {
                ignorado.printStackTrace();
            }
            dialog.dispose();
        });

        fileiraBotoes.add(botaoOk);
        fileiraBotoes.add(botaoAtacadao);

        corpo.add(msg1);
        corpo.add(Box.createVerticalStrut(8));
        corpo.add(msg2);
        corpo.add(Box.createVerticalStrut(20));
        corpo.add(fileiraBotoes);
        painel.add(corpo, BorderLayout.CENTER);

        dialog.setContentPane(painel);
        dialog.setVisible(true);
    }

    private JButton criarBotaoDialog(String texto, Color fundo, Color hover) {
        JButton b = new JButton(texto) {
            private boolean sobreBtn = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { sobreBtn = true;  repaint(); }
                public void mouseExited (MouseEvent e) { sobreBtn = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(sobreBtn ? hover : fundo);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 6, 6));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        b.setForeground(COR_TEXTO_PRINCIPAL);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(Math.max(60, texto.length() * 9 + 20), 32));
        return b;
    }

    
    private String formatarResultado(double valor) {
        if (valor == Math.floor(valor) && !Double.isInfinite(valor) && Math.abs(valor) < 1e15)
            return String.valueOf((long) valor);
        return formatador.format(valor);
    }

    
    private void destacarBotaoOperador(String operador) {
        limparDestaqueBotaoOp();
        for (Component comp : ((JPanel) getContentPane().getComponent(2)).getComponents()) {
            if (comp instanceof JButton botao) {
                if (operador.equals(botao.getClientProperty("botaoOp"))) {
                    botao.putClientProperty("corSobreposta", COR_OP_SELECIONADO);
                    botao.repaint();
                    ultimoBotaoOp = botao;
                }
            }
        }
    }

    
    private void limparDestaqueBotaoOp() {
        if (ultimoBotaoOp != null) {
            ultimoBotaoOp.putClientProperty("corSobreposta", null);
            ultimoBotaoOp.repaint();
            ultimoBotaoOp = null;
        }
    }

    
    private void adicionarSuporteArrasto(JComponent componente) {
        final Point[] pontoInicial = {null};
        componente.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { pontoInicial[0] = e.getPoint(); }
        });
        componente.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (pontoInicial[0] != null) {
                    Point posicaoAtual = getLocation();
                    setLocation(
                        posicaoAtual.x + e.getX() - pontoInicial[0].x,
                        posicaoAtual.y + e.getY() - pontoInicial[0].y
                    );
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignorado) {}
        SwingUtilities.invokeLater(() -> new CalculatorGUI().setVisible(true));
    }
}
