public class Cliente extends Pessoa {
    private Endereco endereco;

    public Cliente(String nome, int idade, Endereco endereco) {
        super(nome, idade);
        this.endereco = endereco;
    }

    @Override
    public void apresentarSe() {
        System.out.println("Cliente: " + nome);
        System.out.println("   Idade: " + idade);
        endereco.apresentarLogradouro();
        System.out.println("-------------------");
    }

    public Endereco getEndereco() { return endereco; }