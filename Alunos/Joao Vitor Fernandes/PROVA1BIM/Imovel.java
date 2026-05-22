public abstract class Imovel {
    protected String endereco;
    protected double valorMensal;

    public Imovel(String endereco, double valorMensal) {
        this.endereco = endereco;
        this.valorMensal = valorMensal;
    }

    public String getEndereco() {
        return endereco;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public abstract void exibirInformacoes();
}