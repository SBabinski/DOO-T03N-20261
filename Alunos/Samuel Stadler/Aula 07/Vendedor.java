public class Vendedor extends Pessoa {
    private String matricula;
    private String loja;
    public Vendedor(String nome, int idade, Endereco endereco, String matricula, String loja) {
        super(nome, idade, endereco);
        this.matricula = matricula;
        this.loja = loja;
    }
    public String getMatricula() { return matricula; }
    public String getLoja() { return loja; }
    @Override
    public void apresentarse() {
        super.apresentarse();
        System.out.println("Matricula: " + matricula);
        System.out.println("Loja: " + loja);
        getEndereco().apresentarLogradouro();
    }
}
