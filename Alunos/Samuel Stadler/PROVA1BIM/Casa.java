public class Casa extends Imovel {
    private boolean temQuintal;
    public Casa(String endereco, double valorAluguelMensal, boolean temQuintal) {
        super(endereco, valorAluguelMensal);
        this.temQuintal = temQuintal;
    }
    public boolean isTemQuintal() {
        return temQuintal;
    }
    @Override
    public void exibirInfo() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereco: " + getEndereco());
        System.out.println("Valor do Aluguel Mensal: R$ " + getValorAluguelMensal());
        System.out.println("Tem Quintal: " + (temQuintal ? "Sim" : "Nao"));
    }
}
