import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class CalculadoraException extends Exception {
    public CalculadoraException(String mensagem) {
        super(mensagem);
    }
}

public class Calculadora extends JFrame implements ActionListener {

    // Display grande (numero atual)
    JTextField display = new JTextField("0");

    // Display pequeno em cima (historico da expressao)
    JLabel historico = new JLabel(" ");

    // Variaveis de controle
    double numero1 = 0;
    double numero2 = 0;
    String operacao = "";
    boolean novoNumero = true;
    String expressao = ""; // guarda tudo que aparece no historico

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel do display (historico + numero atual)
        JPanel painelDisplay = new JPanel();
        painelDisplay.setLayout(new BorderLayout());
        painelDisplay.setBackground(Color.WHITE);

        // Historico pequeno em cima
        historico.setFont(new Font("Arial", Font.PLAIN, 14));
        historico.setHorizontalAlignment(SwingConstants.RIGHT);
        historico.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        painelDisplay.add(historico, BorderLayout.NORTH);

        // Display grande embaixo
        display.setFont(new Font("Arial", Font.PLAIN, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        display.setBackground(Color.WHITE);
        painelDisplay.add(display, BorderLayout.CENTER);

        add(painelDisplay, BorderLayout.NORTH);

        // Painel dos botoes
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 4, 5, 5));

        String[] botoes = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "x",
            "0", ".", "=", "/",
            "C", "", "", ""
        };

        for (String texto : botoes) {
            if (texto.equals("")) {
                painelBotoes.add(new JLabel());
            } else {
                JButton btn = new JButton(texto);
                btn.setFont(new Font("Arial", Font.PLAIN, 20));
                btn.addActionListener(this);
                painelBotoes.add(btn);
            }
        }

        add(painelBotoes, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String clicado = e.getActionCommand();

        try {
            if (clicado.equals("C")) {
                // Limpa tudo
                display.setText("0");
                historico.setText(" ");
                expressao = "";
                numero1 = 0;
                numero2 = 0;
                operacao = "";
                novoNumero = true;

            } else if (clicado.equals("+") || clicado.equals("-") ||
                       clicado.equals("x") || clicado.equals("/")) {

                // Guarda numero1 e operacao, atualiza historico
                numero1 = Double.parseDouble(display.getText());
                operacao = clicado;

                // Monta o historico: adiciona numero + operador
                expressao = expressao + display.getText() + " " + clicado + " ";
                historico.setText(expressao);

                novoNumero = true;

            } else if (clicado.equals("=")) {
                // Calcula o resultado
                numero2 = Double.parseDouble(display.getText());
                double resultado = calcular(numero1, numero2, operacao);

                // Mostra a expressao completa no historico
                historico.setText(expressao + display.getText() + " =");
                expressao = ""; // reseta para proxima conta

                // Mostra resultado no display
                if (resultado == (int) resultado) {
                    display.setText(String.valueOf((int) resultado));
                } else {
                    display.setText(String.valueOf(resultado));
                }

                novoNumero = true;

            } else if (clicado.equals(".")) {
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }

            } else {
                // Numero clicado
                if (novoNumero) {
                    display.setText(clicado);
                    novoNumero = false;
                } else {
                    display.setText(display.getText() + clicado);
                }
            }

        } catch (CalculadoraException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            display.setText("0");
            historico.setText(" ");
            expressao = "";
            novoNumero = true;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Entrada invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
            display.setText("0");
            historico.setText(" ");
            expressao = "";
            novoNumero = true;
        }
    }

    double calcular(double n1, double n2, String op) throws CalculadoraException {
        if (op.equals("+")) return n1 + n2;
        if (op.equals("-")) return n1 - n2;
        if (op.equals("x")) return n1 * n2;
        if (op.equals("/")) {
            if (n2 == 0) {
                throw new CalculadoraException("Erro: divisao por zero nao e permitida!");
            }
            return n1 / n2;
        }
        throw new CalculadoraException("Operacao invalida!");
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}