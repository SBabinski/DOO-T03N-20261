public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valorMensal, int andar) {
        super(endereco, valorMensal);
        this.andar = andar;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Apartamento:");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor mensal: " + valorMensal);
        System.out.println("Andar: " + andar);
    }
}
