import java.util.ArrayList;

public class Vendedor {
    String nome;
    int idade;
    String cidade;
    String loja;
    String bairro;
    String rua;
    double salarioBase;
    ArrayList<Double> salarioRecebido = new ArrayList<>();

    public Vendedor(String nome, int idade, String cidade, String loja, String bairro, String rua, Double salarioBase) {
        this.nome = nome;
        this.idade = idade;
        this.cidade = cidade;
        this.loja = loja;
        this.bairro = bairro;
        this.rua = rua;
        this.salarioBase = salarioBase;


        salarioRecebido.add(1600.00);
        salarioRecebido.add(1700.00);
        salarioRecebido.add(1800.00);
   
       
    }
    public void apresentarSe(){
        System.out.println("Nome: " + nome );
        System.out.println("idade: " + idade);
        System.out.println("Loja: " + loja);
        System.out.println("Salário Base: R$ " + salarioBase);


    }
    public double calcularMedia(){
        double soma = 0;
        for (Double salario : salarioRecebido) {
            soma += salario;
        }
        return soma / salarioRecebido.size();
    }
    public double calcularBonus(){
        return salarioBase * 0.2;
    }

}
