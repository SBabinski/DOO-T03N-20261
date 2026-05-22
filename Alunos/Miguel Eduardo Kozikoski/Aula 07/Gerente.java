import java.util.ArrayList;

public class Gerente {
    String nome;
    String loja;
    String cidade;
    String bairro;
    String rua;
    int idade;
    ArrayList<Double> salarioBase; // o D em double siginifica que ele está sendo chamado pelo Classe Wrapper (Objeto) - null /  não uma class primitiva double - 0.0
    ArrayList<Double> salarioRecebido;
    Endereco endereco;

    public Gerente(String nome , String loja , String cidade , String bairro , String rua , int idade , Endereco endereco ){
        this.nome = nome;
        this.loja = loja;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.idade = idade;
        salarioRecebido = new ArrayList<>();
        salarioBase = new ArrayList<>();
        this.endereco = endereco;

        salarioRecebido.add(3000.0);
        salarioRecebido.add(3300.0);
        salarioRecebido.add(4000.0);

        salarioBase.add(2900.0);
        salarioBase.add(2900.0);
        salarioBase.add(2900.0);
    }

    public void apresentacaoGerente(){
        System.out.println("Meu nome é " + nome + " " + "sou Gerente da " + loja + " " +  " e tenho " + idade);
        endereco.apresentaEndereco();
    }

    public double mediaSalario(){
        double soma = 0;
        for (double salario : salarioRecebido){
            soma += salario;
        }
        return soma / salarioRecebido.size();
    }

    public double calculoBonus(){
        double soma = 0;
        for (double salario : salarioBase){
            soma += salario;
        }
        double media = soma / salarioBase.size();
        return media * 0.35;
    }
}
