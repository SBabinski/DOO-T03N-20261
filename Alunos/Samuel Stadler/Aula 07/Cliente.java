public class Cliente extends Pessoa {
    private String cpf;
    private String telefone;
    public Cliente(String nome, int idade, Endereco endereco, String cpf, String telefone) {
        super(nome, idade, endereco);
        this.cpf = cpf;
        this.telefone = telefone;
    }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    @Override
    public void apresentarse() {
        super.apresentarse();
        System.out.println("CPF: " + cpf);
        System.out.println("Telefone: " + telefone);
        getEndereco().apresentarLogradouro();
    }
}
