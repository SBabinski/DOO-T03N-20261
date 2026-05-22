import java.util.Scanner;

public class Vendedor{
  
    private String nome;
    private int idade;
    private String loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private double[] salarioRecebido;

      public Vendedor(String nome, int idade, String loja, String cidade,
                     String bairro, String rua, double[] salarioRecebido, double salarioBase) {

        this.nome = nome;
        this.idade = idade;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;
        this.salarioRecebido = salarioRecebido;
    }

    public void mostrarVendedor() {
       System.out.println("O Nome do vendedor é " + nome + " e tem " + idade + " anos." + " Trabalha na loja " + loja);
    }

    public double calcularMedia() {
        double soma = 0;

        for (double salario : salarioRecebido) {
            soma += salario;
        }

        return soma / salarioRecebido.length;
    }

    public double calcularBonus() {
        return salarioBase * 0.2;
    }
}

