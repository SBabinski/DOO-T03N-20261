public class Inquilino {

    private String nome;
    private String cpf;
    private String telefone;

    public Inquilino() {
    }

    // Construtor completo
    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Método para exibir informações do inquilino
    public void mostrarInquilino() {
        System.out.printf("Inquilino | Nome: %s | CPF: %s | Telefone: %s%n",
                getNome(), getCpf(), getTelefone());
    }

}
