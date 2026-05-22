public class Cliente extends Pessoa {
    private String email;
    private String telefone;

    public Cliente(String nome, int idade, Endereco endereco, String email, String telefone) {
        super(nome, idade, endereco);
        this.email = email;
        this.telefone = telefone;
    }

    @Override
    public void apresentarse() {
        System.out.println("👤 Cliente: " + nome + ", " + idade + " anos");
        if (endereco != null) {
            endereco.apresentarLogradouro();
        }
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
