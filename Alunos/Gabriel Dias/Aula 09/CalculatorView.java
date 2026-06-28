package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class CalculatorView extends JFrame {
    private JTextField display;
    private JPanel buttonPanel;
    private Map<String, JButton> buttons;
    
    public CalculatorView() {
        setupFrame();
        createDisplay();
        createButtons();
        setupKeyboardListener();
    }
    
    private void setupFrame() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 450);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void createDisplay() {
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);
    }
    
    private void createButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "", "", ""
        };
        
        buttons = new HashMap<>();
        for (String label : buttonLabels) {
            if (label.isEmpty()) {
                buttonPanel.add(new JLabel());
                continue;
            }
            
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setFocusPainted(false);
            
            if (label.matches("[0-9]")) {
                button.setBackground(new Color(240, 240, 240));
            } else if (label.equals("=")) {
                button.setBackground(new Color(100, 150, 255));
                button.setForeground(Color.WHITE);
            } else if (label.equals("C")) {
                button.setBackground(new Color(255, 100, 100));
                button.setForeground(Color.WHITE);
            } else if (label.matches("[+\\-*/]")) {
                button.setBackground(new Color(255, 200, 100));
            } else if (label.equals(".")) {
                button.setBackground(new Color(240, 240, 240));
            }
            
            buttons.put(label, button);
            buttonPanel.add(button);
        }
        
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    private void setupKeyboardListener() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                requestFocusInWindow();
            }
        });
    }
    
    private void handleKeyPress(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();
        
        String buttonLabel = null;
        
        if (Character.isDigit(keyChar)) {
            buttonLabel = String.valueOf(keyChar);
        }
        else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {
            buttonLabel = String.valueOf(keyChar);
        }
        else if (keyChar == '.' || keyChar == ',') {
            buttonLabel = ".";
        }
        else if (keyCode == KeyEvent.VK_ENTER || keyChar == '=') {
            buttonLabel = "=";
        }
        else if (keyCode == KeyEvent.VK_ESCAPE || keyChar == 'c' || keyChar == 'C') {
            buttonLabel = "C";
        }
        else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            buttonLabel = "C";
        }
        
        if (buttonLabel != null && buttons.containsKey(buttonLabel)) {
            JButton button = buttons.get(buttonLabel);
            button.doClick();
        }
    }
    
    public JButton getButton(String label) {
        return buttons.get(label);
    }
    
    public void updateDisplay(String text) {
        display.setText(text);
    }
    
    public String getDisplayText() {
        return display.getText();
    }
}
