import java.time.LocalDate;

public class Venda {
    private int quantidade;
    private double valorTotal;
    private double desconto;
    private LocalDate data;

    public Venda(int quantidade, double valorTotal, double desconto, LocalDate data) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.data = data;
    }

    public LocalDate getData(){
        return data;
    }

    public void exibirVenda() {
        System.out.println("Data: " + data);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Valor final: R$ " + valorTotal);
        System.out.println("Desconto aplicado: R$ " + desconto);
        System.out.println("--------------------------");
    }
}