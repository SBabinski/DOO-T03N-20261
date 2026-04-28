public class Cliente {
    String nome;
    String cidade;
    String bairro;
    String rua;
    int idade;
    Endereco endereco;

    public Cliente(String nome , String cidade , String bairro , String rua , int idade , Endereco endereco ){
        this.nome = nome;
        this.bairro = bairro;
        this.rua = rua;
        this.cidade = cidade;
        this.idade = idade;
        this.endereco =endereco;
    }

    public void aprensetaseClinte(){
        System.out.println("O cliente : " + nome + " " + "com idade : " + idade);
        endereco.apresentaEndereco();
    }
}
