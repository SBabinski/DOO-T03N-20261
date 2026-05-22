package Objetos;

import java.util.ArrayList;
import java.util.List;

public abstract class Funcionario extends Pessoa {

    private Loja loja;
    private double salarioBase;
    private List<Double> salarioRecebido;

    public Funcionario(String nome, int idade, Endereco endereco, Loja loja, double salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = new ArrayList<>(List.of(salarioBase, salarioBase, salarioBase));
    }

    public Loja getLoja() { return loja; }
    public void setLoja(Loja loja) { this.loja = loja; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public List<Double> getSalarioRecebido() { return salarioRecebido; }

    public void adicionarSalario(double salario) {
        salarioRecebido.add(salario);
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        double media = soma / salarioRecebido.size();
        System.out.println("Média salarial de " + getNome() + ": R$ " + media);
        return media;
    }

    public abstract double calcularBonus();
}
