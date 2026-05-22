class Apartamento extends Imovel {

    int andar;

    public Apartamento(String endereco,
                       double aluguelMensal,
                       int andar) {

        super(endereco, aluguelMensal);
        this.andar = andar;
    }

    @Override
    public void exibirInformacoes() {

        System.out.println("Tipo: Apartamento");
        System.out.println("Endereço: " + endereco);
        System.out.println("Aluguel: R$ " + aluguelMensal);
        System.out.println("Andar: " + andar);

    }
}