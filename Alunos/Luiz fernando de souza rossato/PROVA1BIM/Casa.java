public class Casa extends Imovel {
    private boolean possuiQuintal;

    public Casa(String endereco, double valorAluguelmensal, boolean possuiQuintal) {
        super(endereco, valorAluguelmensal);
        this.possuiQuintal = possuiQuintal;
    }   

    public boolean isPossuiQuintal() {
        return possuiQuintal;
    }


    @Override
    public void exibirdados() {
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Valor do Aluguel Mensal: R$ " + getValorAluguelmensal());
        System.out.println("Possui Quintal: " + (possuiQuintal ? "Sim" : "Não"));
    }

    
}
