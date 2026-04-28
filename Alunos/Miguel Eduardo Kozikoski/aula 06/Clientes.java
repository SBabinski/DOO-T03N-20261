public class Cliente {
    String nome;
    String cidade;
    String bairro;
    String rua;
    int idade;

    public Cliente(String nome , String cidade , String bairro , String rua , int idade ){
        this.nome = nome;
        this.bairro = bairro;
        this.rua = rua;
        this.cidade = cidade;
        this.idade = idade;
    }

    public void aprensetaseClinte(){
        System.out.println("O cliente : " + nome + " " + "com idade : " + idade);
    }
}
