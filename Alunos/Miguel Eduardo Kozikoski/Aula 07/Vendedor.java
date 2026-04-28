import java.util.ArrayList;

public class Vendedor {
    //atributos
    String nome;
    String cidade;
    String bairro;
    String rua;
    int idade;
    ArrayList<Double> salarioBase;
    ArrayList<Double> salarioRecebido;
    Endereco endereco;

    //Construdor
    public Vendedor (String nome , String cidade , String bairro , String rua , int idade , Endereco endereco){
        this.nome = nome;
        this.bairro = bairro;
        this.cidade  = cidade;
        this.rua = rua;
        this.idade = idade;
        salarioBase = new ArrayList<>();
        salarioRecebido = new ArrayList<>();
        this.endereco = endereco;

        //valores fixo do salario ( minimo 3 )
        salarioRecebido.add(1000.0);
        salarioRecebido.add(1500.0);
        salarioRecebido.add(2000.0);

        // valores fixo do salario base
        salarioBase.add(1000.0);
        salarioBase.add(1500.0);
        salarioBase.add(2000.00);


    }

    //apresentação do vendedor
    public void Apresentase(){
        System.out.println("Nome:" + nome + " " + "Idade:" + idade );
        endereco.apresentaEndereco();
    }

    //Calculo da média dos salarios
    public double mediaSalrio(){
        double soma = 0;
        for (double salario : salarioRecebido) {
            soma += salario; // soma de todos os salarios add = 4500
        }
        return soma / salarioRecebido.size(); // size quantidade de vezes , que foi igual a 3
    }

    //Calculo da média do Bonus recebido
    public double calculoBonus(){
        double soma = 0;
        for (double salario : salarioBase) {
            soma += salario;// soma de todos os salarios add = 4500
        }
        double media = soma / salarioRecebido.size(); // a soma dividida por a quantidade de vezes
        return media * 0.2; // o resultado da media vezes o 0.2 que sai o resultado
    }



}
