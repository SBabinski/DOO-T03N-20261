public class Apartamento extends Imovel {
    int andar;

    public Apartamento(String endereco, double valorAluguel, int andar) {
        super(endereco, valorAluguel);
        this.andar = andar;
    }

    public void exibirInformacoes() {
        System.out.println("Tipo: Apartamento");
        System.out.println("Endereco: " + endereco);
        System.out.println("Valor do aluguel: R$ " + valorAluguel);
        System.out.println("Andar: " + andar);
    }
}
