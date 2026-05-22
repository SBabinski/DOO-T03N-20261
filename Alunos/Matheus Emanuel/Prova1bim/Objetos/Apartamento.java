public class Apartamento extends Imoveis {
    private int numeroAndar;

    public Apartamento(String endereco, double valorAluguel, int numeroAndar) {
        super(endereco, valorAluguel);
        this.numeroAndar = numeroAndar;
    }

    public int getNumeroAndar() {
        return numeroAndar;
    }

    public void setNumeroAndar(int numeroAndar) {
        if (numeroAndar < 0) {
            System.out.println("O número do andar não pode ser negativo.");
            return;
        }
        this.numeroAndar = numeroAndar;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Tipo: Apartamento");
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Número do andar: " + numeroAndar);
        System.out.printf("Valor do aluguel mensal: R$ %.2f%n", getValorAluguel());
}
}