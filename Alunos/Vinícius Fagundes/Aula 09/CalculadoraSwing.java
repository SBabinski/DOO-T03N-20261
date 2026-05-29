import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

// ================= EXCEPTION PERSONALIZADA =================

class CalculadoraException extends Exception {

    public CalculadoraException(String mensagem) {
        super(mensagem);
    }
}

// ================= CALCULADORA =================

public class CalculadoraSwing extends JFrame
        implements ActionListener, KeyListener {

    private JTextField visor;

    private JTextArea historico;

    private String expressao = "";

    private JButton[] numeros = new JButton[10];

    private JButton soma;
    private JButton subtracao;
    private JButton multiplicacao;
    private JButton divisao;

    private JButton igual;
    private JButton limpar;

    // ================= CONSTRUTOR =================

    public CalculadoraSwing() {

        setTitle("Calculadora");
        setSize(400, 600);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int resposta = JOptionPane.showConfirmDialog(
                        null,
                        "Deseja realmente sair?",
                        "Confirmar saida",
                        JOptionPane.YES_NO_OPTION
                );

                if (resposta == JOptionPane.YES_OPTION) {

                    dispose();

                    System.exit(0);
                }
            }
        });

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        JMenuBar barra = new JMenuBar();

        JMenu menu = new JMenu("Menu");

        JMenuItem abrirHistorico = new JMenuItem("Historico");
            
        menu.add(abrirHistorico);

        barra.add(menu);

        setJMenuBar(barra);

        historico = new JTextArea();

        historico.setEditable(false);

        historico.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        abrirHistorico.addActionListener(e -> {

            JFrame janelaHistorico =
                    new JFrame("Historico");

            janelaHistorico.setSize(300, 400);

            janelaHistorico.setLocationRelativeTo(null);

            JScrollPane scroll =
                    new JScrollPane(historico);

            janelaHistorico.add(scroll);

            janelaHistorico.setVisible(true);
        });

        // ================= VISOR =================

        visor = new JTextField();
        
        visor.addKeyListener(this);

        visor.setFocusable(true);

        visor.setFont(new Font("Arial", Font.BOLD, 40));

        visor.setPreferredSize(
                new Dimension(400, 80)
        );

        visor.setFont(new Font("Arial", Font.BOLD, 28));

        visor.setHorizontalAlignment(JTextField.RIGHT);

        visor.setEditable(false);

        visor.setFocusable(true); 

        add(visor, BorderLayout.NORTH);

        // ================= PAINEL PRINCIPAL =================

        JPanel painel = new JPanel();

        painel.setLayout(new GridLayout(4, 4, 5, 5));

        // Numeros
        for (int i = 0; i <= 9; i++) {

            numeros[i] = new JButton(String.valueOf(i));

            numeros[i].setFont(
                    new Font("Arial", Font.BOLD, 22)
            );

            numeros[i].setFocusable(false);
        }

        soma = criarBotao("+");
        subtracao = criarBotao("-");
        multiplicacao = criarBotao("*");
        divisao = criarBotao("/");

        igual = criarBotao("=");

        limpar = criarBotao("C");

        // Linha 1
        painel.add(numeros[7]);
        painel.add(numeros[8]);
        painel.add(numeros[9]);
        painel.add(divisao);

        // Linha 2
        painel.add(numeros[4]);
        painel.add(numeros[5]);
        painel.add(numeros[6]);
        painel.add(multiplicacao);

        // Linha 3
        painel.add(numeros[1]);
        painel.add(numeros[2]);
        painel.add(numeros[3]);
        painel.add(subtracao);

        // Linha 4
        painel.add(limpar);
        painel.add(numeros[0]);
        painel.add(igual);
        painel.add(soma);

        add(painel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> {
            visor.requestFocusInWindow();
        });

        setVisible(true);
    }

    // ================= CRIAR BOTAO =================

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        botao.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        botao.addActionListener(this);

        botao.setFocusable(false);

        return botao;
    }

    // ================= EVENTOS =================

    @Override
    public void actionPerformed(ActionEvent e) {

        Object origem = e.getSource();

        // Numeros
        for (int i = 0; i <= 9; i++) {

            if (origem == numeros[i]) {

                expressao += i;

                visor.setText(expressao);

                return;
            }
        }

        // Operacoes
        if (origem == soma) adicionarOperador("+");

        if (origem == subtracao) adicionarOperador("-");

        if (origem == multiplicacao) adicionarOperador("*");

        if (origem == divisao) adicionarOperador("/");

        // Igual
        if (origem == igual) calcular();

        // Limpar
        if (origem == limpar) {

            expressao = "";

            visor.setText("");
        }
    }

    // ================= ADICIONAR OPERADOR =================

    private void adicionarOperador(String op) {

    if (expressao.isEmpty()) {
        return;
    }

    char ultimo =
            expressao.charAt(expressao.length() - 1);

    // Evita operadores duplicados
    if (
            ultimo == '+' ||
            ultimo == '-' ||
            ultimo == '*' ||
            ultimo == '/'
    ) {

        expressao =
                expressao.substring(
                        0,
                        expressao.length() - 1
                );

        expressao += op;

    } else {

        expressao += op;
    }

    visor.setText(expressao);
}

    // ================= CALCULAR =================

    private void calcular() {

        try {

            double resultado = resolverExpressao(expressao);

            historico.append(
                    expressao + " = " + resultado + "\n"
            );

            visor.setText(String.valueOf(resultado));

            expressao = String.valueOf(resultado);

        } catch (CalculadoraException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expressao invalida",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= RESOLVER EXPRESSAO =================

    private double resolverExpressao(String exp)
            throws CalculadoraException {

        Stack<Double> numeros = new Stack<>();

        Stack<Character> operadores = new Stack<>();

        String numeroAtual = "";

        for (int i = 0; i < exp.length(); i++) {

            char c = exp.charAt(i);

            // Numero
            if (Character.isDigit(c) || c == '.') {

                numeroAtual += c;
            }

            // Operador
            else {

                numeros.push(
                        Double.parseDouble(numeroAtual)
                );

                numeroAtual = "";

                while (!operadores.isEmpty() &&
                        prioridade(operadores.peek())
                        >= prioridade(c)) {

                    executarOperacao(
                            numeros,
                            operadores.pop()
                    );
                }

                operadores.push(c);
            }
        }

        // Ultimo numero
        numeros.push(
                Double.parseDouble(numeroAtual)
        );

        // Finalizar
        while (!operadores.isEmpty()) {

            executarOperacao(
                    numeros,
                    operadores.pop()
            );
        }

        return numeros.pop();
    }

    // ================= PRIORIDADE =================

    private int prioridade(char op) {

        if (op == '+' || op == '-') {
            return 1;
        }

        if (op == '*' || op == '/') {
            return 2;
        }

        return 0;
    }

    // ================= EXECUTAR OPERACAO =================

    private void executarOperacao(
            Stack<Double> numeros,
            char operador
    ) throws CalculadoraException {

        double b = numeros.pop();

        double a = numeros.pop();

        double resultado = 0;

        switch (operador) {

            case '+':

                resultado = a + b;

                break;

            case '-':

                resultado = a - b;

                break;

            case '*':

                resultado = a * b;

                break;

            case '/':

                if (b == 0) {

                    throw new CalculadoraException(
                            "Divisao por zero"
                    );
                }

                resultado = a / b;

                break;
        }

        numeros.push(resultado);
    }

        @Override
    public void keyTyped(KeyEvent e) {

        char tecla = e.getKeyChar();

        // Numeros
        if (Character.isDigit(tecla)) {

            expressao += tecla;

            visor.setText(expressao);

            return;
        }

        // Decimal
        if (tecla == '.') {

            expressao += ".";

            visor.setText(expressao);

            return;
        }

        // Operadores
        if (
                tecla == '+' ||
                tecla == '-' ||
                tecla == '*' ||
                tecla == '/'
        ) {

            adicionarOperador(
                    String.valueOf(tecla)
            );

            return;
        }

        // Enter
        if (tecla == '=') {

            calcular();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int tecla = e.getKeyCode();

        // ENTER
        if (tecla == KeyEvent.VK_ENTER) {

            calcular();
        }

        // BACKSPACE
        if (tecla == KeyEvent.VK_BACK_SPACE) {

            if (!expressao.isEmpty()) {

                expressao = expressao.substring(
                        0,
                        expressao.length() - 1
                );

                visor.setText(expressao);
            }
        }

        // DELETE
        if (tecla == KeyEvent.VK_DELETE) {

            expressao = "";

            visor.setText("");
        }

        // ESC
        if (tecla == KeyEvent.VK_ESCAPE) {

            expressao = "";

            visor.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

}

    // ================= MAIN =================

    public static void main(String[] args) {

        new CalculadoraSwing();
    }
}