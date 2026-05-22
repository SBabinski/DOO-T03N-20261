package Entities;

import java.util.ArrayList;

public class Vendedor {
    private static int contId = 1;
    private int id;
    private String nome;
    private int idade;
    private Loja loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private ArrayList<Double> salarioRecebido;
    
    public Vendedor(String nome, int idade, Loja loja, String cidade,
                    String bairro, String rua, double salarioBase) {
        this.id = contId++; // Gera id
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>();
    }
    
    // Adiciona salarios lista de salários recebidos
    public void depositaSalario(double salario) {
        salarioRecebido.add(salario);
    }
    
    // Printa o nome, idade e Loja
    public String apresentarse(){
        return String.format("\n" + id + "  |  " + nome + "  |  " +
                idade + " anos  |  " + loja);
    }
    
    // Retorna a média dos salarios recebidos
    public double calcularMedia() {
        double montante = 0;
        for (Double m : salarioRecebido){
            montante += m;
        }
        
        return montante / salarioRecebido.size();
    }
    
    // Retorna o valor do bónus
    public double calcularBonus() {
        return salarioBase * 0.2;
    }
    
    @Override
    public String toString() {
        return String.format("  %-3d   %-10s   %-10s   %-10s   %-10s   %-10s  %-10s",
                id, nome, idade, loja, cidade, bairro, rua);
    }
    
}
