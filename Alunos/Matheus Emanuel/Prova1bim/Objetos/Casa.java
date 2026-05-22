public class Casa extends Imoveis {
    private boolean possuiQuintal;

    public Casa(String endereco, double valorAluguel, boolean possuiQuintal) {
        super(endereco, valorAluguel);
        this.possuiQuintal = possuiQuintal;
    }

    public boolean isPossuiQuintal() {
        return possuiQuintal;
    }

    public void setPossuiQuintal(boolean possuiQuintal) {
        this.possuiQuintal = possuiQuintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Possui quintal: " + (possuiQuintal ? "Sim" : "Não"));
        System.out.printf("Valor do aluguel mensal: R$ %.2f%n", getValorAluguel());
    }
}