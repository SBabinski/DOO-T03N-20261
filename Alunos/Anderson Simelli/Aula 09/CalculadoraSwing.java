package aula09;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraSwing extends JFrame implements ActionListener {

    JTextField campo1;
    JTextField campo2;
    JTextField resultado;

    JButton somar;
    JButton subtrair;
    JButton multiplicar;
    JButton dividir;
    JButton limpar;

    public CalculadoraSwing() {

        setTitle("Calculadora");
        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 2, 10, 10));

        campo1 = new JTextField();
        campo2 = new JTextField();

        resultado = new JTextField();
        resultado.setEditable(false);

        somar = new JButton("+");
        subtrair = new JButton("-");
        multiplicar = new JButton("*");
        dividir = new JButton("/");
        limpar = new JButton("Limpar");

        somar.addActionListener(this);
        subtrair.addActionListener(this);
        multiplicar.addActionListener(this);
        dividir.addActionListener(this);
        limpar.addActionListener(this);

        add(new JLabel("Primeiro Número"));
        add(campo1);

        add(new JLabel("Segundo Número"));
        add(campo2);

        add(somar);
        add(subtrair);

        add(multiplicar);
        add(dividir);

        add(new JLabel("Resultado"));
        add(resultado);

        add(limpar);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            if (e.getSource() == limpar) {

                campo1.setText("");
                campo2.setText("");
                resultado.setText("");

                return;
            }

            double numero1 = lerNumero(campo1.getText());

            double numero2 = lerNumero(campo2.getText());

            double conta = 0;

            if (e.getSource() == somar) {

                conta = numero1 + numero2;
            }

            else if (e.getSource() == subtrair) {

                conta = numero1 - numero2;
            }

            else if (e.getSource() == multiplicar) {

                conta = numero1 * numero2;
            }

            else if (e.getSource() == dividir) {

                if (numero2 == 0) {

                    throw new EntradaInvalidaException(
                            "Não pode dividir por zero!"
                    );
                }

                conta = numero1 / numero2;
            }

            resultado.setText(String.valueOf(conta));

        }

        catch (EntradaInvalidaException erro) {

            JOptionPane.showMessageDialog(
                    null,
                    erro.getMessage()
            );
        }
    }

    public double lerNumero(String texto)
            throws EntradaInvalidaException {

        try {

            return Double.parseDouble(texto);

        }

        catch (NumberFormatException erro) {

            throw new EntradaInvalidaException(
                    "Digite apenas números!"
            );
        }
    }

    public static void main(String[] args) {

        new CalculadoraSwing();
    }
}