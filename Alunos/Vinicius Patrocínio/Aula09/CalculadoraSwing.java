import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class EntradaInvalidaException extends Exception {
    public EntradaInvalidaException(String mensagem) {
        super(mensagem);
    }
}

public class CalculadoraSwing extends JFrame implements ActionListener {

    private JTextField tela;
    private double primeiroNumero = 0;
    private String operacao = "";
    private boolean inicioNovaEntrada = true;

    public CalculadoraSwing() {
        setTitle("Calculadora Estilo iOS");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout(10, 10));

        tela = new JTextField("0");
        tela.setFont(new Font("Arial", Font.PLAIN, 45));
        tela.setHorizontalAlignment(JTextField.RIGHT);
        tela.setEditable(false);
        tela.setBackground(Color.BLACK);
        tela.setForeground(Color.WHITE);
        tela.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        add(tela, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 4, 10, 10));
        painelBotoes.setBackground(Color.BLACK);

        String[] botoes = {
                "7", "8", "9", "÷",
                "4", "5", "6", "×",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        Color corCinzaEscuro = new Color(51, 51, 51);
        Color corLaranja = new Color(255, 159, 10);
        Color corCinzaClaro = new Color(165, 165, 165);

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 24));
            botao.setFocusable(false);
            botao.setBorderPainted(false);
            botao.setOpaque(true);

            if (texto.equals("C")) {
                botao.setBackground(corCinzaClaro);
                botao.setForeground(Color.BLACK);
            } else if ("÷×-+=".contains(texto)) {
                botao.setBackground(corLaranja);
                botao.setForeground(Color.WHITE);
            } else {
                botao.setBackground(corCinzaEscuro);
                botao.setForeground(Color.WHITE);
            }

            botao.addActionListener(this);
            painelBotoes.add(botao);
        }

        JPanel painelContainer = new JPanel(new BorderLayout());
        painelContainer.setBackground(Color.BLACK);
        painelContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        painelContainer.add(painelBotoes, BorderLayout.CENTER);

        add(painelContainer, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            if ("0123456789".contains(comando)) {
                if (inicioNovaEntrada || tela.getText().equals("0")) {
                    tela.setText(comando);
                    inicioNovaEntrada = false;
                } else {
                    tela.setText(tela.getText() + comando);
                }
            }
            else if (comando.equals("C")) {
                tela.setText("0");
                primeiroNumero = 0;
                operacao = "";
                inicioNovaEntrada = true;
            }
            else if ("+-×÷".contains(comando)) {
                validarEntrada(tela.getText());
                primeiroNumero = Double.parseDouble(tela.getText());
                operacao = comando;
                inicioNovaEntrada = true;
            }
            else if (comando.equals("=")) {
                validarEntrada(tela.getText());
                double segundoNumero = Double.parseDouble(tela.getText());
                double resultado = calcular(primeiroNumero, segundoNumero, operacao);

                if (resultado % 1 == 0) {
                    tela.setText(String.valueOf((int) resultado));
                } else {
                    tela.setText(String.valueOf(resultado));
                }
                inicioNovaEntrada = true;
                operacao = "";
            }
        } catch (EntradaInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro Encontrado", JOptionPane.ERROR_MESSAGE);
            tela.setText("0");
            inicioNovaEntrada = true;
        }
    }

    private void validarEntrada(String texto) throws EntradaInvalidaException {
        try {
            Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException("Erro: A entrada '" + texto + "' contém caracteres inválidos!");
        }
    }

    private double calcular(double num1, double num2, String op) throws EntradaInvalidaException {
        switch (op) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "×": return num1 * num2;
            case "÷":
                if (num2 == 0) {
                    throw new EntradaInvalidaException("Erro: Não é possível dividir por zero!");
                }
                return num1 / num2;
            default: return num2;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraSwing().setVisible(true);
        });
    }
}