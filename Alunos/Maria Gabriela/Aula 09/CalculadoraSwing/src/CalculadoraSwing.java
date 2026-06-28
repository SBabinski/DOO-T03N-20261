import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraSwing extends JFrame implements ActionListener {

    private static final Color COR_FUNDO      = new Color(30, 0, 50);
    private static final Color COR_DISPLAY_BG = new Color(20, 0, 35);
    private static final Color COR_DISPLAY_FG = new Color(220, 180, 255);
    private static final Color COR_BTN_NUMERO = new Color(80, 0, 120);
    private static final Color COR_BTN_OP     = new Color(130, 0, 180);
    private static final Color COR_BTN_IGUAL  = new Color(170, 0, 230);
    private static final Color COR_BTN_LIMPAR = new Color(100, 0, 60);
    private static final Color COR_TEXTO_BTN  = new Color(240, 210, 255);
    private static final Color COR_HOVER      = new Color(190, 100, 255);

    private JTextField display;
    private double primeiroNumero = 0;
    private String operacao = "";
    private boolean novoNumero = true;

    
    public CalculadoraSwing() {
        setTitle("Calculadora");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(8, 8));
        getContentPane().setBackground(COR_FUNDO);

        construirDisplay();
        construirBotoes();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void construirDisplay() {
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(COR_DISPLAY_BG);
        display.setForeground(COR_DISPLAY_FG);
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 0, 180), 2),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        display.setPreferredSize(new Dimension(360, 70));
        add(display, BorderLayout.NORTH);
    }

    private void construirBotoes() {
        JPanel painel = new JPanel(new GridLayout(5, 4, 6, 6));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10));

        painel.add(criarBotao("C",  COR_BTN_LIMPAR));
        painel.add(criarBotao("±",  COR_BTN_OP));
        painel.add(criarBotao("%",  COR_BTN_OP));
        painel.add(criarBotao("÷",  COR_BTN_OP));

        painel.add(criarBotao("7",  COR_BTN_NUMERO));
        painel.add(criarBotao("8",  COR_BTN_NUMERO));
        painel.add(criarBotao("9",  COR_BTN_NUMERO));
        painel.add(criarBotao("×",  COR_BTN_OP));

        painel.add(criarBotao("4",  COR_BTN_NUMERO));
        painel.add(criarBotao("5",  COR_BTN_NUMERO));
        painel.add(criarBotao("6",  COR_BTN_NUMERO));
        painel.add(criarBotao("-",  COR_BTN_OP));

        painel.add(criarBotao("1",  COR_BTN_NUMERO));
        painel.add(criarBotao("2",  COR_BTN_NUMERO));
        painel.add(criarBotao("3",  COR_BTN_NUMERO));
        painel.add(criarBotao("+",  COR_BTN_OP));

        painel.add(criarBotao("0",  COR_BTN_NUMERO));
        painel.add(criarBotao(".",  COR_BTN_NUMERO));
        painel.add(new JLabel());                        // célula vazia
        painel.add(criarBotao("=",  COR_BTN_IGUAL));

        add(painel, BorderLayout.CENTER);
    }

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setBackground(corFundo);
        btn.setForeground(COR_TEXTO_BTN);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(80, 60));
        btn.addActionListener(this);

        // Efeito hover
        btn.addMouseListener(new MouseAdapter() {
            final Color original = corFundo;
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(COR_HOVER);  }
            @Override public void mouseExited (MouseEvent e) { btn.setBackground(original);   }
        });

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        try {
            switch (cmd) {
                case "C"  -> limpar();
                case "±"  -> inverterSinal();
                case "%"  -> porcentagem();
                case "="  -> calcular();
                case "."  -> adicionarPonto();
                case "+", "-", "×", "÷" -> definirOperacao(cmd);
                default   -> digitarNumero(cmd);
            }
        } catch (CalculadoraException ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void digitarNumero(String digito) throws CalculadoraException {
        if (!digito.matches("[0-9]")) {
            throw new CalculadoraException(
                "Entrada inválida: '" + digito + "' não é um dígito.");
        }
        if (novoNumero) {
            display.setText(digito);
            novoNumero = false;
        } else {
            String atual = display.getText();
            if (atual.length() >= 15) return;
            display.setText(atual.equals("0") ? digito : atual + digito);
        }
    }

    private void adicionarPonto() {
        if (novoNumero) {
            display.setText("0.");
            novoNumero = false;
            return;
        }
        if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void definirOperacao(String op) throws CalculadoraException {
        primeiroNumero = parseDisplay();
        operacao = op;
        novoNumero = true;
    }

    private void calcular() throws CalculadoraException {
        if (operacao.isEmpty()) return;

        double segundo   = parseDisplay();
        double resultado = realizarOperacao(primeiroNumero, segundo, operacao);

        exibirValor(resultado);
        operacao   = "";
        novoNumero = true;
    }

    private double realizarOperacao(double a, double b, String op)
            throws CalculadoraException {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "×" -> a * b;
            case "÷" -> {
                if (b == 0)
                    throw new CalculadoraException(
                        "Erro: divisão por zero não é permitida.");
                yield a / b;
            }
            default -> throw new CalculadoraException(
                "Operação desconhecida: " + op);
        };
    }

    private void inverterSinal() throws CalculadoraException {
        exibirValor(-parseDisplay());
    }

    private void porcentagem() throws CalculadoraException {
        exibirValor(parseDisplay() / 100.0);
    }

    private double parseDisplay() throws CalculadoraException {
        try {
            return Double.parseDouble(display.getText());
        } catch (NumberFormatException ex) {
            throw new CalculadoraException(
                "Entrada inválida: '" + display.getText() + "' não é um número válido.");
        }
    }

    private void exibirValor(double valor) {
        if (valor == (long) valor) {
            display.setText(String.valueOf((long) valor));
        } else {
            display.setText(String.valueOf(valor));
        }
        novoNumero = true;
    }

    private void limpar() {
        display.setText("0");
        primeiroNumero = 0;
        operacao       = "";
        novoNumero     = true;
    }

    private void mostrarErro(String mensagem) {
        display.setText("Erro");
        novoNumero = true;
        operacao   = "";
        JOptionPane.showMessageDialog(
            this,
            mensagem,
            "Erro na Calculadora",
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        // Roda na Event Dispatch Thread (boa prática Swing)
        SwingUtilities.invokeLater(CalculadoraSwing::new);
    }
}