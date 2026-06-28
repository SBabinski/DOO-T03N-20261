import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    private static double primeiroNumero = 0;
    private static String operacao = "";
    private static boolean aguardandoSegundoNumero = false;

    public static void main(String[] args) {

        // Janela principal 
        JFrame frame = new JFrame("CALCULADORA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(415, 340);

        // Painel com layout absoluto (setBounds) 
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY);

        // Display principal
        // Mostra o número que está sendo digitado ou o resultado da operação.
        JLabel display = new JLabel("0", SwingConstants.RIGHT);
        display.setBounds(1, 5, 399, 60);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setOpaque(true);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Label de mensagens de erro 
        // Exibe em vermelho as mensagens lançadas por CalculadoraException.
        JLabel labelErro = new JLabel("", SwingConstants.CENTER);
        labelErro.setBounds(1, 65, 399, 25);
        labelErro.setForeground(Color.RED);
        labelErro.setFont(new Font("Arial", Font.PLAIN, 12));

        // Botões numéricos
        JButton button1 = new JButton("1");  button1.setBounds(1,   255, 80, 40);
        JButton button2 = new JButton("2");  button2.setBounds(81,  255, 80, 40);
        JButton button3 = new JButton("3");  button3.setBounds(161, 255, 80, 40);
        JButton button4 = new JButton("4");  button4.setBounds(241, 255, 80, 40);
        JButton button5 = new JButton("5");  button5.setBounds(321, 255, 80, 40);
        JButton button6 = new JButton("6");  button6.setBounds(1,   215, 80, 40);
        JButton button7 = new JButton("7");  button7.setBounds(81,  215, 80, 40);
        JButton button8 = new JButton("8");  button8.setBounds(161, 215, 80, 40);
        JButton button9 = new JButton("9");  button9.setBounds(241, 215, 80, 40);
        JButton button0 = new JButton("0");  button0.setBounds(321, 215, 80, 40);

        // Botões de operação
        JButton buttonMais     = new JButton("+"); buttonMais.setBounds(1,   175, 80, 40);
        JButton buttonMenos    = new JButton("-"); buttonMenos.setBounds(81,  175, 80, 40);
        JButton buttonVezes    = new JButton("×"); buttonVezes.setBounds(161, 175, 80, 40);
        JButton buttonDividido = new JButton("÷"); buttonDividido.setBounds(241, 175, 80, 40);
        JButton buttonIgual    = new JButton("="); buttonIgual.setBounds(321, 175, 80, 40);

        // Botão limpar (C)
        JButton buttonC = new JButton("C");
        buttonC.setBounds(1, 135, 399, 40);
        buttonC.setBackground(new Color(220, 80, 80));
        buttonC.setForeground(Color.WHITE);
        buttonC.setFont(new Font("Arial", Font.BOLD, 14));

        // LISTENER: dígitos (0–9)
        ActionListener digitoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String digito = ((JButton) e.getSource()).getText();
                labelErro.setText(""); // limpa erro anterior

                if (aguardandoSegundoNumero) {
                    display.setText(digito);
                    aguardandoSegundoNumero = false;
                } else {
                    String atual = display.getText();
                    display.setText(atual.equals("0") ? digito : atual + digito);
                }
            }
        };

        button0.addActionListener(digitoListener);
        button1.addActionListener(digitoListener);
        button2.addActionListener(digitoListener);
        button3.addActionListener(digitoListener);
        button4.addActionListener(digitoListener);
        button5.addActionListener(digitoListener);
        button6.addActionListener(digitoListener);
        button7.addActionListener(digitoListener);
        button8.addActionListener(digitoListener);
        button9.addActionListener(digitoListener);

        ActionListener operacaoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelErro.setText("");
                try {
                    if (!operacao.isEmpty() && !aguardandoSegundoNumero) {
                        double segundoNumero = parsearNumero(display.getText());
                        double resultado = calcular(primeiroNumero, segundoNumero, operacao);
                        display.setText(formatarResultado(resultado));
                        primeiroNumero = resultado;
                    } else {
                        primeiroNumero = parsearNumero(display.getText());
                    }

                    operacao = ((JButton) e.getSource()).getText();
                    aguardandoSegundoNumero = true;

                } catch (ExcecoesCalculadora ex) {
                    labelErro.setText(ex.getMessage());
                }
            }
        };

        buttonMais.addActionListener(operacaoListener);
        buttonMenos.addActionListener(operacaoListener);
        buttonVezes.addActionListener(operacaoListener);
        buttonDividido.addActionListener(operacaoListener);

        // LISTENER: igual (=)
        buttonIgual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelErro.setText("");
                if (operacao.isEmpty()) return; // nada a calcular

                try {
                    double segundoNumero = parsearNumero(display.getText());
                    double resultado = calcular(primeiroNumero, segundoNumero, operacao);
                    display.setText(formatarResultado(resultado));
                    operacao = "";
                    aguardandoSegundoNumero = true;

                } catch (ExcecoesCalculadora ex) {
                    labelErro.setText(ex.getMessage());
                    display.setText("Erro");
                    operacao = "";
                    aguardandoSegundoNumero = true;
                }
            }
        });

        // LISTENER: limpar (C)
        buttonC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.setText("0");
                labelErro.setText("");
                primeiroNumero = 0;
                operacao = "";
                aguardandoSegundoNumero = false;
            }
        });

        // Adiciona componentes ao painel
        panel.add(display);
        panel.add(labelErro);
        panel.add(buttonC);
        panel.add(buttonMais);
        panel.add(buttonMenos);
        panel.add(buttonVezes);
        panel.add(buttonDividido);
        panel.add(buttonIgual);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);
        panel.add(button7);
        panel.add(button8);
        panel.add(button9);
        panel.add(button0);

        frame.add(panel);
        frame.setVisible(true);
    }

    // MÉTODO: parsearNumero
    private static double parsearNumero(String texto) throws ExcecoesCalculadora {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new ExcecoesCalculadora("Entrada inválida: \"" + texto + "\" não é um número.");
        }
    }

    // MÉTODO: calcular
    private static double calcular(double a, double b, String op) throws ExcecoesCalculadora {
        switch (op) {
            case "+": 
                return a + b;
            case "-": 
                return a - b;
            case "×":
                return a * b;
            case "÷":
                if (b == 0) {
                    throw new ExcecoesCalculadora("Erro: divisão por zero não é permitida.");
                }
                return a / b;
            default:
                throw new ExcecoesCalculadora("Operação desconhecida: " + op);
        }
    }

    // MÉTODO: formatarResultado
    private static String formatarResultado(double resultado) {
        if (resultado == (long) resultado) {
            return String.valueOf((long) resultado);
        }
        return String.valueOf(resultado);
    }
}