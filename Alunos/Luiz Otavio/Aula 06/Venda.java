import java.time.LocalDate;

public class Venda {

    int quantidade;
    double valorTotal;
    double desconto;
    LocalDate data;

    public Venda(int quantidade, double valorTotal, double desconto, LocalDate data) {
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.data = data;
    }

    public void mostrarVenda() {
        System.out.println("Data: " + data);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Total: R$ " + valorTotal);
        System.out.println("Desconto: R$ " + desconto);
        System.out.println("-------------------");
    }
}