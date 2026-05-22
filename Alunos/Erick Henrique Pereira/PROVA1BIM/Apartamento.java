public non-sealed class Apartamento extends Imovel{

    private int andar;

    public Apartamento(int andar, Endereco endereco, double valorAluguel) {
        super(endereco, valorAluguel);
        this.andar = andar;
    }
    public Apartamento(){}

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public String mostrarInformacoes(){
        return "Apartamento: " + this.getEndereco().apresentarLogradouro() + ", Andar: " + this.andar + ", Valor do Aluguel: " + this.getValorAluguel();
    }

}
