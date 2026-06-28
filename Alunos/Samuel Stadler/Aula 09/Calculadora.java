import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculadora extends JFrame implements ActionListener {
    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JTextField campoResultado;
    private String operacaoEscolhida = "";
    private int campoAtivo = 1;
    public Calculadora() {
        setTitle("Calculadora");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel painelEntrada = new JPanel();
        painelEntrada.setLayout(new GridLayout(4, 2, 5, 5));
        painelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelEntrada.add(new JLabel("Número 1:"));
        campoNumero1 = new JTextField();
        campoNumero1.setEditable(false);
        painelEntrada.add(campoNumero1);
        painelEntrada.add(new JLabel("Número 2:"));
        campoNumero2 = new JTextField();
        campoNumero2.setEditable(false);
        painelEntrada.add(campoNumero2);
        painelEntrada.add(new JLabel("Resultado:"));
        campoResultado = new JTextField();
        campoResultado.setEditable(false);
        campoResultado.setBackground(Color.LIGHT_GRAY);
        painelEntrada.add(campoResultado);
        JButton btnCampo1 = new JButton("Digitar no Número 1");
        btnCampo1.addActionListener(e -> {
            campoAtivo = 1;
            campoNumero1.setBackground(Color.YELLOW);
            campoNumero2.setBackground(Color.WHITE);
        });
        JButton btnCampo2 = new JButton("Digitar no Número 2");
        btnCampo2.addActionListener(e -> {
            campoAtivo = 2;
            campoNumero2.setBackground(Color.YELLOW);
            campoNumero1.setBackground(Color.WHITE);
        });
        painelEntrada.add(btnCampo1);
        painelEntrada.add(btnCampo2);
        add(painelEntrada, BorderLayout.NORTH);
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(5, 4, 5, 5));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        String[] numeros = {"7", "8", "9", "+",
                            "4", "5", "6", "-",
                            "1", "2", "3", "×",
                            "0", ".", "C", "÷"};
        for (String texto : numeros) {
            JButton btn = new JButton(texto);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(this);
            if (texto.equals("+") || texto.equals("-") || texto.equals("×") || texto.equals("÷")) {
                btn.setBackground(new Color(100, 149, 237));
                btn.setForeground(Color.WHITE);
            } else if (texto.equals("C")) {
                btn.setBackground(Color.RED);
                btn.setForeground(Color.WHITE);
            }
            painelBotoes.add(btn);
        }
        add(painelBotoes, BorderLayout.CENTER);
        JPanel painelInferior = new JPanel();
        painelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        JButton btnCalcular = new JButton("= Calcular");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 20));
        btnCalcular.setBackground(new Color(34, 139, 34));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setPreferredSize(new Dimension(350, 50));
        btnCalcular.addActionListener(e -> calcular());
        painelInferior.add(btnCalcular);
        add(painelInferior, BorderLayout.SOUTH);
        campoAtivo = 1;
        campoNumero1.setBackground(Color.YELLOW);
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("C")) {
            campoNumero1.setText("");
            campoNumero2.setText("");
            campoResultado.setText("");
            operacaoEscolhida = "";
            return;
        }
        if (comando.equals("+") || comando.equals("-") || comando.equals("×") || comando.equals("÷")) {
            operacaoEscolhida = comando;
            campoResultado.setText("Operação: " + comando);
            return;
        }
        if (campoAtivo == 1) {
            campoNumero1.setText(campoNumero1.getText() + comando);
        } else {
            campoNumero2.setText(campoNumero2.getText() + comando);
        }
    }
    private void calcular() {
        try {
            if (campoNumero1.getText().isEmpty()) {
                throw new CalculadoraException("O Número 1 está vazio! Por favor, informe um valor.");
            }
            if (campoNumero2.getText().isEmpty()) {
                throw new CalculadoraException("O Número 2 está vazio! Por favor, informe um valor.");
            }
            if (operacaoEscolhida.isEmpty()) {
                throw new CalculadoraException("Nenhuma operação foi selecionada! Escolha +, -, × ou ÷.");
            }
            double numero1;
            double numero2;
            try {
                numero1 = Double.parseDouble(campoNumero1.getText());
            } catch (NumberFormatException ex) {
                throw new CalculadoraException("O Número 1 contém um valor inválido: \"" + campoNumero1.getText() + "\"");
            }
            try {
                numero2 = Double.parseDouble(campoNumero2.getText());
            } catch (NumberFormatException ex) {
                throw new CalculadoraException("O Número 2 contém um valor inválido: \"" + campoNumero2.getText() + "\"");
            }
            double resultado;
            if (operacaoEscolhida.equals("+")) {
                resultado = numero1 + numero2;
            } else if (operacaoEscolhida.equals("-")) {
                resultado = numero1 - numero2;
            } else if (operacaoEscolhida.equals("×")) {
                resultado = numero1 * numero2;
            } else if (operacaoEscolhida.equals("÷")) {
                if (numero2 == 0) {
                    throw new CalculadoraException("Erro: Divisão por zero não é permitida!");
                }
                resultado = numero1 / numero2;
            } else {
                throw new CalculadoraException("Operação desconhecida: " + operacaoEscolhida);
            }
            if (resultado == (long) resultado) {
                campoResultado.setText(String.valueOf((long) resultado));
            } else {
                campoResultado.setText(String.valueOf(resultado));
            }
        } catch (CalculadoraException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Calculadora", JOptionPane.ERROR_MESSAGE);
            campoResultado.setText("Erro!");
        }
    }
    public static void main(String[] args) {
        new Calculadora();
    }
}