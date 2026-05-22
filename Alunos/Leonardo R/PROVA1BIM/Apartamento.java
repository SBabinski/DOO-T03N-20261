public class Apartamento extends Imovel {

    private int andar;

    public Apartamento(String endereco, double valorAluguel, int andar) {
        super(endereco, valorAluguel);
        this.andar = andar;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    @Override
    public void exibirImovel() {
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Valor do Aluguel: " + getValorAluguel());
        System.out.println("Andar: " + andar);
    }
}