package objetos;

import java.util.List;

public class Vendedor {

    private String nome;
    private int idade;
    private Loja loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private List<Double> salarioRecebido;

    public Vendedor(String nome, int idade, Loja loja, String cidade, String bairro, String rua, double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
    
        this.salarioRecebido = new java.util.ArrayList<>(List.of(salarioBase, salarioBase, salarioBase));
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public Loja getLoja() { return loja; }
    public void setLoja(Loja loja) { this.loja = loja; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

    public List<Double> getSalarioRecebido() { return salarioRecebido; }

    public void adicionarSalario(double salario) {
        salarioRecebido.add(salario);
    }

    public void apresentarse() {
        System.out.println("Nome: " + nome
                + " | Idade: " + idade
                + " | Loja: " + loja.getNomeFantasia());
    }

    public double calcularMedia() {
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario;
        }
        double media = soma / salarioRecebido.size();
        System.out.println("Média salarial de " + nome + ": R$ " + media);
        return media;
    }

    public double calcularBonus() {
        double bonus = salarioBase * 0.2;
        System.out.println("Bônus de " + nome + ": R$ " + bonus);
        return bonus;
    }
}