public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valorAluguelMensal, int andar) {
        super(endereco, valorAluguelMensal);
        this.andar = andar;
    }

    public int getAndar() { return andar; }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Apartamento");
        System.out.println("Endereco: " + getEndereco());
        System.out.println("Valor mensal: R$ " + getValorAluguelMensal());
        System.out.println("Andar: " + andar);
    }
}