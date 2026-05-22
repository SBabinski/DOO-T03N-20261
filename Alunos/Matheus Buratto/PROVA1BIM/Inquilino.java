public class Inquilino {
    private String nome;
    private String cpf;
    private String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }

    @Override
    public String toString() {
        return "Inquilino: " + nome + " | CPF: " + cpf + " | Tel: " + telefone;
    }
}
