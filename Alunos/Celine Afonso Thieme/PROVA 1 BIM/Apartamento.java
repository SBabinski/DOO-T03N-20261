public class Apartamento extends Imovel {

    private int andar;

    public Apartamento(String endereco, double valorAluguel, int andar) {
        super(endereco, valorAluguel);
        this.andar = andar;
    }

    @Override
    public void exibirDados() {
        System.out.println("APARTAMENTO");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor: " + valorAluguel);
        System.out.println("Andar: " + andar);
    }
}