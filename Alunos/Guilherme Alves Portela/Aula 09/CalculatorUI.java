import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe responsável pela interface gráfica da calculadora usando Swing.
 * Apenas desenha a tela e captura as ações do usuário.
 * Toda a lógica de cálculo e validação fica na classe CalculatorService, seguindo o princípio de responsabilidade única (SRP).
 * 
 * Por: Guilherme Alves Portela
 */

public class CalculatorUI extends JFrame {
    private final JTextField display;
    private final CalculatorService service;
    
    private double firstNumber = 0;
    private String currentOperation = "";
    private boolean isOperatorClicked = false;

    public CalculatorUI() {
        // Instancia o motor de regras de negócio
        service = new CalculatorService();

        // Configurações básicas da janela 
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);
        setLocationRelativeTo(null); // Centraliza a janela na tela ao iniciar
        setLayout(new BorderLayout(10, 10));

        // Tela de exibição (permite digitar direto por teclado para testar erros de texto)
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 26));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Grade para organizar os botões igualmente
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 6, 6));

        // Desenho do teclado numérico e de funções
        String[] buttons = {
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        // Cria os botões e adiciona a escuta de cliques em cada um
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Ouvinte que intercepta o clique de qualquer botão ou ação na calculadora.
     */
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                // 1. Se clicou em um número (0-9)
                if (command.matches("[0-9]")) {
                    if (isOperatorClicked) {
                        display.setText("");
                        isOperatorClicked = false;
                    }
                    display.setText(display.getText() + command);
                    
                // 2. Se clicou no botão 'C' para limpar a tela
                } else if (command.equals("C")) {
                    display.setText("");
                    firstNumber = 0;
                    currentOperation = "";
                    
                // 3. Se clicou em uma operação (+, -, ×, ÷)
                } else if (command.matches("[+\\-×÷]")) {
                    firstNumber = service.parseInput(display.getText());
                    currentOperation = command;
                    isOperatorClicked = true;
                    
                // 4. Se clicou no botão '=' para ver o resultado
                } else if (command.equals("=")) {
                    if (!currentOperation.isEmpty()) {
                        double secondNumber = service.parseInput(display.getText());
                        
                        // Faz o cálculo real chamando o serviço
                        double result = service.compute(currentOperation, firstNumber, secondNumber);
                        
                        // Formata o resultado visual para tirar o ".0" caso seja número inteiro
                        if (result % 1 == 0) {
                            display.setText(String.valueOf((long) result));
                        } else {
                            display.setText(String.valueOf(result));
                        }
                        currentOperation = "";
                    }
                }
            } catch (CalculatorException ex) {
                // TRATAMENTO DE EXCEÇÃO CENTRALIZADO:
                // Captura qualquer erro de negócio gerado e abre uma janelinha de alerta (Pop-up)
                JOptionPane.showMessageDialog(
                        CalculatorUI.this, 
                        ex.getMessage(), 
                        "Atenção", 
                        JOptionPane.ERROR_MESSAGE
                );
                display.setText(""); // Reseta a tela para o usuário tentar novamente
            }
        }
    }
}