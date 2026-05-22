package fag;

import java.util.ArrayList;

public abstract class Funcionario extends Pessoa {
    protected Loja loja;
    protected double salarioBase;
    protected ArrayList<Double> salarioRecebido;

    public Funcionario(String nome, int idade, Endereco endereco,
                       Loja loja, double salarioBase) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;

        salarioRecebido = new ArrayList<>();
        salarioRecebido.add(salarioBase);
        salarioRecebido.add(salarioBase + 200);
        salarioRecebido.add(salarioBase + 300);
    }

    public double calcularMedia() {
        double soma = 0;
        for (Double s : salarioRecebido) soma += s;
        return soma / salarioRecebido.size();
    }

    public abstract double calcularBonus();
}