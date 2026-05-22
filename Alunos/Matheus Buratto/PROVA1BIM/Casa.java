public class Casa extends Imovel {
    private boolean temQuintal;

    public Casa(String endereco, double valorAluguelMensal, boolean temQuintal) {
        super(endereco, valorAluguelMensal);
        this.temQuintal = temQuintal;
    }

    public boolean isTemQuintal() { return temQuintal; }

    @Override
    public void exibirInformacoes() {
        System.out.println("Casa | Endereço: " + getEndereco()
                + " | Quintal: " + (temQuintal ? "Sim" : "Não")
                + " | Aluguel: R$ " + getValorAluguelMensal());
    }
}