import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame {

    private static final Color COR_FUNDO        = new Color(32, 32, 32);
    private static final Color COR_DISPLAY       = new Color(32, 32, 32);
    private static final Color COR_BTN_NUM       = new Color(50, 50, 50);
    private static final Color COR_BTN_NUM_HOV   = new Color(68, 68, 68);
    private static final Color COR_BTN_NUM_PRE   = new Color(90, 90, 90);
    private static final Color COR_BTN_OP        = new Color(43, 43, 43);
    private static final Color COR_BTN_OP_HOV    = new Color(60, 60, 60);
    private static final Color COR_BTN_IGUAL     = new Color(118, 87, 160);
    private static final Color COR_BTN_IGUAL_HOV = new Color(138, 107, 180);
    private static final Color COR_TEXTO         = new Color(255, 255, 255);
    private static final Color COR_TEXTO_DIM     = new Color(180, 180, 180);
    private static final Color COR_ERRO          = new Color(255, 100, 100);

    private String numeroAtual    = "0";
    private String numeroAnterior = "";
    private String operacao       = "";
    private boolean novaEntrada   = false;
    private boolean calculou      = false;

    private JLabel labelExpressao;
    private JLabel labelDisplay;

    private final CalculadoraLogica logica = new CalculadoraLogica();

    public Calculadora() {
        configurarJanela();
        construirInterface();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(COR_FUNDO);
    }

    private void construirInterface() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COR_FUNDO);
        root.setBorder(new EmptyBorder(8, 8, 8, 8));
        root.add(criarCabecalho(), BorderLayout.NORTH);
        root.add(criarDisplay(),   BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(COR_FUNDO);
        bottom.add(criarTeclado(), BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        add(root);
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private JPanel criarCabecalho() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        p.setBackground(COR_FUNDO);
        JLabel titulo = new JLabel("Calculadora");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setForeground(COR_TEXTO);
        p.add(titulo);
        return p;
    }

    private JPanel criarDisplay() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COR_DISPLAY);
        p.setBorder(new EmptyBorder(0, 12, 4, 12));

        labelExpressao = new JLabel("", SwingConstants.RIGHT);
        labelExpressao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelExpressao.setForeground(COR_TEXTO_DIM);
        labelExpressao.setPreferredSize(new Dimension(0, 24));

        labelDisplay = new JLabel("0", SwingConstants.RIGHT);
        labelDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 52));
        labelDisplay.setForeground(COR_TEXTO);
        labelDisplay.setPreferredSize(new Dimension(0, 70));

        p.add(labelExpressao, BorderLayout.NORTH);
        p.add(labelDisplay,   BorderLayout.CENTER);
        return p;
    }

    private JPanel criarTeclado() {
        JPanel p = new JPanel(new GridLayout(5, 4, 4, 4));
        p.setBackground(COR_FUNDO);
        p.setBorder(new EmptyBorder(4, 0, 0, 0));

        
        p.add(btn("CE", COR_BTN_OP,  COR_BTN_OP_HOV,  false));
        p.add(btn("C",  COR_BTN_OP,  COR_BTN_OP_HOV,  false));
        p.add(btn("⌫",  COR_BTN_OP,  COR_BTN_OP_HOV,  false));
        p.add(btn("÷",  COR_BTN_OP,  COR_BTN_OP_HOV,  false));

        
        p.add(btn("7",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("8",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("9",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("×",  COR_BTN_OP,  COR_BTN_OP_HOV,  false));

        
        p.add(btn("4",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("5",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("6",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("−",  COR_BTN_OP,  COR_BTN_OP_HOV,  false));

        
        p.add(btn("1",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("2",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("3",  COR_BTN_NUM, COR_BTN_NUM_HOV, true));
        p.add(btn("+",  COR_BTN_OP,  COR_BTN_OP_HOV,  false));

        
        p.add(btn("+/-", COR_BTN_NUM,   COR_BTN_NUM_HOV,   false));
        p.add(btn("0",   COR_BTN_NUM,   COR_BTN_NUM_HOV,   true));
        p.add(btn(",",   COR_BTN_NUM,   COR_BTN_NUM_HOV,   false));
        p.add(btn("=",   COR_BTN_IGUAL, COR_BTN_IGUAL_HOV, false));

        return p;
    }

    private JButton btn(String txt, Color normal, Color hover, boolean grande) {
        JButton b = criarBotao(txt, normal, hover, COR_BTN_NUM_PRE);
        b.setFont(new Font("Segoe UI", Font.PLAIN, grande ? 20 : 16));
        b.addActionListener(e -> processarClique(txt));
        return b;
    }

    private JButton criarBotao(String texto, Color normal, Color hover, Color pressed) {
        JButton btn = new JButton(texto) {
            private boolean over = false;
            private boolean down = false;
            {
                setOpaque(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e)  { over = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e)  { over = false; repaint(); }
                    @Override public void mousePressed(MouseEvent e)  { down = true;  repaint(); }
                    @Override public void mouseReleased(MouseEvent e) { down = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(down ? pressed : (over ? hover : normal));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()  - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        btn.setForeground(COR_TEXTO);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(72, 52));
        return btn;
    }

    private void processarClique(String txt) {
        switch (txt) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                digitoPrecionado(txt); break;
            case ",":  pontoDecimal();       break;
            case "+": case "−": case "×": case "÷":
                operadorPrecionado(txt); break;
            case "=":   calcular();          break;
            case "C":   limparTudo();        break;
            case "CE":  limparEntrada();     break;
            case "⌫":   backspace();         break;
            case "+/-": inverterSinal();     break;
            default: break;
        }
    }

    private void digitoPrecionado(String d) {
        if (novaEntrada || calculou) {
            numeroAtual = d;
            novaEntrada = false;
            calculou    = false;
        } else {
            if (numeroAtual.equals("0")) numeroAtual = d;
            else if (numeroAtual.length() < 15) numeroAtual += d;
        }
        atualizarDisplay();
    }

    private void pontoDecimal() {
        if (novaEntrada || calculou) {
            numeroAtual = "0,";
            novaEntrada = false;
            calculou    = false;
        } else if (!numeroAtual.contains(",")) {
            numeroAtual += ",";
        }
        atualizarDisplay();
    }

    private void operadorPrecionado(String op) {
        if (!operacao.isEmpty() && !novaEntrada) calcular();
        numeroAnterior = numeroAtual;
        operacao       = op;
        novaEntrada    = true;
        calculou       = false;
        labelExpressao.setText(numeroAnterior + " " + op);
    }

    private void calcular() {
        if (operacao.isEmpty() || numeroAnterior.isEmpty()) return;
        try {
            double n1 = logica.parsearNumero(numeroAnterior.replace(",", "."), "N1");
            double n2 = logica.parsearNumero(numeroAtual.replace(",",    "."), "N2");
            double resultado = logica.calcular(n1, n2, operacao.replace("−", "-"));
            labelExpressao.setText(numeroAnterior + " " + operacao + " " + numeroAtual + " =");
            labelExpressao.setForeground(COR_TEXTO_DIM);
            numeroAtual    = logica.formatarNumero(resultado).replace(".", ",");
            numeroAnterior = "";
            operacao       = "";
            calculou       = true;
            atualizarDisplay();
        } catch (CalculadoraCalc ex) {
            mostrarErro(ex.getMensagemAmigavel());
        }
    }

    private void limparTudo() {
        numeroAtual = "0"; numeroAnterior = ""; operacao = "";
        novaEntrada = false; calculou = false;
        labelExpressao.setText("");
        labelExpressao.setForeground(COR_TEXTO_DIM);
        labelDisplay.setForeground(COR_TEXTO);
        atualizarDisplay();
    }

    private void limparEntrada() {
        numeroAtual = "0";
        novaEntrada = false;
        labelDisplay.setForeground(COR_TEXTO);
        atualizarDisplay();
    }

    private void backspace() {
        if (calculou) { limparTudo(); return; }
        if (numeroAtual.length() <= 1 ||
           (numeroAtual.startsWith("-") && numeroAtual.length() == 2))
            numeroAtual = "0";
        else
            numeroAtual = numeroAtual.substring(0, numeroAtual.length() - 1);
        atualizarDisplay();
    }

    private void inverterSinal() {
        try {
            double v = logica.parsearNumero(numeroAtual.replace(",", "."), "N");
            numeroAtual = logica.formatarNumero(-v).replace(".", ",");
            atualizarDisplay();
        } catch (CalculadoraCalc ignored) {}
    }

    private void atualizarDisplay() {
        int len  = numeroAtual.length();
        int size = len <= 9 ? 52 : len <= 13 ? 36 : 26;
        labelDisplay.setFont(new Font("Segoe UI", Font.PLAIN, size));
        labelDisplay.setText(numeroAtual);
    }

    private void mostrarErro(String msg) {
        labelDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        labelDisplay.setForeground(COR_ERRO);
        labelDisplay.setText(msg);
        numeroAtual = "0"; numeroAnterior = ""; operacao = ""; novaEntrada = true;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() { new Calculadora(); }
        });
    }
}