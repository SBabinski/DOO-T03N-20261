package entities;

import java.util.ArrayList;

public abstract class Funcionario {
    protected static int contId = 1;
    protected int id;
    protected String nome;
    protected int idade;
    protected Loja loja;
    protected Endereco endereco;
    protected double salarioBase;
    protected ArrayList<Double> salarioRecebido;
    
    // Adiciona salario à lista de salários recebidos
    public void setSalario(double salario) {
        salarioRecebido.add(salario);
    }
    
    // Retorna nome, idade e loja do funcionário
    public String apresentarse() {
        return String.format("\n" + id + "  |  " + nome + "  |  " +
                idade + " anos  |  " + loja);
    }
    
    // Retorna a média dos salários recebidos
    public double calcularMedia() {
        double montante = 0;
        for (Double m : salarioRecebido) {
            montante += m;
        }
        return montante / salarioRecebido.size();
    }
    
    // Retorna o valor do bônus (sobrescrito nas subclasses)
    public abstract double calcularBonus();
}
