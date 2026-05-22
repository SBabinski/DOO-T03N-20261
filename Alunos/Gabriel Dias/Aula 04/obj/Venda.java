package obj;
import java.time.LocalDate;

public class Venda {

    private String nomeProduto;
    private int quantidadeVendida;
    private double valorTotal;
    private double descontoAplicado;
    private LocalDate dataVenda;

    public Venda(String nomeProduto, int quantidadeVendida, double valorTotal, double descontoAplicado) {
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
        this.descontoAplicado = descontoAplicado;
        this.dataVenda = LocalDate.now();
    }

    public LocalDate getDataVenda() {

        return dataVenda;
    }

    public void exibirResumo() {
        System.out.println("\n=== Venda Registrada ===");
        System.out.println("Produto: " + nomeProduto);
        System.out.println("Quantidade: " + quantidadeVendida);
        System.out.println("Desconto: R$ " + descontoAplicado);
        System.out.println("Total pago: R$ " + valorTotal);
        System.out.println("Data: " + dataVenda);
    }
}