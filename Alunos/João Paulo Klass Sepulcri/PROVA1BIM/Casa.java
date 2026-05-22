public class Casa extends Imovel {
    boolean possuiQuintal;

    public Casa(String endereco, double valorAluguel, boolean possuiQuintal) {
        super(endereco, valorAluguel);
        this.possuiQuintal = possuiQuintal;
    }

    public void exibirInformacoes() {
        System.out.println("Tipo: Casa");
        System.out.println("Endereco: " + endereco);
        System.out.println("Valor do aluguel: R$ " + valorAluguel);
        System.out.println("Possui quintal: " + (possuiQuintal ? "Sim" : "Nao"));
    }
}
