public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valor, int andar) {
        super(endereco, valor);
        this.andar = andar;
    }

    @Override
    public String exibirDados() {
        return "Apartamento: " + endereco + " | R$" + valorAluguel + " | Andar: " + andar;
    }
}