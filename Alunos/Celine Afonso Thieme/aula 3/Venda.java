public class Venda {

    private String nomeProduto;
    private int quantidadeVendida;
    private double valorTotal;
    private double descontoAplicado;

    public Venda(String nomeProduto, int quantidadeVendida, double valorTotal, double descontoAplicado) {
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
        this.descontoAplicado = descontoAplicado;
    }

    public void exibirResumo() {
        System.out.println("\n=== Venda Registrada ===");
        System.out.println("Produto: " + nomeProduto);
        System.out.println("Quantidade: " + quantidadeVendida);
        System.out.println("Desconto: R$ " + descontoAplicado);
        System.out.println("Total pago: R$ " + valorTotal);
    }
}