public class Casa extends Imovel {
    private boolean quintal;

    public Casa(String endereco, double aluguelMensal, boolean quintal) {
        super(endereco, aluguelMensal);
        this.quintal = quintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereço: " + endereco);
        System.out.println("Aluguel: R$ " + aluguelMensal);
        System.out.println("Possui quintal: " + (quintal ? "Sim" : "Não"));
    }
}