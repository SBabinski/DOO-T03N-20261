class Inquilino {

    String nome;
    String cpf;
    String telefone;

    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
    }

    void ExibirDados() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void ExibirDados() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}