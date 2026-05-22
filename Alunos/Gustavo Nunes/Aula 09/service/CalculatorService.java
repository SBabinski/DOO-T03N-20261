package service;

import exceptions.InvalidExpressionException;

public class CalculatorService {
    
    public double calculate(String expression) {
        
        if (expression == null || expression.isEmpty()) {
            throw new InvalidExpressionException(
                    "Expressão vazia."
            );
        }
        
        double result = 0;
        
        if (expression.contains("+")) {
            result = sum(expression);
        }
        else if (expression.contains("-")) {
            result = subtraction(expression);
        }
        else if (expression.contains("*")) {
            result = multiply(expression);
        }
        else if (expression.contains("/")) {
            result = division(expression);
        }
        
        return result;
    }
    
    private double sum(String expression) {
        
        String[] parts = expression.split("\\+");
        
        validate(parts);
        
        double n1 = Double.parseDouble(parts[0]);
        double n2 = Double.parseDouble(parts[1]);
        
        return n1 + n2;
    }
    private double subtraction(String expression) {
        
        String[] parts = expression.split("-");
        
        validate(parts);
        
        double n1 = Double.parseDouble(parts[0]);
        double n2 = Double.parseDouble(parts[1]);
        
        return n1 - n2;
    }
    
    private double multiply(String expression) {
        
        String[] parts = expression.split("\\*");
        
        validate(parts);
        
        double n1 = Double.parseDouble(parts[0]);
        double n2 = Double.parseDouble(parts[1]);
        
        return n1 * n2;
    }
    
    private double division(String expression) {
        
        String[] parts = expression.split("/");
        
        validate(parts);
        
        double n1 = Double.parseDouble(parts[0]);
        double n2 = Double.parseDouble(parts[1]);
        
        if (n2 == 0) {
            throw new ArithmeticException(
                    "Não é possível dividir por zero."
            );
        }
        
        return n1 / n2;
    }
    
    private void validate(String[] parts) {
        
        if (parts.length != 2) {
            throw new InvalidExpressionException(
                    "Expressão inválida."
            );
        }
        
        if (parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new InvalidExpressionException(
                    "Complete a operação."
            );
        }
        
        try {
            
            Double.parseDouble(parts[0]);
            Double.parseDouble(parts[1]);
            
        } catch (NumberFormatException e) {
            
            throw new InvalidExpressionException(
                    "Digite apenas números."
            );
        }
    }
    
}
