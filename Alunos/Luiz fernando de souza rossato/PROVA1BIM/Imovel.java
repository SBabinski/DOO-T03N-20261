public abstract class Imovel {
    private String endereco;
    private double valorAluguelmensal;

    public Imovel(String endereco, double valorAluguelmensal) {
        this.endereco = endereco;
        this.valorAluguelmensal = valorAluguelmensal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getValorAluguelmensal() {
        return valorAluguelmensal;
    }

    public void setValorAluguelmensal(double valorAluguelmensal) {
        this.valorAluguelmensal = valorAluguelmensal;
    }

    public abstract void exibirdados();    

}
