public abstract class Imovel {
    protected String endereco;
    protected double valorAluguel;

    public Imovel(String endereco, double valorAluguel) {
        if(valorAluguel <= 0) {
            throw new IllegalArgumentException("Valor inválido!");
        }
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public abstract String exibirDados(); 
}
