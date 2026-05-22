import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame {

    // LOGICA DA CALCULADORA
    private Calculator calculator = new Calculator();

    // Guarda o primeiro numero digitado e o operador escolhido
    private double primeiroNumero = 0;
    private String operadorSelecionado = "";
    private boolean aguardandoSegundoNumero = false;
   
    // COMPONENTES DA TELA
    private JTextField display;       // campo que mostra os numeros
    private JLabel labelOperacao;     // mostra a operacao atual
    private JLabel labelErro;         // label que mostra mensagens de erro

    // CONSTRUTOR
    public CalculatorGUI() {
        super("Calculadora");     // titulo da janela
        configurarJanela();
        montarTela();
        configurarAtalhosTeclado();
        setVisible(true);       // exibe a janela
    }

    // CONFIGURACAO DA JANELA
    private void configurarJanela() {
        setSize(320, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // fecha ao clicar no X
        setLocationRelativeTo(null);                     // centraliza na tela
        setResizable(false);                             // nao permite redimensionar
    }

    // MONTAGEM DA TELA
    private void montarTela() {
        // Divide a janela em: NORTH (display), CENTER (botoes), SOUTH (erro)
        JPanel painelPrincipal = new JPanel(new BorderLayout(5, 5));
        painelPrincipal.setBackground(new Color(45, 45, 45));

        // Adiciona cada secao no lugar correto
        painelPrincipal.add(criarDisplay(),  BorderLayout.NORTH);
        painelPrincipal.add(criarBotoes(),   BorderLayout.CENTER);
        painelPrincipal.add(criarLabelErro(), BorderLayout.SOUTH);

        // Adiciona o painel principal na janela
        add(painelPrincipal);
    }

    // TECLADO
    private void configurarAtalhosTeclado() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent e) {
                    if (e.getID() != KeyEvent.KEY_PRESSED) {
                        return false;
                    }

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_BACK_SPACE:
                            apagarUltimoDigito();
                            return true;
                        case KeyEvent.VK_ENTER:
                            clicarIgual();
                            return true;
                        case KeyEvent.VK_ESCAPE:
                            clicarUtilitario("AC");
                            return true;
                        case KeyEvent.VK_ADD:
                            clicarOperador("+");
                            return true;
                        case KeyEvent.VK_MINUS:
                        case KeyEvent.VK_SUBTRACT:
                            clicarOperador("-");
                            return true;
                        case KeyEvent.VK_SLASH:
                        case KeyEvent.VK_DIVIDE:
                            clicarOperador("/");
                            return true;
                        case KeyEvent.VK_MULTIPLY:
                            clicarOperador("x");
                            return true;
                        case KeyEvent.VK_EQUALS:
                            if (e.isShiftDown()) {
                                clicarOperador("+");
                            } else {
                                clicarIgual();
                            }
                            return true;
                        case KeyEvent.VK_PERIOD:
                        case KeyEvent.VK_DECIMAL:
                            clicarNumero(".");
                            return true;
                    }

                    char ch = e.getKeyChar();
                    if (Character.isDigit(ch)) {
                        clicarNumero(String.valueOf(ch));
                        return true;
                    }

                    return false;
                }
            });
    }

    private void apagarUltimoDigito() {
        limparErro();
        String texto = display.getText();
        if (texto.length() <= 1 || texto.equals("Erro")) {
            display.setText("0");
            return;
        }
        display.setText(texto.substring(0, texto.length() - 1));
    }

    // SECAO: DISPLAY
    private JPanel criarDisplay() {
        display = new JTextField("0");
        display.setFont(new Font("Courier New", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);  // texto alinhado a direita
        display.setEditable(false);                        // usuario nao digita direto
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        labelOperacao = new JLabel(" ");
        labelOperacao.setFont(new Font("Courier New", Font.PLAIN, 14));
        labelOperacao.setForeground(new Color(200, 200, 200));
        labelOperacao.setHorizontalAlignment(JLabel.RIGHT);
        labelOperacao.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));

        JPanel painelDisplay = new JPanel(new BorderLayout());
        painelDisplay.setBackground(new Color(45, 45, 45));
        painelDisplay.add(labelOperacao, BorderLayout.NORTH);
        painelDisplay.add(display, BorderLayout.CENTER);
        return painelDisplay;
    }

    // SECAO: LABEL DE ERRO
    private JLabel criarLabelErro() {
        labelErro = new JLabel(" ");   // espaco para manter altura
        labelErro.setFont(new Font("Courier New", Font.ITALIC, 12));
        labelErro.setForeground(new Color(255, 80, 80));   // vermelho
        labelErro.setHorizontalAlignment(JLabel.CENTER);
        return labelErro;
    }

    // SECAO: BOTOES
    private JPanel criarBotoes() {
        JPanel painel = new JPanel(new GridLayout(5, 4, 5, 5));
        painel.setBackground(new Color(0, 0, 0));

        // --- Linha 1: botoes utilitarios e divisao ---
        painel.add(criarBotaoUtilitario("AC"));
        painel.add(criarBotaoUtilitario("+/-"));
        painel.add(criarBotaoUtilitario("%"));
        painel.add(criarBotaoOperador("/"));

        // --- Linha 2: 7, 8, 9, multiplicacao ---
        painel.add(criarBotaoNumero("7"));
        painel.add(criarBotaoNumero("8"));
        painel.add(criarBotaoNumero("9"));
        painel.add(criarBotaoOperador("x"));

        // --- Linha 3: 4, 5, 6, subtracao ---
        painel.add(criarBotaoNumero("4"));
        painel.add(criarBotaoNumero("5"));
        painel.add(criarBotaoNumero("6"));
        painel.add(criarBotaoOperador("-"));

        // --- Linha 4: 1, 2, 3, adicao ---
        painel.add(criarBotaoNumero("1"));
        painel.add(criarBotaoNumero("2"));
        painel.add(criarBotaoNumero("3"));
        painel.add(criarBotaoOperador("+"));

        // --- Linha 5: 0, ponto, igual ---
        // O botao 0 ocupa 2 colunas: usamos um painel com GridLayout 1x2
        JPanel painelZero = new JPanel(new GridLayout(1, 2, 5, 0));
        painelZero.setBackground(new Color(45, 45, 45));
        painelZero.add(criarBotaoNumero("."));
        painelZero.add(criarBotaoNumero("0"));
        painel.add(painelZero);

        // celula vazia para ocupar a segunda coluna (ja usada pelo painelZero)
        painel.add(new JLabel(""));

        painel.add(criarBotaoIgual());

        return painel;
    }


    // FABRICAS DE BOTOES
    // Cada metodo cria um tipo diferente de botao


    private JButton criarBotaoNumero(final String rotulo) {
        JButton btn = new JButton(rotulo);
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.setBackground(new Color(220, 60, 120)); // rosa
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ActionListener: o que acontece quando o botao e clicado
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicarNumero(rotulo);
            }
        });

        return btn;
    }

    private JButton criarBotaoOperador(final String operador) {
        JButton btn = new JButton(operador);
        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setBackground(new Color(40, 120, 200));   // azul
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicarOperador(operador);
            }
        });

        return btn;
    }

    private JButton criarBotaoIgual() {
        JButton btn = new JButton("=");
        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setBackground(new Color(0, 170, 110));    // verde
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicarIgual();
            }
        });

        return btn;
    }

    private JButton criarBotaoUtilitario(final String rotulo) {
        JButton btn = new JButton(rotulo);
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setBackground(new Color(100, 100, 110));  // cinza medio
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicarUtilitario(rotulo);
            }
        });

        return btn;
    }

    // -------------------------------------------------------
    // ACOES DOS BOTOES
    // -------------------------------------------------------

    /**
     * Chamado quando um numero ou ponto e clicado.
     */
    private void clicarNumero(String digito) {
        limparErro();

        // Se estamos mostrando erro, reinicia o display
        if (display.getText().startsWith("Erro")) {
            display.setText("0");
        }

        // Se e ponto decimal: verifica se ja existe um no display
        if (digito.equals(".")) {
            if (display.getText().contains(".")) return;
            display.setText(display.getText() + ".");
            return;
        }

        // Se estamos aguardando o segundo numero, limpa o display
        if (aguardandoSegundoNumero) {
            display.setText(digito);
            aguardandoSegundoNumero = false;
        } else {
            // Se o display so tem "0", substitui pelo digito
            if (display.getText().equals("0")) {
                display.setText(digito);
            } else {
                // Concatena o digito ao que ja esta no display
                display.setText(display.getText() + digito);
            }
        }
    }

    /**
     * Chamado quando um operador (+, -, x, /) e clicado.
     */
    private void clicarOperador(String operador) {
        limparErro();

        try {
            // Salva o numero atual como primeiro operando
            primeiroNumero = calculator.parseNumber(display.getText());
            operadorSelecionado = operador;
            aguardandoSegundoNumero = true;
            labelOperacao.setText(
                calculator.formatarResultado(primeiroNumero) + " " + operador
            );

        } catch (CalculatorException ex) {
            mostrarErro(ex.getFriendlyMessage());
        }
    }

    /**
     * Chamado quando o botao "=" e clicado.
     */
    private void clicarIgual() {
        limparErro();

        // Verifica se um operador foi selecionado
        if (operadorSelecionado.isEmpty()) {
            mostrarErro(new CalculatorException(
                CalculatorException.ErrorType.NO_OPERATOR_SELECTED, "")
                .getFriendlyMessage());
            return;
        }

        try {
            // Pega o segundo numero do display
            double segundoNumero = calculator.parseNumber(display.getText());

            // Faz o calculo — pode lancar CalculatorException (ex: divisao por zero)
            double resultado = calculator.calculate(
                primeiroNumero, segundoNumero, operadorSelecionado
            );

            // Mostra o resultado no display
            display.setText(calculator.formatarResultado(resultado));
            labelOperacao.setText(" ");

            // Reseta o estado para um novo calculo
            operadorSelecionado = "";
            aguardandoSegundoNumero = false;

        } catch (CalculatorException ex) {
            // Captura o erro e mostra mensagem amigavel
            mostrarErro(ex.getFriendlyMessage());
        }
    }

    /**
     * Chamado pelos botoes AC, +/- e %.
     */
    private void clicarUtilitario(String rotulo) {
        limparErro();

        switch (rotulo) {
            case "AC":
                // Limpa tudo e volta ao estado inicial
                display.setText("0");
                labelOperacao.setText(" ");
                primeiroNumero = 0;
                operadorSelecionado = "";
                aguardandoSegundoNumero = false;
                break;

            case "+/-":
                // Inverte o sinal do numero no display
                try {
                    double valor = calculator.parseNumber(display.getText());
                    display.setText(calculator.formatarResultado(-valor));
                } catch (CalculatorException ex) {
                    mostrarErro(ex.getFriendlyMessage());
                }
                break;

            case "%":
                // Divide o numero por 100
                try {
                    double valor = calculator.parseNumber(display.getText());
                    display.setText(calculator.formatarResultado(valor / 100));
                } catch (CalculatorException ex) {
                    mostrarErro(ex.getFriendlyMessage());
                }
                break;
        }
    }

    private void mostrarErro(String mensagem) {
        labelErro.setText(mensagem);
        display.setText(truncarMensagemDisplay(mensagem));
        labelOperacao.setText(" ");
    }

    private String truncarMensagemDisplay(String mensagem) {
        int max = 16;
        if (mensagem == null || mensagem.length() <= max) {
            return mensagem;
        }
        return mensagem.substring(0, max - 1) + "…";
    }

    private void limparErro() {
        labelErro.setText(" ");
    }

    // MAIN: ponto de entrada do programa
  
    public static void main(String[] args) {
        // Aplica o visual padrao do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Se nao funcionar, continua com o visual padrao do Java
        }

        // Cria a janela na Event Dispatch Thread (thread do Swing)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CalculatorGUI();
            }
        });
    }
}
