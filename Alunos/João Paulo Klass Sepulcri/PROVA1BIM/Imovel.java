public class Imovel {
    String endereco;
    double valorAluguel;

    public Imovel(String endereco, double valorAluguel) {
        this.endereco = endereco;
        this.valorAluguel = valorAluguel;
    }

    public void exibirInformacoes() {
        System.out.println("Endereco: " + endereco);
        System.out.println("Valor do aluguel: R$ " + valorAluguel);
    }
}
