public abstract class Imovel {
    public String endereco;
    public double valorAluguel;

    public Imovel(String endereco, double valorAluguel) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    public String getEndereco() {
        return endereco;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }    

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setValorAluguel(double valorAluguel) {
        this.valorAluguel = valorAluguel;
    }


    public abstract void exibirInfos();
}
