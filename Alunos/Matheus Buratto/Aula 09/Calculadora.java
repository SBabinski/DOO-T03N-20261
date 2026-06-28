import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Calculadora extends JFrame {
    private final JTextField display;
    private final StringBuilder currentInput;

    public Calculadora() {
        JFrame frame = new JFrame("Calculadora Simples");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        currentInput = new StringBuilder();

        JPanel panel = new JPanel(new BorderLayout());
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "x",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("=")) {
                try {
                    calcular();
                } catch (CalculadoraException ex) {
                    ex.exibirMensagem(Calculadora.this, display);
                    currentInput.setLength(0);
                }
            } else {
                if (display.getText().startsWith("Erro") || display.getText().contains("ERRO")) {
                    currentInput.setLength(0);
                }

                if ("+-x/".contains(command)) {
                    currentInput.append(" ").append(command).append(" ");
                } else {
                    currentInput.append(command);
                }

                display.setText(currentInput.toString());
            }
        }
    }

    private void calcular() throws CalculadoraException {
        String expression = currentInput.toString().trim();
        String[] tokens = expression.split(" ");
        if (tokens.length != 3) throw new ExpressaoInvalidaException();

        double num1, num2;
        try {
            num1 = Double.parseDouble(tokens[0]);
            num2 = Double.parseDouble(tokens[2]);
        } catch (NumberFormatException e) {
            throw new EntradaInvalidaException();
        }

        String operator = tokens[1];
        double result;

        switch (operator) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "x" -> result = num1 * num2;
            case "/" -> {
                if (num2 == 0) throw new DivisaoPorZeroException();
                result = num1 / num2;
            }
            default -> throw new ExpressaoInvalidaException();
        }

        display.setText(String.valueOf(result));
        currentInput.setLength(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculadora::new);
    }
}