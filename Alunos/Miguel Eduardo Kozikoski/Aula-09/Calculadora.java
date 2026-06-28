import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    private JTextField visor;
    private JPanel painelBotoes;

    private final String[] botoesTexto = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
    };

    private JButton[] botoes = new JButton[16];

    private double num1 = 0;
    private String operador = "";
    private boolean novaOperacao = true;

    public Calculadora() {
        setTitle("Calculadora Swing");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        visor = new JTextField("0");
        visor.setFont(new Font("Arial", Font.BOLD, 28));
        visor.setEditable(false);
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setBackground(Color.WHITE);
        visor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(visor, BorderLayout.NORTH);

        painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 4, 5, 5));

        for (int i = 0; i < botoesTexto.length; i++) {
            botoes[i] = new JButton(botoesTexto[i]);
            botoes[i].setFont(new Font("Arial", Font.BOLD, 20));
            botoes[i].setFocusPainted(false);
            botoes[i].addActionListener(this);

            if (botoesTexto[i].matches("[/\\-*+=C]")) {
                botoes[i].setBackground(new Color(230, 230, 230));
            }

            painelBotoes.add(botoes[i]);
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.matches("[0-9]")) {
            if (novaOperacao || visor.getText().equals("0")) {
                visor.setText(comando);
                novaOperacao = false;
            } else {
                visor.setText(visor.getText() + comando);
            }
        }
        else if (comando.equals("C")) {
            visor.setText("0");
            num1 = 0;
            operador = "";
            novaOperacao = true;
        }
        else if (comando.equals("=")) {
            if (!operador.isEmpty()) {
                double num2 = Double.parseDouble(visor.getText());
                double resultado = calcular(num1, num2, operador);

                if (resultado % 1 == 0) {
                    visor.setText(String.valueOf((int) resultado));
                } else {
                    visor.setText(String.valueOf(resultado));
                }
                operador = "";
                novaOperacao = true;
            }
        }
        else {
            num1 = Double.parseDouble(visor.getText());
            operador = comando;
            novaOperacao = true;
        }
    }

    private double calcular(double n1, double n2, String op) {
        switch (op) {
            case "+": return n1 + n2;
            case "-": return n1 - n2;
            case "*": return n1 * n2;
            case "/":
                if (n2 == 0) {
                    JOptionPane.showMessageDialog(this, "Erro: Divisão por zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
                return n1 / n2;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculadora calc = new Calculadora();
            calc.setVisible(true);
        });
    }
}