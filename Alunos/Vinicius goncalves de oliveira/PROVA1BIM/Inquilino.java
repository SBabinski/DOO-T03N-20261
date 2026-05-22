public class Inquilino {
    String nome;
    String cpf;
    String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    //nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //cpf
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    //telefone
    public String gettelefone() {
        return telefone;
    }

    public void settelefone(String telefone) {
        this.telefone = telefone;
    }

    public void exibirInquilino() {
        System.out.println("Nome: " + nome
        + ", CPF: " + cpf
        + ", telefone: " + telefone
        );
    }
}
