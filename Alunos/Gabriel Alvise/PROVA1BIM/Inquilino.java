public class Inquilino {
    private String nome;
    private String cpf;
    private String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public void exibirInformacoes() {
        System.out.printf("Nome: %s\n", nome);
        System.out.printf("CPF: %s\n", cpf);
        System.out.printf("Telefone: %s\n", telefone);
    }

    public String getNome() {
        return nome;
    }
}