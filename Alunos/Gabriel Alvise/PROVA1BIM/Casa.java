public class Casa extends Imovel{
    private boolean quintal;

    public Casa(String endereco, double valorAluguelMensal, boolean quintal) {
        super(endereco, valorAluguelMensal);
        this.quintal = quintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.printf("INFORMAÇÕES CASA\n");
        System.out.printf("Endereço: %s\n", getEndereco());
        System.out.printf("Valor Aluguel Mensal: %.2f\n", getValorAluguelMensal());
        System.out.printf("Possui quintal? %b\n", quintal);
    }
}
