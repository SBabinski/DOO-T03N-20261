public class Casa extends Imovel {
    private boolean temQuintal;

    public Casa(String endereco, double valorAluguelMensal, boolean temQuintal) {
        super(endereco, valorAluguelMensal);
        this.temQuintal = temQuintal;
    }

    public boolean isTemQuintal() { return temQuintal; }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereco: " + getEndereco());
        System.out.println("Valor mensal: R$ " + getValorAluguelMensal());
        System.out.println("Quintal: " + (temQuintal ? "Sim" : "Nao"));
    }
}