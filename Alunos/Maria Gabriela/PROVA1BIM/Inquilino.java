public class Inquilino {
    private String nome;
    private String cpf;
    private String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        if(nome.isEmpty() || cpf.isEmpty()) {
            throw new IllegalArgumentException("Nome e CPF são obrigatórios!");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String toString() {
        return "Nome: " + nome + " | CPF: " + cpf + " | Telefone: " + telefone;
    }
}