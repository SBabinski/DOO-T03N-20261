public class Casa extends Imovel {

    private boolean temQuintal;

    public Casa(String endereco, double valorAluguelMensal, boolean temQuintal) {
        super(endereco, valorAluguelMensal);
        this.temQuintal = temQuintal;
    }

    public boolean isTemQuintal() {
        return temQuintal;
    }

    public void setTemQuintal(boolean temQuintal) {
        this.temQuintal = temQuintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereco: " + getEndereco());
        System.out.println("Tem quintal: " + (temQuintal ? "Sim" : "Nao"));
        System.out.printf("Valor do Aluguel Mensal: R$ %.2f%n", getValorAluguelMensal());
    }
}