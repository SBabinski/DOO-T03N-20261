package entities;

public class Inquilino {
    
    private String nome;
    private String cpf;
    private String telefone;
    
    public Inquilino(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Nome: %s | CPF: %s | Telefone: %s",
                nome, cpf, telefone
        );
    }
}
