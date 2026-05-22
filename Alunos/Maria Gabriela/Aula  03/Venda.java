public class Venda {
    private int quantidade;
    private double valorTotal;
    private double desconto;

    public Venda(int quantidade, double valorTotal, double desconto) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }

    public void exibirVenda() {
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Valor final: R$ " + valorTotal);
        System.out.println("Desconto aplicado: R$ " + desconto);
        System.out.println("--------------------------");
    }
}