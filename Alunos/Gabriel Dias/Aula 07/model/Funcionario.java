package myplant.model;

/**
 * Classe abstrata intermediária para funcionários (Vendedor e Gerente).
 * Herda de Pessoa e adiciona atributos e comportamentos de funcionário.
 */
public abstract class Funcionario extends Pessoa {

    protected Loja loja;
    protected double salarioBase;
    protected double[] salarioRecebido;

    public Funcionario(String nome, int idade, Endereco endereco, Loja loja,
                       double salarioBase, double[] salarioRecebido) {
        super(nome, idade, endereco);
        this.loja = loja;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public double calcularMedia() {
        if (salarioRecebido == null || salarioRecebido.length == 0) return 0;
        double soma = 0;
        for (double s : salarioRecebido) soma += s;
        return soma / salarioRecebido.length;
    }

    public abstract double calcularBonus();

    // Getters
    public Loja getLoja()            { return loja; }
    public double getSalarioBase()   { return salarioBase; }
    public double[] getSalarioRecebido() { return salarioRecebido; }
}
