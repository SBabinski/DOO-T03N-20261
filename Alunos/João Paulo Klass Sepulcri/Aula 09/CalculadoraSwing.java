import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraSwing extends JFrame {
    private static final String MULTIPLICACAO = "\u00D7";
    private static final String DIVISAO = "\u00F7";

    private JTextField visor;
    private double primeiroNumero;
    private String operacao;
    private boolean operacaoEscolhida;
    private boolean resultadoMostrado;

    public CalculadoraSwing() {
        setTitle("Calculadora Swing");
        setSize(320, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        visor = new JTextField();
        visor.setFont(new Font("Arial", Font.PLAIN, 28));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        add(visor, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 4, 5, 5));

        adicionarBotao(painelBotoes, "7");
        adicionarBotao(painelBotoes, "8");
        adicionarBotao(painelBotoes, "9");
        adicionarBotao(painelBotoes, "+");

        adicionarBotao(painelBotoes, "4");
        adicionarBotao(painelBotoes, "5");
        adicionarBotao(painelBotoes, "6");
        adicionarBotao(painelBotoes, "-");

        adicionarBotao(painelBotoes, "1");
        adicionarBotao(painelBotoes, "2");
        adicionarBotao(painelBotoes, "3");
        adicionarBotao(painelBotoes, MULTIPLICACAO);

        adicionarBotao(painelBotoes, "C");
        adicionarBotao(painelBotoes, "0");
        adicionarBotao(painelBotoes, "=");
        adicionarBotao(painelBotoes, DIVISAO);

        add(painelBotoes, BorderLayout.CENTER);
    }

    private void adicionarBotao(JPanel painel, String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 22));

        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                try {
                    tratarClique(texto);
                } catch (EntradaInvalidaException erro) {
                    JOptionPane.showMessageDialog(CalculadoraSwing.this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        painel.add(botao);
    }

    private void tratarClique(String texto) throws EntradaInvalidaException {
        if (texto.equals("C")) {
            limpar();
        } else if (texto.equals("=")) {
            calcularResultado();
        } else if (ehOperacao(texto)) {
            escolherOperacao(texto);
        } else {
            adicionarNumero(texto);
        }
    }

    private boolean ehOperacao(String texto) {
        return texto.equals("+") || texto.equals("-") || texto.equals(MULTIPLICACAO) || texto.equals(DIVISAO);
    }

    private void adicionarNumero(String numero) {
        if (resultadoMostrado) {
            visor.setText("");
            resultadoMostrado = false;
        }

        visor.setText(visor.getText() + numero);
    }

    private void escolherOperacao(String texto) throws EntradaInvalidaException {
        primeiroNumero = lerNumero();
        operacao = texto;
        operacaoEscolhida = true;
        resultadoMostrado = false;
        visor.setText("");
    }

    private void calcularResultado() throws EntradaInvalidaException {
        if (!operacaoEscolhida) {
            throw new EntradaInvalidaException("Informe dois numeros e uma operacao antes de calcular.");
        }

        double segundoNumero = lerNumero();
        double resultado;

        if (operacao.equals("+")) {
            resultado = primeiroNumero + segundoNumero;
        } else if (operacao.equals("-")) {
            resultado = primeiroNumero - segundoNumero;
        } else if (operacao.equals(MULTIPLICACAO)) {
            resultado = primeiroNumero * segundoNumero;
        } else if (operacao.equals(DIVISAO)) {
            if (segundoNumero == 0) {
                throw new EntradaInvalidaException("Nao e possivel dividir por zero.");
            }
            resultado = primeiroNumero / segundoNumero;
        } else {
            throw new EntradaInvalidaException("Operacao invalida.");
        }

        visor.setText(String.valueOf(resultado));
        operacaoEscolhida = false;
        resultadoMostrado = true;
    }

    private double lerNumero() throws EntradaInvalidaException {
        String texto = visor.getText().trim();

        if (texto.isEmpty()) {
            throw new EntradaInvalidaException("Digite um numero antes de continuar.");
        }

        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException erro) {
            throw new EntradaInvalidaException("Digite apenas numeros validos.");
        }
    }

    private void limpar() {
        visor.setText("");
        primeiroNumero = 0;
        operacao = "";
        operacaoEscolhida = false;
        resultadoMostrado = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CalculadoraSwing calculadora = new CalculadoraSwing();
                calculadora.setVisible(true);
            }
        });
    }
}
