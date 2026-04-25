public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valorAluguelMensal, int andar){
        super(endereco, valorAluguelMensal);
        this.andar = andar;
    }

    @Override
    public void exibirInformacoes() {
        System.out.printf("INFORMAÇÕES APARTAMENTO\n");
        System.out.printf("Endereço: %s\n", getEndereco());
        System.out.printf("Valor Aluguel Mensal: %.2f\n", getValorAluguelMensal());
        System.out.printf("Andar: %d\n", andar);
    }
}
