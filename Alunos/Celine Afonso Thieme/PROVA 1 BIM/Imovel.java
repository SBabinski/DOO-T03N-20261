public abstract class Imovel {

    protected String endereco;
    protected double valorAluguel;

    public Imovel(String endereco, double valorAluguel) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public abstract void exibirDados();

    @Override
    public String toString() {
        return endereco + " - R$ " + valorAluguel;
    }
}