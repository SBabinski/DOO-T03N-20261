import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CalculadoraSwing {

    // CORES
    private static final Color FUNDO = new Color(25, 25, 35);
    private static final Color BOTAO_NUMERO = new Color(70, 90, 120);
    private static final Color BOTAO_OPERADOR = new Color(0, 120, 215);
    private static final Color BOTAO_FUNCAO = new Color(180, 180, 180);

    // BOTÕES
    private static final String[] TECLAS = {
            "AC", "+/-", "DEL", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "="
    };

    private final Set<String> botoesOperadores = new HashSet<>();
    private final Set<String> botoesFuncionais = new HashSet<>();

    // COMPONENTES
    private final JFrame janela = new JFrame("Calculadora Java");
    private final JLabel historicoLabel = new JLabel("");
    private final JLabel visorResultado = new JLabel("0");
    private final JTextField campoConta = new JTextField();

    // CONTROLE
    private double valorInicial = 0;
    private String operacaoAtual = "";
    private boolean limparProximoNumero = false;
    private boolean atualizandoCampo = false;

    public CalculadoraSwing() {

        botoesOperadores.add("/");
        botoesOperadores.add("*");
        botoesOperadores.add("-");
        botoesOperadores.add("+");
        botoesOperadores.add("=");

        botoesFuncionais.add("AC");
        botoesFuncionais.add("+/-");
        botoesFuncionais.add("DEL");

        criarJanela();
        criarDisplay();
        criarPainelBotoes();
        criarCampoExpressao();

        janela.setVisible(true);
    }

    private void criarJanela() {
        janela.setSize(340, 650);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());
    }

    private void criarDisplay() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        historicoLabel.setForeground(Color.LIGHT_GRAY);
        historicoLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        historicoLabel.setHorizontalAlignment(JLabel.RIGHT);

        visorResultado.setForeground(Color.WHITE);
        visorResultado.setFont(new Font("SansSerif", Font.BOLD, 60));
        visorResultado.setHorizontalAlignment(JLabel.RIGHT);

        painel.add(historicoLabel, BorderLayout.NORTH);
        painel.add(visorResultado, BorderLayout.SOUTH);

        janela.add(painel, BorderLayout.NORTH);
    }

    private void criarPainelBotoes() {

        JPanel painel = new JPanel(new GridLayout(5, 4, 3, 3));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (String tecla : TECLAS) {

            JButton botao = new JButton(tecla);

            botao.setFont(new Font("Arial", Font.BOLD, 24));
            botao.setFocusable(false);
            botao.setBorder(new LineBorder(FUNDO));

            if (botoesFuncionais.contains(tecla)) {

                botao.setBackground(BOTAO_FUNCAO);
                botao.setForeground(Color.BLACK);

            } else if (botoesOperadores.contains(tecla)) {

                botao.setBackground(BOTAO_OPERADOR);
                botao.setForeground(Color.WHITE);

            } else {

                botao.setBackground(BOTAO_NUMERO);
                botao.setForeground(Color.WHITE);
            }

            botao.addActionListener(e -> executarComando(tecla));

            painel.add(botao);
        }

        janela.add(painel, BorderLayout.CENTER);
    }

    private void executarComando(String comando) {

        switch (comando) {

            case "AC":
                reiniciarCalculadora();
                break;

            case "DEL":
                apagarNumero();
                break;

            case "+/-":
                inverterSinal();
                break;

            case ".":
                adicionarPonto();
                break;

            case "=":
                calcular();
                break;

            case "+":
            case "-":
            case "*":
            case "/":
                selecionarOperacao(comando);
                break;

            default:
                inserirNumero(comando);
        }
    }

    private void inserirNumero(String numero) {

        if (limparProximoNumero) {

            visorResultado.setText(numero);
            limparProximoNumero = false;

        } else {

            String textoAtual = visorResultado.getText();

            if (textoAtual.equals("0")) {
                visorResultado.setText(numero);
            } else {
                visorResultado.setText(textoAtual + numero);
            }
        }

        atualizarCampoTexto();
    }

    private void adicionarPonto() {

        String textoAtual = visorResultado.getText();

        if (!textoAtual.contains(".")) {
            visorResultado.setText(textoAtual + ".");
        }

        atualizarCampoTexto();
    }

    private void apagarNumero() {

        String texto = visorResultado.getText();

        if (texto.length() <= 1) {

            visorResultado.setText("0");

        } else {

            visorResultado.setText(texto.substring(0, texto.length() - 1));
        }

        atualizarCampoTexto();
    }

    private void inverterSinal() {

        try {

            double numero = Double.parseDouble(visorResultado.getText());

            numero *= -1;

            visorResultado.setText(formatarNumero(numero));

            atualizarCampoTexto();

        } catch (NumberFormatException erro) {

            exibirMensagemErro("Valor inválido.");
        }
    }

    private void selecionarOperacao(String operacao) {

        try {

            valorInicial = Double.parseDouble(visorResultado.getText());

            operacaoAtual = operacao;

            historicoLabel.setText(formatarNumero(valorInicial) + " " + operacaoAtual);

            limparProximoNumero = true;

            atualizarCampoTexto();

        } catch (NumberFormatException erro) {

            exibirMensagemErro("Não foi possível ler o número.");
        }
    }

    private void calcular() {

        try {

            if (operacaoAtual.isEmpty()) {
                throw new ErroCalculadoraException("Escolha uma operação.");
            }

            double valorFinal = converterNumero(visorResultado.getText());

            double resultado = 0;

            if (operacaoAtual.equals("+")) {

                resultado = valorInicial + valorFinal;

            } else if (operacaoAtual.equals("-")) {

                resultado = valorInicial - valorFinal;

            } else if (operacaoAtual.equals("*")) {

                resultado = valorInicial * valorFinal;

            } else if (operacaoAtual.equals("/")) {

                if (valorFinal == 0) {
                    throw new ErroCalculadoraException("Não é permitido dividir por zero.");
                }

                resultado = valorInicial / valorFinal;
            }

            historicoLabel.setText(
                    formatarNumero(valorInicial)
                            + " "
                            + operacaoAtual
                            + " "
                            + formatarNumero(valorFinal)
                            + " ="
            );

            visorResultado.setText(formatarNumero(resultado));

            valorInicial = resultado;

            operacaoAtual = "";

            limparProximoNumero = true;

            atualizarCampoTexto();

        } catch (ErroCalculadoraException erro) {

            exibirMensagemErro(erro.getMessage());
        }
    }

    private double converterNumero(String texto) throws ErroCalculadoraException {

        try {

            return Double.parseDouble(texto);

        } catch (NumberFormatException erro) {

            throw new ErroCalculadoraException("Entrada inválida.");
        }
    }

    private void atualizarCampoTexto() {

        if (atualizandoCampo) return;

        atualizandoCampo = true;

        if (!operacaoAtual.isEmpty()) {

            campoConta.setText(
                    formatarNumero(valorInicial)
                            + " "
                            + operacaoAtual
                            + " "
                            + visorResultado.getText()
            );

        } else {

            campoConta.setText(visorResultado.getText());
        }

        atualizandoCampo = false;
    }

    private void reiniciarCalculadora() {

        valorInicial = 0;
        operacaoAtual = "";
        limparProximoNumero = false;

        visorResultado.setText("0");
        historicoLabel.setText("");
        campoConta.setText("");
    }

    private String formatarNumero(double valor) {

        if (valor % 1 == 0) {
            return String.valueOf((long) valor);
        }

        return String.valueOf(valor);
    }

    private void exibirMensagemErro(String mensagem) {

        JOptionPane.showMessageDialog(
                janela,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );

        reiniciarCalculadora();
    }

    private void criarCampoExpressao() {

        JPanel painelInferior = new JPanel(new BorderLayout());

        painelInferior.setBackground(FUNDO);
        painelInferior.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        JLabel titulo = new JLabel("Digite a expressão:");

        titulo.setForeground(Color.LIGHT_GRAY);

        painelInferior.add(titulo, BorderLayout.NORTH);

        campoConta.setBackground(new Color(40, 40, 50));
        campoConta.setForeground(Color.WHITE);
        campoConta.setCaretColor(Color.WHITE);

        campoConta.setFont(new Font("Arial", Font.PLAIN, 18));

        painelInferior.add(campoConta, BorderLayout.CENTER);

        campoConta.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                sincronizarCampo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sincronizarCampo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                sincronizarCampo();
            }
        });

        campoConta.addActionListener(e -> calcular());

        janela.add(painelInferior, BorderLayout.SOUTH);
    }

    private void sincronizarCampo() {

        if (atualizandoCampo) return;

        String texto = campoConta.getText().trim();

        if (texto.isEmpty()) {

            visorResultado.setText("0");
            historicoLabel.setText("");

            return;
        }

        try {

            Double.parseDouble(texto);

            visorResultado.setText(texto);

        } catch (Exception ignored) {

        }
    }

    // EXCEPTION PERSONALIZADA
    static class ErroCalculadoraException extends Exception {

        public ErroCalculadoraException(String mensagem) {
            super(mensagem);
        }
    }

    public static void main(String[] args) {

        new CalculadoraSwing();
    }
}