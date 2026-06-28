import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Exception personalizada
class CalculadoraException extends Exception {

    public CalculadoraException(String mensagem) {
        super(mensagem);
    }
}

public class Calculadoraswing extends JFrame implements ActionListener {

    private JTextField campo1;
    private JTextField campo2;
    private JTextField resultado;

    private JButton soma;
    private JButton subtracao;
    private JButton multiplicacao;
    private JButton divisao;

    // Botões numéricos
    private JButton[] numeros = new JButton[10];

    public CalculadoraSwing() {

        setTitle("Calculadora Simples");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel superior
        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 5, 5));

        campo1 = new JTextField();
        campo2 = new JTextField();
        resultado = new JTextField();

        resultado.setEditable(false);

        painelCampos.add(new JLabel("Primeiro número:"));
        painelCampos.add(campo1);

        painelCampos.add(new JLabel("Segundo número:"));
        painelCampos.add(campo2);

        painelCampos.add(new JLabel("Resultado:"));
        painelCampos.add(resultado);

        add(painelCampos, BorderLayout.NORTH);

        // Painel central para números
        JPanel painelNumeros = new JPanel(new GridLayout(4, 3, 5, 5));

        for (int i = 1; i <= 9; i++) {
            numeros[i] = new JButton(String.valueOf(i));
            numeros[i].addActionListener(this);
            painelNumeros.add(numeros[i]);
        }

        numeros[0] = new JButton("0");
        numeros[0].addActionListener(this);

        painelNumeros.add(new JLabel(""));
        painelNumeros.add(numeros[0]);
        painelNumeros.add(new JLabel(""));

        add(painelNumeros, BorderLayout.CENTER);

        // Painel inferior para operações
        JPanel painelOperacoes = new JPanel(new GridLayout(1, 4, 5, 5));

        soma = new JButton("+");
        subtracao = new JButton("-");
        multiplicacao = new JButton("×");
        divisao = new JButton("÷");

        soma.addActionListener(this);
        subtracao.addActionListener(this);
        multiplicacao.addActionListener(this);
        divisao.addActionListener(this);

        painelOperacoes.add(soma);
        painelOperacoes.add(subtracao);
        painelOperacoes.add(multiplicacao);
        painelOperacoes.add(divisao);

        add(painelOperacoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Método para validar os números
    public double validarNumero(String valor) throws CalculadoraException {

        try {
            return Double.parseDouble(valor);

        } catch (NumberFormatException e) {

            throw new CalculadoraException(
                "Entrada inválida. Digite apenas números."
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object botao = e.getSource();

        // Inserir números no campo selecionado
        for (int i = 0; i <= 9; i++) {

            if (botao == numeros[i]) {

                if (campo1.hasFocus()) {
                    campo1.setText(campo1.getText() + i);

                } else if (campo2.hasFocus()) {
                    campo2.setText(campo2.getText() + i);
                }
                return;
            }
        }

        try {

            double num1 = validarNumero(campo1.getText());
            double num2 = validarNumero(campo2.getText());

            double res = 0;

            if (botao == soma) {
                res = num1 + num2;
            }

            else if (botao == subtracao) {
                res = num1 - num2;
            }

            else if (botao == multiplicacao) {
                res = num1 * num2;
            }

            else if (botao == divisao) {

                if (num2 == 0) {
                    throw new CalculadoraException(
                        "Erro: divisão por zero não é permitida."
                    );
                }

                res = num1 / num2;
            }

            resultado.setText(String.valueOf(res));

        } catch (CalculadoraException ex) {

            JOptionPane.showMessageDialog(
                null,
                ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {

        new CalculadoraSwing();
    }
}