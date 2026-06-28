package calculator;

public class Calculator {
    private String currentDisplay;
    private double storedValue;
    private String pendingOperation;
    private boolean startNewNumber;
    
    public Calculator() {
        clear();
    }
    
    public void inputNumber(String digit) {
        if (startNewNumber) {
            currentDisplay = digit;
            startNewNumber = false;
        } else {
            currentDisplay += digit;
        }
    }
    
    public void inputDecimal() {
        if (startNewNumber) {
            currentDisplay = "0.";
            startNewNumber = false;
        } else if (!currentDisplay.contains(".")) {
            currentDisplay += ".";
        }
    }
    
    public void setOperation(String operation) {
        if (!pendingOperation.isEmpty()) {
            calculate();
        }
        storedValue = Double.parseDouble(currentDisplay);
        pendingOperation = operation;
        startNewNumber = true;
    }
    
    public double calculate() {
        double currentValue = Double.parseDouble(currentDisplay);
        double result = currentValue;
        
        switch (pendingOperation) {
            case "+":
                result = storedValue + currentValue;
                break;
            case "-":
                result = storedValue - currentValue;
                break;
            case "*":
                result = storedValue * currentValue;
                break;
            case "/":
                if (currentValue != 0) {
                    result = storedValue / currentValue;
                } else {
                    currentDisplay = "Error";
                    pendingOperation = "";
                    startNewNumber = true;
                    return 0;
                }
                break;
        }
        
        if (result == (long) result) {
            currentDisplay = String.format("%d", (long) result);
        } else {
            currentDisplay = String.format("%s", result);
        }
        
        pendingOperation = "";
        startNewNumber = true;
        
        return result;
    }
    
    public void clear() {
        currentDisplay = "0";
        storedValue = 0;
        pendingOperation = "";
        startNewNumber = true;
    }
    
    public String getCurrentDisplay() {
        return currentDisplay;
    }
}
