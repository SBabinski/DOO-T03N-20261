package calculator;

import javax.swing.JButton;

public class CalculatorController {
    private Calculator model;
    private CalculatorView view;
    
    public CalculatorController(Calculator model, CalculatorView view) {
        this.model = model;
        this.view = view;
        
        initializeListeners();
    }
    
    private void initializeListeners() {
        for (int i = 0; i <= 9; i++) {
            String number = String.valueOf(i);
            JButton button = view.getButton(number);
            if (button != null) {
                button.addActionListener(e -> {
                    model.inputNumber(number);
                    view.updateDisplay(model.getCurrentDisplay());
                });
            }
        }
        
        JButton decimalButton = view.getButton(".");
        if (decimalButton != null) {
            decimalButton.addActionListener(e -> {
                model.inputDecimal();
                view.updateDisplay(model.getCurrentDisplay());
            });
        }
        
        String[] operations = {"+", "-", "*", "/"};
        for (String op : operations) {
            JButton button = view.getButton(op);
            if (button != null) {
                button.addActionListener(e -> {
                    model.setOperation(op);
                    view.updateDisplay(model.getCurrentDisplay());
                });
            }
        }
        
        JButton equalsButton = view.getButton("=");
        if (equalsButton != null) {
            equalsButton.addActionListener(e -> {
                model.calculate();
                view.updateDisplay(model.getCurrentDisplay());
            });
        }
        
        JButton clearButton = view.getButton("C");
        if (clearButton != null) {
            clearButton.addActionListener(e -> {
                model.clear();
                view.updateDisplay(model.getCurrentDisplay());
            });
        }
    }
}
