public class Apartamento extends Imovel {
    private int andar;

    public Apartamento(String endereco, double valorMensal, int andar) {
        super(endereco, valorMensal);
        this.andar = andar;
    }

    public int getAndar() {
        return andar;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Apartamento - Endereço: " + endereco + ", Valor Mensal: R$ " + valorMensal + ", Andar: " + andar);
    }
}
        

    
  