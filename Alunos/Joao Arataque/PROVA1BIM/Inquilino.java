public class Inquilino {
    String nome;
    String cpf;
    String telefone;

    public Inquilino(String nome, String cpf, String telefone){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    @Override
    public String toString(){
        return "Nome: " + nome + " | CPF: " + cpf;
    }
}