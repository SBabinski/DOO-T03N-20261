package fag;

public class Casa extends Imovel {
    private boolean possuiQuintal;

    public Casa(String endereco, double valorAluguelMensal, boolean possuiQuintal) {
        super(endereco, valorAluguelMensal);
        this.possuiQuintal = possuiQuintal;
    }

    public boolean isPossuiQuintal() {
        return possuiQuintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo de imovel: Casa");
        System.out.println("Endereco: " + getEndereco());
        System.out.println("Valor do aluguel mensal: R$ " + getValorAluguelMensal());
        System.out.println("Possui quintal: " + (possuiQuintal ? "Sim" : "Nao"));
    }
}
