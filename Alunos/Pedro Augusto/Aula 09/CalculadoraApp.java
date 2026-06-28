import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// criação da exception personalizada
class CalculadoraException extends Exception {
    public CalculadoraException(String mensagem) {
        super(mensagem);
    }
}

// classe Principal da Interface Gráfica
public class CalculadoraApp extends JFrame implements ActionListener {
    
    private JTextField display;
    private double num1 = 0, num2 = 0;
    private String operador = "";
    private boolean isNovoNumero = true;

    // paleta de Cores (Estilo Dark Mode Moderno)
    private final Color COR_FUNDO = new Color(30, 30, 30);
    private final Color COR_DISPLAY = new Color(45, 45, 45);
    private final Color COR_BOTAO_NUM = new Color(60, 60, 60);
    private final Color COR_BOTAO_OP = new Color(255, 153, 0); // Laranja estilo iOS
    private final Color COR_BOTAO_LIMPAR = new Color(165, 165, 165);
    private final Color COR_TEXTO_BRANCO = Color.WHITE;
    private final Color COR_TEXTO_PRETO = Color.BLACK;

    public CalculadoraApp() {
        // Configurações da Janela Principal
        setTitle("Calculadora Swing");
        setSize(320, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COR_FUNDO);
        
        // margens internas (Padding)
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // inicializar Componentes
        inicializarDisplay();
        inicializarBotoes();
    }

    private void inicializarDisplay() {
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 48));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(COR_DISPLAY);
        display.setForeground(COR_TEXTO_BRANCO);
        display.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(display, BorderLayout.NORTH);
    }

    private void inicializarBotoes() {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(4, 4, 10, 10));
        painelBotoes.setBackground(COR_FUNDO);

        String[] textosBotoes = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        for (String texto : textosBotoes) {
            JButton botao = criarBotao(texto);
            painelBotoes.add(botao);
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 24));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botao.addActionListener(this);

        // estilizar botões baseado no seu tipo
        if (texto.matches("[0-9]")) {
            botao.setBackground(COR_BOTAO_NUM);
            botao.setForeground(COR_TEXTO_BRANCO);
        } else if (texto.equals("C")) {
            botao.setBackground(COR_BOTAO_LIMPAR);
            botao.setForeground(COR_TEXTO_PRETO);
        } else {
            botao.setBackground(COR_BOTAO_OP);
            botao.setForeground(COR_TEXTO_BRANCO);
        }

        return botao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            // se for número
            if (comando.matches("[0-9]")) {
                if (isNovoNumero) {
                    display.setText(comando);
                    isNovoNumero = false;
                } else {
                    display.setText(display.getText() + comando);
                }
            } 
            // se for botão de limpar
            else if (comando.equals("C")) {
                display.setText("0");
                num1 = 0;
                num2 = 0;
                operador = "";
                isNovoNumero = true;
            } 
            // se for botão de igual
            else if (comando.equals("=")) {
                calcular();
                operador = ""; // reseta operador após calcular
            } 
            // se for operador (+, -, *, /)
            else {
                // guarda o primeiro número e o operador
                if (!display.getText().isEmpty()) {
                    validarEntradaNumerica(display.getText()); // Validação de segurança
                    num1 = Double.parseDouble(display.getText());
                    operador = comando;
                    isNovoNumero = true;
                }
            }
        } catch (CalculadoraException ex) {
            // captura da exception personalizada e exibição amigável
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro na Calculadora", JOptionPane.ERROR_MESSAGE);
            display.setText("Erro");
            isNovoNumero = true;
        }
    }

    // lógica das operações com tratamento de exception
    private void calcular() throws CalculadoraException {
        if (operador.isEmpty()) return;

        validarEntradaNumerica(display.getText());
        num2 = Double.parseDouble(display.getText());
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "*":
                resultado = num1 * num2;
                break;
            case "/":
                // validação de divisão por zero
                if (num2 == 0) {
                    throw new CalculadoraException("Não é possível realizar divisão por zero.");
                }
                resultado = num1 / num2;
                break;
        }

        // formatação do resultado: remove ".0" se for número inteiro
        if (resultado % 1 == 0) {
            display.setText(String.format("%.0f", resultado));
        } else {
            display.setText(String.valueOf(resultado));
        }
        
        isNovoNumero = true;
    }

    // validação de entrada para caracteres não numéricos
    private void validarEntradaNumerica(String valor) throws CalculadoraException {
        try {
            Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            throw new CalculadoraException("Entrada inválida detectada. Por favor, insira apenas números.");
        }
    }

    // método Main para rodar o app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraApp calc = new CalculadoraApp();
            calc.setVisible(true);
        });
    }
}