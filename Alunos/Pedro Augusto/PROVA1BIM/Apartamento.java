public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valorAluguel, int andar) {
        super(endereco, valorAluguel); // chama construtor da classe pai
        this.setAndar(andar);
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Apartamento");
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Aluguel: R$ " + getValorAluguel());
        System.out.println("Andar: " + andar + "º");
        System.out.println("---------------------------");
    }
}
