package calculator;

import javax.swing.SwingUtilities;

public class CalculatorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator model = new Calculator();
            CalculatorView view = new CalculatorView();
            CalculatorController controller = new CalculatorController(model, view);
            
            view.setVisible(true);
        });
    }
}
