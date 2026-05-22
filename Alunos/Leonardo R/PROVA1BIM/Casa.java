public class Casa extends Imovel {

    private boolean temQuintal;

    public Casa(String endereco, double valorAluguel, boolean temQuintal) {
        super(endereco, valorAluguel);
        this.temQuintal = temQuintal;
    }

    public boolean isTemQuintal() {
        return temQuintal;
    }

    public void setTemQuintal(boolean temQuintal) {
        this.temQuintal = temQuintal;
    }

    @Override
    public void exibirImovel() {
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Valor do Aluguel: " + getValorAluguel());
        System.out.println("Tem quintal: " + temQuintal);
    }
}