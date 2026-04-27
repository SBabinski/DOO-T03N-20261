import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    int quantidade;
    double valorUnitario;
    double valorBruto;
    double desconto;
    double valorFinal;
    LocalDate dataVenda;

    public Venda(int quantidade, double valorUnitario, LocalDate dataVenda) {
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataVenda = dataVenda;
        calcularValores();
    }

    private void calcularValores() {
        valorBruto = quantidade * valorUnitario;

        if (quantidade > 10) {
            desconto = valorBruto * 0.05;
        } else {
            desconto = 0;
        }

        valorFinal = valorBruto - desconto;
    }

    public void apresentarVenda(DateTimeFormatter formatter) {
        System.out.println("Data: " + dataVenda.format(formatter));
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Valor unitario: R$ " + valorUnitario);
        System.out.println("Valor bruto: R$ " + valorBruto);
        System.out.println("Desconto: R$ " + desconto);
        System.out.println("Valor final: R$ " + valorFinal);
    }
}
