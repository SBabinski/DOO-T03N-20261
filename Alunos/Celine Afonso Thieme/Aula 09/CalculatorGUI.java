
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame implements ActionListener {

    private JTextField display;

    private String primeiroNumero = "";
    private String operacao = "";

    private Calculator calculadora;

    public CalculatorGUI() {

        calculadora = new Calculator();

        // Configurações da janela
        setTitle("Calculadora");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setBackground(new Color(32, 32, 32));

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(50, 50, 50));
        display.setForeground(Color.WHITE);
        display.setPreferredSize(new Dimension(350, 80));

        painel.add(display, BorderLayout.NORTH);

        // Painel dos botões
        JPanel botoes = new JPanel();
        botoes.setLayout(new GridLayout(4, 4, 10, 10));
        botoes.setBackground(new Color(32, 32, 32));

        // Botões
        String[] textos = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String texto : textos) {

            JButton botao = new JButton(texto);

            botao.setFont(new Font("Arial", Font.BOLD, 22));
            botao.setFocusPainted(false);

            // Cores
            if (texto.equals("+") || texto.equals("-") ||
                texto.equals("*") || texto.equals("/") ||
                texto.equals("=")) {

                botao.setBackground(new Color(255, 140, 0));
                botao.setForeground(Color.WHITE);

            } else if (texto.equals("C")) {

                botao.setBackground(new Color(200, 50, 50));
                botao.setForeground(Color.WHITE);

            } else {

                botao.setBackground(new Color(70, 70, 70));
                botao.setForeground(Color.WHITE);
            }

            botao.addActionListener(this);
            botoes.add(botao);
        }

        painel.add(botoes, BorderLayout.CENTER);

        add(painel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String comando = e.getActionCommand();

        // Se for número
        if (comando.matches("[0-9]")) {

            display.setText(display.getText() + comando);
        }

        // Botão limpar
        else if (comando.equals("C")) {

            display.setText("");
            primeiroNumero = "";
            operacao = "";
        }

        // Operações
        else if (comando.equals("+") || comando.equals("-") ||
                 comando.equals("*") || comando.equals("/")) {

            primeiroNumero = display.getText();
            operacao = comando;
            display.setText("");
        }

        // Resultado
        else if (comando.equals("=")) {

            try {

                double num1 = Double.parseDouble(primeiroNumero);
                double num2 = Double.parseDouble(display.getText());

                double resultado = calculadora.calcular(num1, num2, operacao);

                display.setText(calculadora.formatarResultado(resultado));

            } catch (NumberFormatException erro) {

                JOptionPane.showMessageDialog(
                        null,
                        "Digite apenas números!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (CalculatorException erro) {

                JOptionPane.showMessageDialog(
                        null,
                        erro.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {

        new CalculatorGUI();
    }
}
