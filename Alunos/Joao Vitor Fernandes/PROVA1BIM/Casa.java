
public class Casa extends Imovel {
    private boolean temQuintal;

    public Casa(String endereco, double valorMensal, boolean temQuintal) {
        super(endereco, valorMensal);
        this.temQuintal = temQuintal;
    }

    public boolean isTemQuintal() {
        return temQuintal;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Casa - Endereço: " + endereco + ", Valor Mensal: R$ " + valorMensal + ", Tem Quintal: " + (temQuintal ? "Sim" : "Não"));
    }
}