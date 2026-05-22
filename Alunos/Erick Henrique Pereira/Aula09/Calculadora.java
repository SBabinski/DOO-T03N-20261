import java.awt.*;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Calculadora {

    private static final Color COR_FUNDO   = new Color(50, 20, 30);
    private static final Color COR_CINZA_E = new Color(212, 212, 210);
    private static final Color COR_CINZA_M = new Color(80, 80, 80);
    private static final Color COR_LARANJA = new Color(255, 149, 0);

    private static final String[] botoes = {
        "AC", "+/-", "_", "/",
         "7",  "8",  "9", "x",
         "4",  "5",  "6", "-",
         "1",  "2",  "3", "+",
         "0",        ".", "="
    };
    private static final String[] botaoCima = {"AC", "+/-", "_"};
    private static final String[] botaoLado = {"/", "x", "-", "+", "="};

    private final JFrame     frame        = new JFrame("Calculadora");
    private final JLabel     display      = new JLabel("");
    private final JLabel     inputDisplay = new JLabel("0");
    private final JTextField campoExpressao = new JTextField();

    private double  a           = 0;
    private String  operador    = null;
    private boolean novaEntrada = false;
    private boolean atualizandoCampo = false;

    Calculadora() {
        configurarFrame();
        configurarDisplay();
        configurarBotoes();
        configurarCampoExpressao();
        frame.setVisible(true);
    }

    private void configurarFrame() {
        frame.setSize(360, 680);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void configurarDisplay() {
        JPanel painel = new JPanel(new BorderLayout()) {
            @Override public Dimension getPreferredSize() { return new Dimension(360, 160); }
            @Override public Dimension getMinimumSize()   { return getPreferredSize(); }
            @Override public Dimension getMaximumSize()   { return getPreferredSize(); }
        };
        painel.setBackground(COR_CINZA_E);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        display.setForeground(new Color(150, 150, 150));
        display.setFont(new Font("Arial", Font.PLAIN, 32));
        display.setHorizontalAlignment(JLabel.RIGHT);
        painel.add(display, BorderLayout.NORTH);

        inputDisplay.setForeground(Color.WHITE);
        inputDisplay.setFont(new Font("Arial", Font.PLAIN, 72));
        inputDisplay.setHorizontalAlignment(JLabel.RIGHT);
        painel.add(inputDisplay, BorderLayout.SOUTH);

        frame.add(painel, BorderLayout.NORTH);
    }

    private void configurarBotoes() {
        JPanel painel = new JPanel(new GridLayout(5, 4, 2, 2));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        frame.add(painel, BorderLayout.CENTER);

        for (String butao : botoes) {
            JButton botao = criarBotao(butao);
            botao.addActionListener(e -> processarClique(butao));
            painel.add(botao);
        }
    }

    private JButton criarBotao(String valor) {
        JButton botao = new JButton(valor);
        botao.setFont(new Font("Arial", Font.PLAIN, 28));
        botao.setFocusable(false);
        botao.setBorder(new LineBorder(COR_FUNDO, 1));

        if (Arrays.asList(botaoCima).contains(valor)) {
            botao.setBackground(COR_CINZA_E);
            botao.setForeground(COR_FUNDO);
        } else if (Arrays.asList(botaoLado).contains(valor)) {
            botao.setBackground(COR_LARANJA);
            botao.setForeground(Color.WHITE);
        } else {
            botao.setBackground(COR_CINZA_M);
            botao.setForeground(Color.WHITE);
        }
        return botao;
    }

    private void processarClique(String valor) {
        switch (valor) {
            case "AC"                -> limparTudo();
            case "_"                 -> apagarUltimo();
            case "+/-"               -> negarEntrada();
            case "="                 -> calcularResultado();
            case "+", "-", "x", "/" -> definirOperador(valor);
            case "."                 -> adicionarPonto();
            default                  -> adicionarDigito(valor);
        }
    }

    private void apagarUltimo() {
        if (novaEntrada) return;
        String atual = inputDisplay.getText();
        String novo  = (atual.length() <= 1) ? "0" : atual.substring(0, atual.length() - 1);
        inputDisplay.setText(novo);
        atualizarCampo();
    }

    private void adicionarPonto() {
        if (novaEntrada) {
            inputDisplay.setText("0.");
            novaEntrada = false;
            atualizarCampo();
            return;
        }
        String atual = inputDisplay.getText();
        if (!atual.contains(".")) {
            inputDisplay.setText(atual + ".");
            atualizarCampo();
        }
    }

    private void atualizarCampo() {
        if (atualizandoCampo) return;
        atualizandoCampo = true;

        String expr = operador != null
            ? formatar(a) + " " + operador + " " + inputDisplay.getText()
            : inputDisplay.getText();

        campoExpressao.setText(expr);
        campoExpressao.setCaretPosition(expr.length());
        atualizandoCampo = false;
    }

    private void negarEntrada() {
        try {
            double num = Double.parseDouble(inputDisplay.getText());
            inputDisplay.setText(formatar(num * -1));
            atualizarCampo();
        } catch (Exception ex) {
            mostrarErro("Entrada não é válida");
        }
    }

    private void adicionarDigito(String digito) {
        if (novaEntrada) {
            inputDisplay.setText(digito);
            novaEntrada = false;
        } else {
            String atual = inputDisplay.getText();
            inputDisplay.setText(atual.equals("0") ? digito : atual + digito);
        }
        atualizarCampo();
    }

    private void definirOperador(String operadorAtual) {
        if (operador != null && !novaEntrada) {
            calcularResultado();
        }
        try {
            a = Double.parseDouble(inputDisplay.getText());
        } catch (NumberFormatException ex) {
            mostrarErro("Entrada inválida");
            return;
        }
        operador    = operadorAtual;
        novaEntrada = true;
        display.setText(formatar(a) + " " + operador);
        atualizarCampo();
    }

    private void calcularResultado() {
        try {
            if (operador == null)
                throw new CalcException("Operador ainda não foi escolhido");

            double b;
            try {
                b = Double.parseDouble(inputDisplay.getText());
            } catch (NumberFormatException ex) {
                mostrarErro("Entrada inválida");
                return;
            }

            double resultado = switch (operador) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "x" -> a * b;
                case "/" -> {
                    if (b == 0) throw new CalcException("Divisão é impossível por zero");
                    yield a / b;
                }
                default -> throw new CalcException("Operador não usual");
            };

            display.setText(formatar(a) + " " + operador + " " + formatar(b) + " =");
            inputDisplay.setText(formatar(resultado));
            a           = resultado;
            operador    = null;
            novaEntrada = true;
            atualizarCampo();

        } catch (CalcException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void limparTudo() {
        a           = 0;
        operador    = null;
        novaEntrada = false;
        inputDisplay.setText("0");
        display.setText("");
        atualizandoCampo = true;
        campoExpressao.setText("");
        atualizandoCampo = false;
    }

    private String formatar(double num) {
        return (num % 1 == 0) ? String.valueOf((long) num) : String.valueOf(num);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(frame, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        limparTudo();
    }

    static class CalcException extends Exception {
        CalcException(String mensagem) { super(mensagem); }
    }

    // ─── MÉTODO CORRIGIDO ─────────────────────────────────────────────────────
    // Regex que detecta o operador entre dois números, ignorando "-" de negativos.
    // Extrai corretamente: a, operador e b — sincronizando as 3 variáveis de estado.
    public void sincronizarCampoParaDisplay() {
        if (atualizandoCampo) return;
        String texto = campoExpressao.getText().trim();

        if (texto.isEmpty()) {
            inputDisplay.setText("0");
            display.setText("");
            a           = 0;
            operador    = null;
            novaEntrada = false;
            return;
        }

        // Encontra o operador: +, -, x, /  que esteja entre dois números
        // O "-" só é operador se vier após um dígito (não é sinal de negativo)
        java.util.regex.Matcher m = java.util.regex.Pattern
            .compile("^(-?[\\d.]+)\\s*([+\\-x/])\\s*(-?[\\d.]*)$")
            .matcher(texto);

        if (m.matches()) {
            String parteA  = m.group(1);  // primeiro número
            String op      = m.group(2);  // operador
            String parteB  = m.group(3);  // segundo número (pode estar incompleto)

            try {
                a = Double.parseDouble(parteA);
            } catch (NumberFormatException ex) {
                return;
            }

            operador = op;
            display.setText(parteA + " " + op + (parteB.isEmpty() ? "" : " " + parteB));

            if (!parteB.isEmpty() && !parteB.equals("-") && !parteB.equals(".")) {
                inputDisplay.setText(parteB);
                novaEntrada = false;
            } else {
                // operador digitado mas segundo número ainda não — aguarda
                novaEntrada = true;
            }

        } else {
            // Apenas um número, sem operador ainda
            try {
                Double.parseDouble(texto);
                inputDisplay.setText(texto);
                display.setText("");
                operador    = null;
                novaEntrada = false;
            } catch (NumberFormatException ex) {
                // texto inválido — ignora
            }
        }
    }

    private void configurarCampoExpressao() {
        JPanel painel = new JPanel(new BorderLayout()) {
            @Override public Dimension getPreferredSize() { return new Dimension(360, 90); }
            @Override public Dimension getMinimumSize()   { return getPreferredSize(); }
            @Override public Dimension getMaximumSize()   { return getPreferredSize(); }
        };
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(4, 6, 8, 6));

        JLabel label = new JLabel("Expressão");
        label.setForeground(new Color(150, 150, 150));
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        painel.add(label, BorderLayout.NORTH);

        campoExpressao.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { sincronizarCampoParaDisplay(); }
            public void removeUpdate(DocumentEvent e)  { sincronizarCampoParaDisplay(); }
            public void changedUpdate(DocumentEvent e) { sincronizarCampoParaDisplay(); }
        });

        campoExpressao.setBackground(new Color(30, 10, 20));
        campoExpressao.setForeground(Color.WHITE);
        campoExpressao.setCaretColor(Color.WHITE);
        campoExpressao.setFont(new Font("Arial", Font.PLAIN, 18));
        campoExpressao.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painel.add(campoExpressao, BorderLayout.CENTER);

        frame.add(painel, BorderLayout.SOUTH);

        campoExpressao.addActionListener(e -> calcularResultado());
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}