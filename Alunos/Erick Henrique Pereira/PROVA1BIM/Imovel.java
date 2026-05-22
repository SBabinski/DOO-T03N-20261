public abstract sealed class Imovel permits Casa, Apartamento {

    private Endereco endereco;
    private double valorAluguel;

    public Imovel(Endereco endereco, double valorAluguel) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    public Imovel(){}

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(double valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public abstract String mostrarInformacoes();


}
