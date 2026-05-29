
package aula09;

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

        Color preto = Color.BLACK;
        Color cinzaEscuro = new Color(30, 30, 30);

        Color roxo = new Color(128, 0, 128);

        Color roxoClaro = new Color(180, 100, 255);

        Color branco = Color.WHITE;

        setTitle("Calculadora Roxa");

        setSize(350, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(preto);

        tela = new JTextField("0");

        tela.setFont(new Font("Arial", Font.BOLD, 28));

        tela.setHorizontalAlignment(JTextField.RIGHT);

        tela.setEditable(true);

        tela.setBackground(preto);

        tela.setForeground(roxoClaro);

        tela.setCaretColor(branco);

        tela.setBorder(BorderFactory.createLineBorder(roxo, 3));

        add(tela, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();

        painelBotoes.setLayout(new GridLayout(4, 4, 5, 5));

        painelBotoes.setBackground(preto);

        String[] botoes = {

                "7", "8", "9", "÷",

                "4", "5", "6", "×",

                "1", "2", "3", "-",

                "C", "0", "=", "+"
        };

        for (String texto : botoes) {

            JButton botao = new JButton(texto);

            botao.setFont(new Font("Arial", Font.BOLD, 20));

            botao.setForeground(branco);

            if ("+-×÷=".contains(texto)) {

                botao.setBackground(roxo);

                botao.setBorder(
                        BorderFactory.createLineBorder(roxoClaro, 2)
                );
            }

            else if (texto.equals("C")) {

                botao.setBackground(new Color(80, 0, 0));

                botao.setBorder(
                        BorderFactory.createLineBorder(Color.RED, 2)
                );
            }

            else {

                botao.setBackground(cinzaEscuro);

                botao.setBorder(
                        BorderFactory.createLineBorder(Color.BLACK, 2)
                );
            }

            botao.setFocusPainted(false);

            botao.addActionListener(this);

            painelBotoes.add(botao);
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

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

                double segundoNumero =
                        Double.parseDouble(tela.getText());

                double resultado =
                        calcular(primeiroNumero, segundoNumero, operacao);

                if (resultado % 1 == 0) {

                    tela.setText(String.valueOf((int) resultado));

                } else {

                    tela.setText(String.valueOf(resultado));
                }

                inicioNovaEntrada = true;

                operacao = "";
            }

        } catch (EntradaInvalidaException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro Encontrado",
                    JOptionPane.ERROR_MESSAGE
            );

            tela.setText("0");

            inicioNovaEntrada = true;
        }
    }

    private void validarEntrada(String texto)
            throws EntradaInvalidaException {

        try {

            Double.parseDouble(texto);

        } catch (NumberFormatException e) {

            throw new EntradaInvalidaException(
                    "Erro: A entrada '" + texto +
                            "' contém caracteres inválidos!"
            );
        }
    }

    private double calcular(double num1,
                            double num2,
                            String op)
            throws EntradaInvalidaException {

        switch (op) {

            case "+":
                return num1 + num2;

            case "-":
                return num1 - num2;

            case "×":
                return num1 * num2;

            case "÷":

                if (num2 == 0) {

                    throw new EntradaInvalidaException(
                            "Erro: Não é possível dividir por zero!"
                    );
                }

                return num1 / num2;

            default:
                return num2;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new CalculadoraSwing().setVisible(true);
        });
    }
}

