class Casa extends Imovel {

    boolean possuiQuintal;

    public Casa(String endereco,
                double aluguelMensal,
                boolean possuiQuintal) {

        super(endereco, aluguelMensal);
        this.possuiQuintal = possuiQuintal;
    }

    @Override
    public void exibirInformacoes() {

        System.out.println("Tipo: Casa");
        System.out.println("Endereço: " + endereco);
        System.out.println("Aluguel: R$ " + aluguelMensal);

        System.out.println(
             "Quintal: " +
             (possuiQuintal ? "Sim" : "Não")
        );

    }
}