package AULA06;

public class Cliente {
    public String nome;
    public int idade;
    public String cidade;
    public String bairro;
    public String rua;

    public void apresentarse() {
        System.out.println("Cliente: " + nome + " | Idade: " + idade);
    }
}
