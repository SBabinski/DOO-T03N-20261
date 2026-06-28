import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class Calculadora extends JFrame implements ActionListener {

    interface Operacao {
        double somar(double a, double b);
        double subtrair(double a, double b);
        double multiplicar(double a, double b);
        double dividir(double a, double b);
    }

    static class CalculadoraException extends RuntimeException {
        public CalculadoraException(String msg) {
            super(msg);
        }
    }

    static class CalculadoraBasica implements Operacao {

        public double somar(double a, double b) {
            return a + b;
        }

        public double subtrair(double a, double b) {
            return a - b;
        }

        public double multiplicar(double a, double b) {
            return a * b;
        }

        public double dividir(double a, double b) {
            if (b == 0) {
                throw new CalculadoraException("Divisão por zero não permitida!");
            }
            return a / b;
        }
    }

    private JTextField display;
    private double numero1 = 0;
    private String operador = "";
    private boolean novaEntrada = true;

    private final CalculadoraBasica calc = new CalculadoraBasica();

    public Calculadora() {

        setTitle("Calculadora");
        setSize(320, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 26));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(4, 4, 5, 5));

        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 18));
            botao.addActionListener(this);
            painel.add(botao);
        }

        add(painel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        try {

            if (cmd.matches("[0-9]")) {

                if (novaEntrada) {
                    display.setText(cmd);
                    novaEntrada = false;
                } else {
                    display.setText(display.getText() + cmd);
                }

            } else if (cmd.equals("C")) {
                display.setText("0");
                numero1 = 0;
                operador = "";
                novaEntrada = true;
            } else if (cmd.equals("=")) {

                double numero2 = Double.parseDouble(display.getText());

                double resultado = switch (operador) {
                    case "+" -> calc.somar(numero1, numero2);
                    case "-" -> calc.subtrair(numero1, numero2);
                    case "*" -> calc.multiplicar(numero1, numero2);
                    case "/" -> calc.dividir(numero1, numero2);
                    default -> numero2;
                };

                display.setText(String.valueOf(resultado));
                novaEntrada = true;
            } else {
                numero1 = Double.parseDouble(display.getText());
                operador = cmd;
                novaEntrada = true;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Entrada invalida!");
            display.setText("0");
        } catch (CalculadoraException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
            display.setText("0");
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}
