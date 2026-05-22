package view;

import service.CalculatorService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CalculatorInterface extends JFrame{
    
    private JTextField display;
    private CalculatorService calculatorService;
    
    public CalculatorInterface() {
        
        /// CONFIGURE FRAME
        // Defines the frame name
        setTitle("Calculadora do NUNES");
        
        // Defines the frame size
        setSize(300, 400);
        
        // Defines the program termination when a frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Blocks frame resizing
        setResizable(false);
        
        // Defines the position where the frame is generated
        setLocationRelativeTo(null);
        
        // Defines the frame layout
        setLayout(new BorderLayout());
        
        /// CREATE DISPLAY
        
        // Creates the frame display
        display = new JTextField();
        
        // Defines the display size
        display.setPreferredSize(new Dimension(0, 80));
        
        // Defines the display font
        display.setFont(new Font("Arial", Font.BOLD, 30));
        
        // Defines the display text alignment
        display.setHorizontalAlignment(JTextField.RIGHT);
        
        // Display is not editable, only shows values
        display.setEditable(false);
        
        // Defines the display background color
        display.setBackground(Color.BLACK);
        
        // Defines the display text color
        display.setForeground(Color.GREEN);
        
        // Defines the display border
        display.setBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5)
        );
        
        // Adds the display to the top
        add(display, BorderLayout.NORTH);
        
        
        /// CREATE PANEL
        
        // Creates the buttons panel
        JPanel panel = new JPanel();
        
        // Defines the panel layout
        panel.setLayout(new GridLayout(5,4,5,5));
        // Defines the panel margins
        panel.setBorder(
                BorderFactory.createEmptyBorder(5,5,5,5)
        );
        
        // Defines the panel background color
        panel.setBackground(Color.DARK_GRAY);
        
        
        /// CREATE BUTTONS
        
        // Adds the buttons
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                ".", "0", "=", "+",
                "C"
        };
        
        // Creates and adds each button to the panel
        for (String text: buttons) {
            panel.add(buildButton(text));
        }
        
        // Adds the panel to the center
        add(panel, BorderLayout.CENTER);
        
        
        this.calculatorService = new CalculatorService();
        
        /// MAKES THE FRAME VISIBLE
        
        // Makes the frame visible
        setVisible(true);
        
    }
    
    private JButton buildButton(String character) {
        
        // Creates the button
        JButton button = new JButton(character);
        
        // Defines the button font
        button.setFont(new Font("Arial", Font.BOLD, 30));
        
        // Defines the button text color
        button.setForeground(new Color(245, 245, 245));
        
        // Defines the button background color
        button.setBackground(new Color(10, 10, 10));
        
        // Removes the focus border
        button.setFocusPainted(false);
        
        // Enables the button border
        button.setBorderPainted(true);
        
        // Defines the button border color and thickness
        button.setBorder(new LineBorder(Color.GRAY, 2));
        
        /// CAPTURE BUTTON CLICK
        
        button.addActionListener(e -> {
            
            String currentText = display.getText();
            
            // IF "C" BUTTON
            if (character.equals("C")) {
                
                display.setText("");
                return;
                
            }
            
            // DON'T TOLERATE TWO "." CONSECUTI
            if (character.equals(".") && currentText.endsWith(".")) {
                return;
            }
            
            // IF "=" BUTTON
            else if (character.equals("=")) {
                
                try {
                    
                    String expression = display.getText();
                    
                    double result = calculatorService.calculate(expression);
                    
                    display.setText(String.valueOf(result));
                    
                } catch (Exception ex) {
                    
                    JOptionPane.showMessageDialog(
                            null,
                            ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            
            // IF OPERATOR
            else if (
                    character.equals("+") ||
                            character.equals("-") ||
                            character.equals("*") ||
                            character.equals("/")
            ) {
                
                // prevents two operators together
                if (
                        currentText.endsWith("+") ||
                                currentText.endsWith("-") ||
                                currentText.endsWith("*") ||
                                currentText.endsWith("/")
                ) {
                    return;
                }
                
                display.setText(currentText + character);
            }
            
            // IF NUMBER
            else {
                
                display.setText(currentText + character);
                
            }
            
        });
        
        // Returns the configured button
        return button;
    }
}