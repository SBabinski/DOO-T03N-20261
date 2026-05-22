public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valorAluguelMensal, int andar) {
        super(endereco, valorAluguelMensal);
        this.andar = andar;
    }

    public int getAndar() { return andar; }

    @Override
    public void exibirInformacoes() {
        System.out.println("Apartamento | Endereço: " + getEndereco()
                + " | Andar: " + andar
                + " | Aluguel: R$ " + getValorAluguelMensal());
    }
}
