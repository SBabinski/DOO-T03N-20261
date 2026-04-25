public class Casa extends Imovel {
    private boolean quintal;

    public Casa(String endereco, double valorMensal, boolean quintal) {
        super(endereco, valorMensal);
        this.quintal = quintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Casa:");
        System.out.println("Endereço: " + endereco);
        System.out.println("Valor mensal: " + valorMensal);
        System.out.println("Possui quintal: " + (quintal ? "Sim" : "Não"));
    }
}
