public class Casa extends Imovel {
    private boolean possuiQuintal;

    public Casa(String endereco, double valorAluguel, boolean possuiQuintal) {
        super(endereco, valorAluguel); // construtor da classe pai
        this.setPossuiQuintal(possuiQuintal);
    }

    public boolean possuiQuintal() {
        // retorna se a casa possui ou não quintal
        return possuiQuintal;
    }

    public void setPossuiQuintal(boolean possuiQuintal) {
        this.possuiQuintal = possuiQuintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Aluguel: R$ " + getValorAluguel());
        System.out.println("Possui Quintal: " + (possuiQuintal ? "Sim" : "Não"));
        System.out.println("---------------------------");
    }
}
