import java.time.LocalDate;

public class Venda {

    int quantidade;
    double valorVenda;
    double desconto;
    LocalDate data;

    public Venda(int quantidade, double valorVenda, double desconto, LocalDate data) {
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
        this.desconto = desconto;
        this.data = data;
    }

    public void mostrarVenda() {
        System.out.println("Data: " + data);
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Valor da venda: R$ " + valorVenda);
        System.out.println("Desconto: R$ " + desconto);
        System.out.println("----------------------");
    }
}