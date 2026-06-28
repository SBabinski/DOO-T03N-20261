import javax.swing.*;
import java.awt.*;

public class TelaCalculadora extends JFrame {

    private JTextField visor;
    private boolean novaEntrada = true;
    private Calculadora calculadora;

    public TelaCalculadora() {
        calculadora = new Calculadora();

        configurarJanela();
        criarVisor();
        criarBotoes();
    }

    private void configurarJanela() {
        setTitle("Calculadora");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void criarVisor() {
        visor = new JTextField("0");
        visor.setFont(new Font("Segoe UI", Font.BOLD, 36));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setEditable(false);
        visor.setBackground(Color.DARK_GRAY);
        visor.setForeground(Color.WHITE);

        add(visor, BorderLayout.NORTH);
    }

    private void criarBotoes() {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 4, 8, 8));
        painelBotoes.setBackground(Color.DARK_GRAY);

        String[] textosBotoes = {
                "C", "←", "%", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ",", "="
        };

        for (String texto : textosBotoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Segoe UI", Font.BOLD, 22));
            botao.addActionListener(e -> tratarClique(texto));
            painelBotoes.add(botao);
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    private void tratarClique(String texto) {
        try {
            if (texto.matches("[0-9]")) {
                adicionarNumero(texto);
                return;
            }

            switch (texto) {
                case ",":
                    adicionarVirgula();
                    break;

                case "C":
                    limpar();
                    break;

                case "←":
                    apagar();
                    break;

                case "+/-":
                    inverterSinal();
                    break;

                case "%":
                    porcentagem();
                    break;

                case "+":
                case "-":
                case "×":
                case "÷":
                    definirOperacao(texto);
                    break;

                case "=":
                    calcularResultado();
                    break;
            }

        } catch (CalculadoraException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void adicionarNumero(String numero) {
        if (novaEntrada || visor.getText().equals("0")) {
            visor.setText(numero);
            novaEntrada = false;
        } else {
            visor.setText(visor.getText() + numero);
        }
    }

    private void adicionarVirgula() {
        if (novaEntrada) {
            visor.setText("0,");
            novaEntrada = false;
        } else if (!visor.getText().contains(",")) {
            visor.setText(visor.getText() + ",");
        }
    }

    private void definirOperacao(String operacao) throws CalculadoraException {
        double numero = calculadora.converterNumero(visor.getText());

        calculadora.salvarOperacao(numero, operacao);
        novaEntrada = true;
    }

    private void calcularResultado() throws CalculadoraException {
        if (calculadora.getOperacao() == null || calculadora.getOperacao().isEmpty()) {
            return;
        }

        double segundoNumero = calculadora.converterNumero(visor.getText());
        double resultado = calculadora.calcular(segundoNumero);

        visor.setText(calculadora.formatar(resultado));
        novaEntrada = true;

        calculadora.setOperacao("");
    }

    private void limpar() {
        visor.setText("0");
        novaEntrada = true;
        calculadora.limpar();
    }

    private void apagar() {
        String texto = visor.getText();

        if (texto.length() > 1) {
            visor.setText(texto.substring(0, texto.length() - 1));
        } else {
            visor.setText("0");
            novaEntrada = true;
        }
    }

    private void inverterSinal() throws CalculadoraException {
        double numero = calculadora.converterNumero(visor.getText());
        numero *= -1;

        visor.setText(calculadora.formatar(numero));
    }

    private void porcentagem() throws CalculadoraException {
        double numero = calculadora.converterNumero(visor.getText());
        numero = numero / 100;

        visor.setText(calculadora.formatar(numero));
    }
}