public abstract class Imovel {

    private String endereco;
    private double valorAluguelMensal;

    public Imovel(String endereco, double valorAluguelMensal) {
        this.endereco = endereco;
        this.valorAluguelMensal = valorAluguelMensal;
    }

    public String getEndereco() {
        return endereco;
    }

    public double getValorAluguelMensal() {
        return valorAluguelMensal;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setValorAluguelMensal(double valorAluguelMensal) {
        this.valorAluguelMensal = valorAluguelMensal;
    }

    public abstract void exibirInformacoes();
}