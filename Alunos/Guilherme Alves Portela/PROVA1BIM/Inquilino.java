public class Inquilino {
    public String nome;
    public String cpf;
    public String telefone;

    public Inquilino(String nome, String cpf, String telefone){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    //Getters

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    //Setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    //Métodos da classe

    public void exibirInfos() {
        String infos = String.format("Nome: %s | CPF: %s | Telefone: %s",
        this.nome, this.cpf, this.telefone);

        System.out.println(infos);

    }    
    
}
