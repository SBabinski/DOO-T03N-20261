public abstract class Imoveis {
    private String endereco;
    private double valorAluguel;

    public Imoveis(String endereco, double valorAluguel) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            System.out.println("O endereço do imóvel não pode ser vazio.");
            return;
        }
        this.endereco = endereco;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(double valorAluguel) {
        if (valorAluguel < 0) {
            System.out.println("O valor do aluguel não pode ser negativo.");
            return;
        }
        this.valorAluguel = valorAluguel;
    }

    public abstract void exibirInformacoes();
}