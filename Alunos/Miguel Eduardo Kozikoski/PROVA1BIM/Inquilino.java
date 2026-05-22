public class Inquilino {
    String nome;
    float cpf;
    float telefone;

    public Inquilino(String nome , float cpf , float telefone){
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
    }

    public void Apresentacao(){
        System.out.println(" nosso mais novo inquilino : " + nome );
    }
    public String getNome() {
        return this.nome;
    }
}
