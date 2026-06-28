import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame implements ActionListener {

    JTextField visor;

    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    JButton soma, subtracao, multiplicacao, divisao;
    JButton igual, limpar;

    double numero1;
    double numero2;
    double resultado;

    String operacao;

    public Calculadora() {

        setTitle("Calculadora");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        visor = new JTextField();
        visor.setBounds(30, 30, 270, 50);
        visor.setFont(new Font("Arial", Font.BOLD, 24));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        add(visor);

        b7 = new JButton("7");
        b7.setBounds(30, 120, 60, 50);
        add(b7);

        b8 = new JButton("8");
        b8.setBounds(100, 120, 60, 50);
        add(b8);

        b9 = new JButton("9");
        b9.setBounds(170, 120, 60, 50);
        add(b9);

        divisao = new JButton("/");
        divisao.setBounds(240, 120, 60, 50);
        add(divisao);

        b4 = new JButton("4");
        b4.setBounds(30, 180, 60, 50);
        add(b4);

        b5 = new JButton("5");
        b5.setBounds(100, 180, 60, 50);
        add(b5);

        b6 = new JButton("6");
        b6.setBounds(170, 180, 60, 50);
        add(b6);

        multiplicacao = new JButton("*");
        multiplicacao.setBounds(240, 180, 60, 50);
        add(multiplicacao);

        b1 = new JButton("1");
        b1.setBounds(30, 240, 60, 50);
        add(b1);

        b2 = new JButton("2");
        b2.setBounds(100, 240, 60, 50);
        add(b2);

        b3 = new JButton("3");
        b3.setBounds(170, 240, 60, 50);
        add(b3);

        subtracao = new JButton("-");
        subtracao.setBounds(240, 240, 60, 50);
        add(subtracao);

        b0 = new JButton("0");
        b0.setBounds(30, 300, 130, 50);
        add(b0);

        igual = new JButton("=");
        igual.setBounds(170, 300, 60, 50);
        add(igual);

        soma = new JButton("+");
        soma.setBounds(240, 300, 60, 50);
        add(soma);

        limpar = new JButton("C");
        limpar.setBounds(30, 370, 270, 50);
        add(limpar);

        b0.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);

        soma.addActionListener(this);
        subtracao.addActionListener(this);
        multiplicacao.addActionListener(this);
        divisao.addActionListener(this);

        igual.addActionListener(this);
        limpar.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b0) {
            visor.setText(visor.getText() + "0");
        }

        if (e.getSource() == b1) {
            visor.setText(visor.getText() + "1");
        }

        if (e.getSource() == b2) {
            visor.setText(visor.getText() + "2");
        }

        if (e.getSource() == b3) {
            visor.setText(visor.getText() + "3");
        }

        if (e.getSource() == b4) {
            visor.setText(visor.getText() + "4");
        }

        if (e.getSource() == b5) {
            visor.setText(visor.getText() + "5");
        }

        if (e.getSource() == b6) {
            visor.setText(visor.getText() + "6");
        }

        if (e.getSource() == b7) {
            visor.setText(visor.getText() + "7");
        }

        if (e.getSource() == b8) {
            visor.setText(visor.getText() + "8");
        }

        if (e.getSource() == b9) {
            visor.setText(visor.getText() + "9");
        }

        try {

            if (e.getSource() == soma) {

                numero1 = Double.parseDouble(visor.getText());
                operacao = "+";
                visor.setText("");
            }

            if (e.getSource() == subtracao) {

                numero1 = Double.parseDouble(visor.getText());
                operacao = "-";
                visor.setText("");
            }

            if (e.getSource() == multiplicacao) {

                numero1 = Double.parseDouble(visor.getText());
                operacao = "*";
                visor.setText("");
            }

            if (e.getSource() == divisao) {

                numero1 = Double.parseDouble(visor.getText());
                operacao = "/";
                visor.setText("");
            }

            if (e.getSource() == igual) {

                numero2 = Double.parseDouble(visor.getText());

                if (operacao.equals("+")) {
                    resultado = numero1 + numero2;
                }

                if (operacao.equals("-")) {
                    resultado = numero1 - numero2;
                }

                if (operacao.equals("*")) {
                    resultado = numero1 * numero2;
                }

                if (operacao.equals("/")) {

                    if (numero2 == 0) {

                        throw new CalculadoraException(
                                "Nao existe divisao por zero!"
                        );
                    }

                    resultado = numero1 / numero2;
                }

                visor.setText(String.valueOf(resultado));
            }

            if (e.getSource() == limpar) {

                visor.setText("");
            }

        }

        catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                    null,
                    "Digite apenas numeros!"
            );
        }

        catch (CalculadoraException erro) {

            JOptionPane.showMessageDialog(
                    null,
                    erro.getMessage()
            );
        }
    }

    public static void main(String[] args) {

        new Calculadora();
    }
}