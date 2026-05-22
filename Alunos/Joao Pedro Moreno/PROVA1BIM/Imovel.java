public class Imovel {

    private String endereco;
    private double valorAluguel;

    // Construtor vazio
    public Imovel() {
    }

    // Construtor completo
    public Imovel(String endereco, double valorAluguel) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    // Getters e setters
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(double valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    // Método para exibir informações do imóvel
    public void mostrarImovel() {
        System.out.printf("Imóvel | Endereço: %s | Valor do Aluguel: R$ %.2f%n",
                getEndereco(), getValorAluguel());
    }

}
