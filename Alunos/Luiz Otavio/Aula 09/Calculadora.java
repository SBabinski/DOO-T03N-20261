import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame {

    private static final Color COR_FUNDO    = new Color(30, 30, 30);
    private static final Color COR_DISPLAY  = new Color(20, 20, 20);
    private static final Color COR_BOTAO    = new Color(60, 60, 65);
    private static final Font  FONTE_DISPLAY = new Font("SansSerif", Font.PLAIN, 32);
    private static final Font  FONTE_BOTAO   = new Font("SansSerif", Font.BOLD, 20);

    private static final String[] BOTOES = {
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "-",
        "C", "0", "=", "+"
    };

    private JTextField campoDisplay;
    private double     primeiroNumero;
    private String     operadorAtual;
    private boolean    novaEntrada;

    public Calculadora() {
        configurarJanela();
        add(criarDisplay(), BorderLayout.NORTH);
        add(criarPainelBotoes(), BorderLayout.CENTER);
        setSize(300, 380);
        setLocationRelativeTo(null);
    }

    private void configurarJanela() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(COR_FUNDO);
    }

    private JTextField criarDisplay() {
        campoDisplay = new JTextField("0");
        campoDisplay.setHorizontalAlignment(JTextField.RIGHT);
        campoDisplay.setFont(FONTE_DISPLAY);
        campoDisplay.setBackground(COR_DISPLAY);
        campoDisplay.setForeground(Color.WHITE);
        campoDisplay.setCaretColor(Color.WHITE);
        campoDisplay.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        campoDisplay.setEditable(false);
        return campoDisplay;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new GridLayout(4, 4, 6, 6));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        for (String rotulo : BOTOES) {
            painel.add(criarBotao(rotulo));
        }

        return painel;
    }

    private JButton criarBotao(String rotulo) {
        JButton botao = new JButton(rotulo);
        botao.setFont(FONTE_BOTAO);
        botao.setBackground(COR_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.addActionListener(new AcaoBotao(rotulo));
        return botao;
    }

    private double converterParaNumero(String texto) {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada invalida: \"" + texto + "\".", "ENTRADA_INVALIDA");
        }
    }

    private double calcular(double a, double b, String operador) {
        switch (operador) {
            case "+": return a + b;
            case "-": return a - b;
            case "×": return a * b;
            case "÷":
                if (b == 0) throw new CalculadoraException("Divisao por zero nao e permitida.", "DIVISAO_POR_ZERO");
                return a / b;
            default:
                throw new CalculadoraException("Operador desconhecido: " + operador, "OPERADOR_INVALIDO");
        }
    }

    private String formatarResultado(double valor) {
        if (valor == Math.floor(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }

    private void exibirErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void resetar() {
        campoDisplay.setText("0");
        operadorAtual = null;
        primeiroNumero = 0;
        novaEntrada = false;
    }

    private class AcaoBotao implements ActionListener {

        private final String rotulo;

        public AcaoBotao(String rotulo) {
            this.rotulo = rotulo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                processarClique(rotulo);
            } catch (CalculadoraException ex) {
                exibirErro(ex.getMessage());
                resetar();
            } catch (Exception ex) {
                exibirErro("Erro inesperado: " + ex.getMessage());
                resetar();
            }
        }

        private void processarClique(String botao) {
            switch (botao) {
                case "C":
                    resetar();
                    break;

                case "+": case "-": case "×": case "÷":
                    primeiroNumero = converterParaNumero(campoDisplay.getText());
                    operadorAtual  = botao;
                    novaEntrada    = true;
                    break;

                case "=":
                    if (operadorAtual != null) {
                        double resultado = calcular(primeiroNumero, converterParaNumero(campoDisplay.getText()), operadorAtual);
                        campoDisplay.setText(formatarResultado(resultado));
                        operadorAtual = null;
                        novaEntrada   = true;
                    }
                    break;

                default:
                    if (novaEntrada || campoDisplay.getText().equals("0")) {
                        campoDisplay.setText(botao);
                        novaEntrada = false;
                    } else if (campoDisplay.getText().length() < 12) {
                        campoDisplay.setText(campoDisplay.getText() + botao);
                    }
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora().setVisible(true));
    }
}
