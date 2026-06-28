import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    private JTextField campoResultado;

    private String numero1 = "";
    private String numero2 = "";
    private String operacao = "";

    public Calculadora() {

        setTitle("Calculadora Java Swing");
        setSize(420, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        campoResultado = new JTextField();

        campoResultado.setFont(new Font("Arial", Font.BOLD, 32));

        campoResultado.setHorizontalAlignment(JTextField.RIGHT);

        campoResultado.setEditable(false);

        campoResultado.setPreferredSize(new Dimension(400, 80));

        add(campoResultado, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();

        painelBotoes.setLayout(new GridLayout(5, 4, 10, 10));

        String[] botoes = {

                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "DEL", "%", "±"
        };

        for (String texto : botoes) {

            JButton botao = new JButton(texto);

            botao.setFont(new Font("Arial", Font.BOLD, 22));

            botao.addActionListener(this);

            painelBotoes.add(botao);
        }

        add(painelBotoes, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String comando = e.getActionCommand();

        try {

            if (comando.matches("[0-9]")) {

                campoResultado.setText(
                        campoResultado.getText() + comando);
            }

            else if (comando.equals(".")) {

                if (!campoResultado.getText().contains(".")) {

                    campoResultado.setText(
                            campoResultado.getText() + ".");
                }
            }

            else if (comando.matches("[+\\-*/%]")) {

                validarEntrada(campoResultado.getText());

                numero1 = campoResultado.getText();

                operacao = comando;

                campoResultado.setText("");
            }

            else if (comando.equals("=")) {

                validarEntrada(campoResultado.getText());

                numero2 = campoResultado.getText();

                double n1 = Double.parseDouble(numero1);

                double n2 = Double.parseDouble(numero2);

                double resultado = calcular(n1, n2, operacao);

                campoResultado.setText(
                        String.valueOf(resultado));
            }

            else if (comando.equals("C")) {

                limpar();
            }

            else if (comando.equals("DEL")) {

                String texto = campoResultado.getText();

                if (!texto.isEmpty()) {

                    campoResultado.setText(
                            texto.substring(0, texto.length() - 1));
                }
            }

            else if (comando.equals("±")) {

                if (!campoResultado.getText().isEmpty()) {

                    double valor = Double.parseDouble(
                            campoResultado.getText());

                    valor *= -1;

                    campoResultado.setText(
                            String.valueOf(valor));
                }
            }

        }

        catch (CalculadoraException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            limpar();
        }

        catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrada inválida!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            limpar();
        }

        catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro inesperado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            limpar();
        }
    }

    private double calcular(double n1, double n2, String op)
            throws CalculadoraException {

        switch (op) {

            case "+":
                return n1 + n2;

            case "-":
                return n1 - n2;

            case "*":
                return n1 * n2;

            case "/":

                if (n2 == 0) {

                    throw new CalculadoraException(
                            "Não é permitido divisão por zero!");
                }

                return n1 / n2;

            case "%":

                if (n2 == 0) {

                    throw new CalculadoraException(
                            "Não é permitido módulo por zero!");
                }

                return n1 % n2;

            default:

                throw new CalculadoraException(
                        "Operação inválida!");
        }
    }

    private void validarEntrada(String valor)
            throws CalculadoraException {

        if (valor == null || valor.isEmpty()) {

            throw new CalculadoraException(
                    "Digite um número!");
        }

        try {

            Double.parseDouble(valor);

        }

        catch (NumberFormatException e) {

            throw new CalculadoraException(
                    "Apenas números são permitidos!");
        }
    }

    private void limpar() {

        campoResultado.setText("");

        numero1 = "";

        numero2 = "";

        operacao = "";
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new Calculadora();
        });
    }
}

class CalculadoraException extends Exception {

    public CalculadoraException(String mensagem) {

        super(mensagem);
    }
}
