public class Apartamento extends Imovel {
    private int andar;
    
    public Apartamento(String endereco, double valorAluguelmensal, int andar) {
        super(endereco, valorAluguelmensal);
        this.andar = andar;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    @Override
    public void exibirdados() {
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Valor do Aluguel Mensal: R$ " + getValorAluguelmensal());
        System.out.println("Andar: " + andar);
    }
}
