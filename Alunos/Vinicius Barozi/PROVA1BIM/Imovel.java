abstract class Imovel {

    String endereco;
    double aluguelMensal;

    public Imovel(String endereco, double aluguelMensal) {
        this.endereco = endereco;
        this.aluguelMensal = aluguelMensal;
    }

    public double getAluguelMensal() {
        return aluguelMensal;
    }

    public abstract void exibirInformacoes();
}